package org.hibernate.omm.jdbc;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.hibernate.omm.jdbc.adapter.StatementAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.jdbc.exception.StatementClosedSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.util.List;

import static org.hibernate.omm.util.DocumentUtil.getProjectionFieldsIncluded;

public class MongoStatement implements StatementAdapter {

    private record CurrentQueryResult(String collection, ResultSet resultSet, long cursorId) {
    }

    protected final MongoDatabase mongoDatabase;
    protected final ClientSession clientSession;

    protected final Connection connection;
    private CurrentQueryResult currentQueryResult;
    private int currentUpdateCount;
    private int fetchSize;

    private volatile boolean closed;

    public MongoStatement(MongoDatabase mongoDatabase, ClientSession clientSession, Connection connection) {
        this.mongoDatabase = mongoDatabase;
        this.clientSession = clientSession;
        this.connection = connection;
    }

    private Document runCommand(Document command) {
        return mongoDatabase.runCommand(clientSession, command);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SimulatedSQLException {
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        Document commandResult = runCommand(command);
        if (commandResult.getDouble("ok") != 1.0) {
            throw new CommandRunFailSQLException();
        }
        return new MongoResultSet(commandResult, getProjectionFieldsIncluded(command.get("projection", Document.class)));
    }

    private boolean projectionInclude(Object projectionValue) {
        if (projectionValue instanceof BsonDocument bsonDocument) {
            return bsonDocument.isInt32() && bsonDocument.asInt32().getValue() == 1
                    || bsonDocument.isBoolean() && bsonDocument.asBoolean().getValue();
        } else {
            return projectionValue instanceof Integer integer && integer == 1
                    || projectionValue instanceof Boolean bool && bool;
        }
    }

    @Override
    public int executeUpdate(String sql) throws SimulatedSQLException {
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        Document commandResult = runCommand(command);
        if (commandResult.getDouble("ok") != 1.0) {
            throw new CommandRunFailSQLException();
        }
        return commandResult.getInteger("n");
    }

    @Override
    public boolean execute(String sql) throws SimulatedSQLException {
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        String collection = (String) command.entrySet().iterator().next().getValue();
        Document commandResult = runCommand(command);
        if (commandResult.getDouble("ok") != 1.0) {
            throw new CommandRunFailSQLException();
        }
        Document cursor = commandResult.get("cursor", Document.class);
        if (cursor != null) {
            long cursorId = cursor.getLong("id");
            ResultSet resultSet =
                    new MongoResultSet(cursor.getList("firstBatch", Document.class));
            currentQueryResult = new CurrentQueryResult(collection, resultSet, cursorId);
            currentUpdateCount = -1;
            return true;
        } else {
            currentQueryResult = null;
            currentUpdateCount = commandResult.getInteger("n");
            return false;
        }
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public void cancel() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public SQLWarning getWarnings() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public void clearWarnings() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public ResultSet getResultSet() {
        return currentQueryResult == null ? null : currentQueryResult.resultSet;
    }

    @Override
    public int getUpdateCount() {
        return currentUpdateCount;
    }

    @Override
    public boolean getMoreResults() throws SimulatedSQLException {
        if (currentQueryResult != null) {
            Document moreResultsCommand =
                    new Document()
                            .append("getMore", currentQueryResult.cursorId)
                            .append("collection", currentQueryResult.collection);
            if (fetchSize != 0) {
                moreResultsCommand.append("batchSize", fetchSize);
            }
            Document moreResults = runCommand(moreResultsCommand);
            if (moreResults.getDouble("ok") != 1.0) {
                throw new CommandRunFailSQLException();
            }
            Document cursor = moreResults.get("cursor", Document.class);
            List<Document> nextBatch = cursor.getList("nextBatch", Document.class);
            long cursorId = cursor.getLong("id");
            currentQueryResult =
                    new CurrentQueryResult(
                            currentQueryResult.collection,
                            new MongoResultSet(nextBatch),
                            cursorId
                    );
            return !nextBatch.isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public void setFetchSize(int rows) throws SimulatedSQLException {
        throwExceptionIfClosed();
        this.fetchSize = rows;
    }

    @Override
    public int getFetchSize() throws SimulatedSQLException {
        throwExceptionIfClosed();
        return this.fetchSize;
    }

    @Override
    public void addBatch(String sql) throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public void clearBatch() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public int[] executeBatch() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    private void throwExceptionIfClosed() throws StatementClosedSQLException {
        if (closed) {
            throw new StatementClosedSQLException();
        }
    }
}
