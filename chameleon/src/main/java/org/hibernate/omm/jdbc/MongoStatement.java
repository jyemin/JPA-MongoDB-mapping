package org.hibernate.omm.jdbc;

import com.mongodb.assertions.Assertions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.Nullable;
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

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoStatement implements StatementAdapter {
    private static final String OK_FIELD = "ok";
    private static final String COUNT_FIELD = "n";
    private static final String CURSOR_FIELD = "cursor";
    private static final String FIRST_BATCH_FIELD = "firstBatch";
    private static final String NEXT_BATCH_FIELD = "nextBatch";
    private static final String CURSOR_ID_FIELD = "id";
    private static final String GET_MORE_ID_FIELD = "getMore";
    private static final String GET_MORE_COLLECTION_FIELD = "collection";
    private static final String GET_MORE_BATCH_SIZE_FIELD = "batchSize";

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
        Assertions.notNull("mongoDatabase", mongoDatabase);
        Assertions.notNull("clientSession", clientSession);
        Assertions.notNull("connection", connection);
        this.mongoDatabase = mongoDatabase;
        this.clientSession = clientSession;
        this.connection = connection;
    }

    private Document runCommand(Document command) {
        return mongoDatabase.runCommand(clientSession, command);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SimulatedSQLException {
        Assertions.notNull("sql", sql);
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        Document commandResult = runCommand(command);
        if (commandResult.getDouble(OK_FIELD) != 1.0) {
            throw new CommandRunFailSQLException(commandResult);
        }
        return new MongoResultSet(commandResult);
    }

    @Override
    public int executeUpdate(String sql) throws SimulatedSQLException {
        Assertions.notNull("sql", sql);
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        Document commandResult = runCommand(command);
        if (commandResult.getDouble(OK_FIELD) != 1.0) {
            throw new CommandRunFailSQLException(commandResult);
        }
        return commandResult.getInteger(COUNT_FIELD);
    }

    @Override
    public boolean execute(String sql) throws SimulatedSQLException {
        Assertions.notNull("sql", sql);
        throwExceptionIfClosed();
        Document command = Document.parse(sql);
        String collection = (String) command.entrySet().iterator().next().getValue();
        Document commandResult = runCommand(command);
        if (commandResult.getDouble(OK_FIELD) != 1.0) {
            throw new CommandRunFailSQLException(commandResult);
        }
        Document cursor = commandResult.get(CURSOR_FIELD, Document.class);
        if (cursor != null) {
            long cursorId = cursor.getLong(CURSOR_ID_FIELD);
            ResultSet resultSet =
                    new MongoResultSet(cursor.getList(FIRST_BATCH_FIELD, Document.class));
            currentQueryResult = new CurrentQueryResult(collection, resultSet, cursorId);
            currentUpdateCount = -1;
            return true;
        } else {
            currentQueryResult = null;
            currentUpdateCount = commandResult.getInteger(COUNT_FIELD);
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
    @Nullable
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
                            .append(GET_MORE_ID_FIELD, currentQueryResult.cursorId)
                            .append(GET_MORE_COLLECTION_FIELD, currentQueryResult.collection);
            if (fetchSize != 0) {
                moreResultsCommand.append(GET_MORE_BATCH_SIZE_FIELD, fetchSize);
            }
            Document moreResults = runCommand(moreResultsCommand);
            if (moreResults.getDouble(OK_FIELD) != 1.0) {
                throw new CommandRunFailSQLException(moreResults);
            }
            Document cursor = moreResults.get(CURSOR_FIELD, Document.class);
            List<Document> nextBatch = cursor.getList(NEXT_BATCH_FIELD, Document.class);
            long cursorId = cursor.getLong(CURSOR_ID_FIELD);
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
