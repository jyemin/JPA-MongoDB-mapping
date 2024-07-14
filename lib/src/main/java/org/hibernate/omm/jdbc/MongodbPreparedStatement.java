package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import org.bson.BsonValue;
import org.bson.Document;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.sql.exec.spi.JdbcOperation;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MongodbPreparedStatement<T extends JdbcOperation> extends MongodbStatement
        implements PreparedStatementAdapter {

    private String commandString;
    private Map<Integer, BsonValue> parameters;

    public MongodbPreparedStatement(MongoDatabase mongoDatabase, Connection connection, String sql) {
        super(mongoDatabase, connection);
        this.commandString = sql;
        this.parameters = new HashMap<>();
    }

    @Override
    public ResultSet executeQuery() throws SimulatedSQLException {
        Document commandResult = runCommand();
        return new MongodbResultSet(commandResult);
    }

    @Override
    public int executeUpdate() throws SimulatedSQLException {
        Document result = runCommand();
        return result.getInteger("n");
    }

    @Override
    public boolean execute() throws SimulatedSQLException {
        Document result = runCommand();
        return result.containsKey("cursor");
    }

    /**
     * @see org.hibernate.engine.jdbc.batch.internal.BatchImpl#addToBatch(JdbcValueBindings,
     * TableInclusionChecker)
     */
    @Override
    public void addBatch() {
        // no-op
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setString(int parameterIndex, String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    private Document runCommand() throws CommandRunFailSQLException {
        Document command = Document.parse(getFinalCommandString());
        Document commandResult = mongoDatabase.runCommand(command);
        if (commandResult.getInteger("ok") != 1) {
            throw new CommandRunFailSQLException();
        }
        return commandResult;
    }

    private String getFinalCommandString() {
        return null;
    }
}
