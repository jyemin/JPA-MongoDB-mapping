package org.hibernate.omm.jdbc;

import com.mongodb.assertions.Assertions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.exception.NotYetImplementedException;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayWriter;
import java.io.IOException;
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

/**
 * Simulate JDBC's {@link java.sql.PreparedStatement} to create a virtual MongoDB JDBC driver
 * on top of MongoDB Java driver.
 *
 * @author Nathan Xu
 * @apiNote v2 extended JSON format is observed when generating command JSON string.
 * @see <a href="https://www.mongodb.com/docs/manual/reference/mongodb-extended-json/">MongoDB Extended JSON (v2)</a>
 * @since 1.0.0
 */
public class MongoPreparedStatement extends MongoStatement
        implements PreparedStatementAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MongoPreparedStatement.class);

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
        return executeQuery(getDeParameterizedBsonCommand());
    }

    @Override
    public int executeUpdate() throws SimulatedSQLException {
        return executeUpdate(getDeParameterizedBsonCommand());
    }

    @Override
    public boolean execute() throws SimulatedSQLException {
        return execute(getDeParameterizedBsonCommand());
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
    public void setNull(int parameterIndex, int sqlType) {
        parameters.put(parameterIndex, "null");
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) {
        parameters.put(parameterIndex, Boolean.toString(x));
    }

    @Override
    public void setByte(int parameterIndex, byte x) {
        parameters.put(parameterIndex, Byte.toString(x));
    }

    @Override
    public void setShort(int parameterIndex, short x) {
        parameters.put(parameterIndex, Short.toString(x));
    }

    @Override
    public void setInt(int parameterIndex, int x) {
        parameters.put(parameterIndex, "{ \"$numberInt\": \"" + x + "\" }");
    }

    @Override
    public void setLong(int parameterIndex, long x) {
        parameters.put(parameterIndex, "{ \"$numberLong\": \"" + x + "\" }");
    }

    @Override
    public void setFloat(int parameterIndex, float x) {
        parameters.put(parameterIndex, Float.toString(x));
    }

    @Override
    public void setDouble(int parameterIndex, double x) {
        parameters.put(parameterIndex, "{ \"$numberDouble\": \"" + x + "\" }");
    }

    @Override
    public void setBigDecimal(int parameterIndex, @Nullable BigDecimal x) {
        parameters.put(parameterIndex, x == null ? "null" : "{ \"$numberDecimal\": \"" + x + "\" }");
    }

    @Override
    public void setString(int parameterIndex, @Nullable String x) {
        parameters.put(parameterIndex, x == null ? "null" : StringUtil.writeStringHelper(x));
    }

    @Override
    public void setBytes(int parameterIndex, @Nullable byte[] x) {
        parameters.put(parameterIndex, x == null ? "null" : "\"$binary\": {\"base64\": \"" + Base64.getEncoder().encodeToString(x) + "\", \"subType\": \"0\"}");
    }

    @Override
    public void setDate(int parameterIndex, @Nullable Date x) {
        parameters.put(parameterIndex, x == null ? "null" : "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTime(int parameterIndex, @Nullable Time x) {
        parameters.put(parameterIndex, x == null ? "null" : "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTimestamp(int parameterIndex, @Nullable Timestamp x) {
        parameters.put(
                parameterIndex,
                x == null ? "null" : "{\"$timestamp\": {\"" + x.toInstant().getEpochSecond() + "\": <t>, \"i\": 1}}"
        );
    }

    @Override
    public void setObject(int parameterIndex, @Nullable Object x, int targetSqlType)
            throws SimulatedSQLException {
        if (x == null) {
            parameters.put(parameterIndex, "null");
        } else {
            switch (targetSqlType) {
                case 3_000:
                    ObjectId objectId = (ObjectId) x;
                    parameters.put(parameterIndex, "{ \"$oid\": \"" + objectId.toHexString() + "\" }");
                    break;
                default:
                    throw new NotSupportedSQLException("unknown MongoSqlType: " + targetSqlType);
            }
        }
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SimulatedSQLException {
        Assertions.notNull("x", x);

        try {
            Iterable<?> iterable;
            if (x.getArray().getClass().isArray()) {
                iterable = Arrays.asList((Object[]) x.getArray());
            } else {
                iterable = ((Iterable<?>) x.getArray());
            }
            parameters.put(parameterIndex, getArrayJson(iterable));
        } catch (SQLException | IOException cause) {
            throw new SimulatedSQLException(cause.getMessage(), cause);
        }
    }

    private String getArrayJson(Iterable<?> iterable) throws IOException {
        var stringWriter = new CharArrayWriter();
        stringWriter.write("[ ");
        var first = true;
        for (Object obj : iterable) {
            if (!first) {
                stringWriter.write(", ");
            } else {
                first = false;
            }
            stringWriter.write(obj instanceof String aStr ?
                    StringUtil.writeStringHelper(aStr) :
                    obj == null ? "null" : obj.toString());
        }
        stringWriter.write(" ]");
        return stringWriter.toString();
    }

    private String getDeParameterizedBsonCommand() {
        int parameterIndex = 1;
        int lastIndex = -1;
        int index;
        var sb = new StringBuilder();

        while ((index = parameterizedCommandJson.indexOf('?', lastIndex + 1)) != -1) {
            sb.append(parameterizedCommandJson, lastIndex + 1, index);
            String parameterValue = parameters.get(parameterIndex++);
            sb.append(parameterValue);
            lastIndex = index;
        }
        sb.append(parameterizedCommandJson.substring(lastIndex + 1));
        var command = sb.toString();
        if (LOG.isDebugEnabled()) {
            LOG.debug("BSON command generated: {}", command);
        }
        return command;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // the following methods are used in Hibernate internally and likely we need to override
    // but for now we left them here for placeholders

    @Override
    public void setObject(int parameterIndex, @Nullable Object x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBlob(int parameterIndex, @Nullable Blob x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setClob(int parameterIndex, @Nullable Clob x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setDate(int parameterIndex, @Nullable Date x, @Nullable Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setTime(int parameterIndex, @Nullable Time x, @Nullable Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setTimestamp(int parameterIndex, @Nullable Timestamp x, @Nullable Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setRowId(int parameterIndex, @Nullable RowId x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNString(int parameterIndex, @Nullable String value) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNCharacterStream(int parameterIndex, @Nullable Reader value, long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNClob(int parameterIndex, @Nullable NClob value) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setClob(int parameterIndex, @Nullable Reader reader, long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBlob(int parameterIndex, @Nullable InputStream inputStream, long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNClob(int parameterIndex, @Nullable Reader reader, long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setSQLXML(int parameterIndex, @Nullable SQLXML xmlObject) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBinaryStream(int parameterIndex, @Nullable InputStream x, long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setCharacterStream(int parameterIndex, @Nullable Reader reader, long length) {
        throw new NotYetImplementedException();
    }

}
