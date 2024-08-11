/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.jdbc;

import com.mongodb.assertions.Assertions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
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
            final MongoDatabase mongoDatabase,
            final ClientSession clientSession,
            final Connection connection,
            final String parameterizedCommandJson) {
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

    @Override
    public void addBatch() {
        // no-op
    }

    @Override
    public void setNull(final int parameterIndex, final int sqlType) {
        parameters.put(parameterIndex, "null");
    }

    @Override
    public void setBoolean(final int parameterIndex, final boolean x) {
        parameters.put(parameterIndex, Boolean.toString(x));
    }

    @Override
    public void setByte(final int parameterIndex, final byte x) {
        parameters.put(parameterIndex, Byte.toString(x));
    }

    @Override
    public void setShort(final int parameterIndex, final short x) {
        parameters.put(parameterIndex, Short.toString(x));
    }

    @Override
    public void setInt(final int parameterIndex, final int x) {
        parameters.put(parameterIndex, "{ \"$numberInt\": \"" + x + "\" }");
    }

    @Override
    public void setLong(final int parameterIndex, final long x) {
        parameters.put(parameterIndex, "{ \"$numberLong\": \"" + x + "\" }");
    }

    @Override
    public void setFloat(final int parameterIndex, final float x) {
        parameters.put(parameterIndex, Float.toString(x));
    }

    @Override
    public void setDouble(final int parameterIndex, final double x) {
        parameters.put(parameterIndex, "{ \"$numberDouble\": \"" + x + "\" }");
    }

    @Override
    public void setBigDecimal(final int parameterIndex, @Nullable final BigDecimal x) {
        parameters.put(parameterIndex, x == null ? "null" : "{ \"$numberDecimal\": \"" + x + "\" }");
    }

    @Override
    public void setString(final int parameterIndex, @Nullable final String x) {
        parameters.put(parameterIndex, x == null ? "null" : StringUtil.writeStringHelper(x));
    }

    @Override
    public void setBytes(final int parameterIndex, @Nullable final byte[] x) {
        parameters.put(parameterIndex, x == null ? "null" : "\"$binary\": {\"base64\": \"" + Base64.getEncoder().encodeToString(x) + "\", \"subType\": \"0\"}");
    }

    @Override
    public void setDate(final int parameterIndex, @Nullable final Date x) {
        parameters.put(parameterIndex, x == null ? "null" : "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTime(final int parameterIndex, @Nullable final Time x) {
        parameters.put(parameterIndex, x == null ? "null" : "{\"$date\": {\"$numberLong\": \"" + x.toInstant().getEpochSecond() + "\"}}");
    }

    @Override
    public void setTimestamp(final int parameterIndex, @Nullable final Timestamp x) {
        parameters.put(
                parameterIndex,
                x == null ? "null" : "{\"$timestamp\": {\"" + x.toInstant().getEpochSecond() + "\": <t>, \"i\": 1}}"
        );
    }

    @Override
    public void setObject(final int parameterIndex, @Nullable final Object x, final int targetSqlType)
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
    public void setArray(final int parameterIndex, final Array x) throws SimulatedSQLException {
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

    private String getArrayJson(final Iterable<?> iterable) throws IOException {
        var stringWriter = new CharArrayWriter();
        stringWriter.write("[ ");
        var first = true;
        for (Object obj : iterable) {
            if (!first) {
                stringWriter.write(", ");
            } else {
                first = false;
            }
            stringWriter.write(obj instanceof String aStr
                    ? StringUtil.writeStringHelper(aStr)
                    : obj == null ? "null" : obj.toString());
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
    public void setObject(final int parameterIndex, @Nullable final Object x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBlob(final int parameterIndex, @Nullable final Blob x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setClob(final int parameterIndex, @Nullable final Clob x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setDate(final int parameterIndex, @Nullable final Date x, @Nullable final Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setTime(final int parameterIndex, @Nullable final Time x, @Nullable final Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setTimestamp(final int parameterIndex, @Nullable final Timestamp x, @Nullable final Calendar cal) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setRowId(final int parameterIndex, @Nullable final RowId x) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNString(final int parameterIndex, @Nullable final String value) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNCharacterStream(final int parameterIndex, @Nullable final Reader value, final long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNClob(final int parameterIndex, @Nullable final NClob value) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setClob(final int parameterIndex, @Nullable final Reader reader, final long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBlob(final int parameterIndex, @Nullable final InputStream inputStream, final long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setNClob(final int parameterIndex, @Nullable final Reader reader, final long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setSQLXML(final int parameterIndex, @Nullable final SQLXML xmlObject) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setBinaryStream(final int parameterIndex, @Nullable final InputStream x, final long length) {
        throw new NotYetImplementedException();
    }

    @Override
    public void setCharacterStream(final int parameterIndex, @Nullable final Reader reader, final long length) {
        throw new NotYetImplementedException();
    }

}
