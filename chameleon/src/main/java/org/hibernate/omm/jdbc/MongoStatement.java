package org.hibernate.omm.jdbc;

import com.mongodb.assertions.Assertions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.lang.Nullable;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.hibernate.omm.jdbc.adapter.StatementAdapter;
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

    protected final MongoDatabase mongoDatabase;
    protected final ClientSession clientSession;

    protected final Connection connection;

    private boolean closed;

    public MongoStatement(MongoDatabase mongoDatabase, ClientSession clientSession, Connection connection) {
        Assertions.notNull("mongoDatabase", mongoDatabase);
        Assertions.notNull("clientSession", clientSession);
        Assertions.notNull("connection", connection);
        this.mongoDatabase = mongoDatabase;
        this.clientSession = clientSession;
        this.connection = connection;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SimulatedSQLException {
        Assertions.notNull("sql", sql);
        throwExceptionIfClosed();
        BsonDocument command = BsonDocument.parse(sql);
        MongoCollection<BsonDocument> collection = mongoDatabase.getCollection(command.getString("aggregate").getValue(),
                BsonDocument.class);
        List<BsonDocument> pipeline = command.getArray("pipeline").stream().map(BsonValue::asDocument).toList();
        MongoCursor<BsonDocument> cursor = collection.aggregate(clientSession, pipeline).cursor();

        return new MongoResultSet(cursor,
                pipeline.get(pipeline.size() - 1).asDocument().getDocument("$project").keySet().stream().toList());
    }

    @Override
    public int executeUpdate(String sql) throws SimulatedSQLException {
        Assertions.notNull("sql", sql);
        throwExceptionIfClosed();
        BsonDocument command = BsonDocument.parse(sql);

        String commandName = command.getFirstKey();
        MongoCollection<BsonDocument> collection = mongoDatabase.getCollection(command.getString(commandName).getValue(),
                BsonDocument.class);
        switch (commandName) {
            case "insert":
                BsonDocument document = command.getArray("documents").get(0).asDocument();
                collection.insertOne(clientSession, document);
                return 1;
            case "update":
                BsonDocument updateDocument = command.getArray("updates").get(0).asDocument();
                // TODO: this should call updateMany or updateOne depending on the value of the multi field
                UpdateResult updateResult = collection.updateMany(clientSession, updateDocument.getDocument("q"), updateDocument.getDocument("u"));
                return (int) updateResult.getModifiedCount();
            case "delete":
                BsonDocument deleteDocument = command.getArray("deletes").get(0).asDocument();
                // TODO: this should call deleteMany or deleteOne depending on the value of the limit field
                DeleteResult deleteResult = collection.deleteMany(clientSession, deleteDocument.getDocument("q"));
                return (int) deleteResult.getDeletedCount();
            default:
                throw new NotSupportedSQLException();
        }
   }

    @Override
    public boolean execute(String sql) throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
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
    public ResultSet getResultSet() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public int getUpdateCount() {
        // unclear what this is, throw for now.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public void setFetchSize(int rows) throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
    }

    @Override
    public int getFetchSize() throws SimulatedSQLException {
        throwExceptionIfClosed();
        throw new NotSupportedSQLException();
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
