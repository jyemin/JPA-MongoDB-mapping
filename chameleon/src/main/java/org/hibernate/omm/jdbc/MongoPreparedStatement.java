package org.hibernate.omm.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.util.StringUtil;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;

public class MongoPreparedStatement extends MongodbStatement
        implements PreparedStatementAdapter {

    private final String parameterizedCommandJson;
    private final Map<Integer, String> parameters;

    public MongoPreparedStatement(
            MongoDatabase mongoDatabase,
            ClientSession clientSession,
            Connection connection,
            String parameterizedCommandJson) {
        super(mongoDatabase, clientSession, connection);
        this.parameterizedCommandJson = parameterizedCommandJson;
        this.parameters = new HashMap<>();
    }

    @Override
    public ResultSet executeQuery() throws SimulatedSQLException {
        return executeQuery(getFinalCommandJson());
    }

    @Override
    public int executeUpdate() throws SimulatedSQLException {
        return executeUpdate(getFinalCommandJson());
    }

    @Override
    public boolean execute() throws SimulatedSQLException {
        return execute(getFinalCommandJson());
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
        parameters.put(parameterIndex, "null");
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
        parameters.put(parameterIndex, Boolean.toString(x));
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
        parameters.put(parameterIndex, Byte.toString(x));
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SimulatedSQLException {
        parameters.put(parameterIndex, Short.toString(x));
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{ \"$numberInt\": \"" + x + "\" }");
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{ \"$numberLong\": \"" + x + "\" }");
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
        parameters.put(parameterIndex, Float.toString(x));
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{ \"$numberDouble\": \"" + x + "\" }");
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{ \"$numberDecimal\": \"" + x + "\" }");
    }

    @Override
    public void setString(int parameterIndex, String x) throws SimulatedSQLException {
        parameters.put(parameterIndex, StringUtil.writeStringHelper(x));
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SimulatedSQLException {
        String base64Text = Base64.getEncoder().encodeToString(x);
        parameters.put(parameterIndex, "\"$binary\": {\"base64\": \"" + base64Text + "\", \"subType\": \"0\"}");
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SimulatedSQLException {
        parameters.put(parameterIndex, "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SimulatedSQLException {
        parameters.put(
                parameterIndex,
                "{\"$timestamp\": {\"" + x.toInstant().getEpochSecond() + "\": <t>, \"i\": 1}}"
        );
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
        try {
            Object[] array = (Object[]) x.getArray();
            String json;
            if (array.length == 0) {
                json = "[]";
            } else {
                json = "[" + Arrays.stream(array).map(obj -> obj instanceof String aStr ?
                        StringUtil.writeStringHelper(aStr) :
                        obj.toString()).collect(Collectors.joining(",")) + "]";
            }
            parameters.put(parameterIndex, json);
        } catch (SQLException cause) {
            throw new SimulatedSQLException(cause.getMessage(), cause);
        }
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

    private String getFinalCommandJson() {
        int parameterIndex = 1;
        int lastIndex = -1;
        int index;
        StringBuilder sb = new StringBuilder();

        while ((index = parameterizedCommandJson.indexOf('?', lastIndex + 1)) != -1) {
            sb.append(parameterizedCommandJson, lastIndex + 1, index);
            String parameterValue = parameters.get(parameterIndex++);
            sb.append(parameterValue);
            lastIndex = index;
        }
        sb.append(parameterizedCommandJson.substring(lastIndex + 1));
        return sb.toString();
    }
}
