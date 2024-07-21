package org.hibernate.omm.jdbc;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.List;

import org.hibernate.omm.jdbc.adapter.ArrayAdapter;
import org.hibernate.omm.jdbc.adapter.ConnectionAdapter;
import org.hibernate.omm.jdbc.adapter.DatabaseMetaDataAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongodbConnection extends ConnectionAdapter {

    private final ClientSession clientSession;
    private final MongoDatabase mongoDatabase;

    private boolean autoCommit;
    private boolean supportsTransaction;
    private boolean closed;

    private SQLWarning sqlWarning;

    public MongodbConnection(ClientSession clientSession, MongoDatabase mongoDatabase) {
        this.clientSession = clientSession;
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public Statement createStatement() {
        return new MongodbStatement(mongoDatabase, clientSession, this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
        return new MongodbPreparedStatement(mongoDatabase, clientSession, this, sql);
    }

    @Override
    public DatabaseMetaData getMetaData() throws SimulatedSQLException {
        Document result = mongoDatabase.runCommand(new Document("buildinfo", 1));
        if (result.getDouble("ok") != 1.0) {
            throw new CommandRunFailSQLException(result.toJson());
        }
        String version = result.getString("version");
        List<Integer> versionArray = result.getList("versionArray", Integer.class);

        if (versionArray.get(0) >= 4) {
            supportsTransaction = true;
        }
        return new DatabaseMetaDataAdapter() {

            @Override
            public String getDatabaseProductVersion() {
                return version;
            }

            @Override
            public int getDatabaseMajorVersion() {
                return versionArray.get(0);
            }

            @Override
            public int getDatabaseMinorVersion() {
                return versionArray.get(1);
            }

            @Override
            public Connection getConnection() {
                return MongodbConnection.this;
            }

        };
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String nativeSQL(String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean getAutoCommit() throws SimulatedSQLException {
        return this.autoCommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        if (!autoCommit && supportsTransaction) {
            this.clientSession.startTransaction();
        }
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        if (!autoCommit && supportsTransaction) {
            clientSession.commitTransaction();
        }
    }

    @Override
    public void rollback() {
        if (!autoCommit && supportsTransaction) {
            clientSession.abortTransaction();
        }
    }

    @Override
    public void close() {
        this.clientSession.close();
        this.closed = true;
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public void clearWarnings() {
        sqlWarning = null;
    }

    @Override
    public SQLWarning getWarnings() {
        return sqlWarning;
    }

    @Override
    public String getCatalog() {
        return "undefined";
    }

    @Override
    public String getSchema() {
        return "undefined";
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SimulatedSQLException {
        return new ArrayAdapter() {
            @Override
            public int getBaseType() throws SQLException {
                return 0;
            }

            @Override
            public Object getArray() throws SQLException {
                return elements;
            }
        };
    }
}
