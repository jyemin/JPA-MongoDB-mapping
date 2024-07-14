package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongodbPreparedStatement extends MongodbStatement
        implements PreparedStatementAdapter {

    private String commandString;
    private Map<Integer, Object> parameters;

    public MongodbPreparedStatement(MongoDatabase mongoDatabase, Connection connection, String sql) {
        super(mongoDatabase, connection);
        this.commandString = sql;
        this.parameters = new HashMap<>();
    }

    @Override
    public ResultSet executeQuery() throws SimulatedSQLException {
        return executeQuery(getFinalCommandString());
    }

    @Override
    public int executeUpdate() throws SimulatedSQLException {
        return executeUpdate(getFinalCommandString());
    }

    @Override
    public boolean execute() throws SimulatedSQLException {
        return execute(getFinalCommandString());
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
        parameters.put(parameterIndex, null);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public void setString(int parameterIndex, String x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SimulatedSQLException {
        parameters.put(parameterIndex, x);
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

    private static final Pattern PARAMETER_PLACEHOLDER_PATTERN = Pattern.compile("\\s(\\?)\\s");

    private String getFinalCommandString() {
        Matcher matcher = PARAMETER_PLACEHOLDER_PATTERN.matcher(commandString);
        int parameterIndex = 1;
        int lastGroupEndIndex = -1;
        StringBuilder sb = new StringBuilder();
        while (matcher.matches()) {
            int startIndex = matcher.start();
            sb.append(commandString, lastGroupEndIndex + 1, startIndex);
            Object parameterValue = parameters.get(parameterIndex++);

            matcher.appendReplacement(sb, parameterValue.toString());
            lastGroupEndIndex = startIndex;
        }
        sb.append(commandString.substring(lastGroupEndIndex + 1));
        return sb.toString();
    }
}
