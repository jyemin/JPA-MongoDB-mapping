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
import com.mongodb.client.MongoCursor;
import com.mongodb.lang.Nullable;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDecimal128;
import org.bson.BsonDocument;
import org.bson.BsonNumber;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.hibernate.omm.jdbc.adapter.ArrayAdapter;
import org.hibernate.omm.jdbc.adapter.ResultSetAdapter;
import org.hibernate.omm.jdbc.adapter.ResultSetMetaDataAdapter;
import org.hibernate.omm.jdbc.exception.BsonNullValueSQLException;
import org.hibernate.omm.jdbc.exception.CurrentDocumentNullSQLException;
import org.hibernate.omm.jdbc.exception.ResultSetClosedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.util.CollectionUtil;
import org.hibernate.omm.util.TypeUtil;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoResultSet implements ResultSetAdapter {

    private final MongoCursor<BsonDocument> cursor;

    private final List<String> fieldNames;

    @Nullable
    private BsonDocument currentDocument;

    @Nullable
    private BsonValue lastRead;

    private boolean closed;

    public MongoResultSet(final MongoCursor<BsonDocument> cursor, final List<String> fieldNames) {
        Assertions.notNull("cursor", cursor);
        Assertions.notNull("fieldNames", fieldNames);
        this.cursor = cursor;
        this.fieldNames = fieldNames;
    }

    @Override
    public boolean next() throws SimulatedSQLException {
        if (closed) {
            throw new ResultSetClosedSQLException();
        }
        if (cursor.hasNext()) {
            currentDocument = cursor.next().toBsonDocument();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void close() {
        closed = true;
        cursor.close();
    }

    @Override
    public boolean wasNull() throws SimulatedSQLException {
        if (closed) {
            throw new ResultSetClosedSQLException();
        }
        return lastRead != null && lastRead.isNull();
    }

    @Override
    @Nullable
    public String getString(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonString bsonValue = castNonNull(currentDocument).getString(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getValue();
    }

    @Override
    public boolean getBoolean(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonBoolean bsonValue = castNonNull(currentDocument).getBoolean(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.getValue();
    }

    @Override
    public byte getByte(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (byte) bsonValue.intValue();
    }

    @Override
    public short getShort(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (short) bsonValue.intValue();
    }

    @Override
    public int getInt(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.intValue();
    }

    @Override
    public long getLong(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.longValue();
    }

    @Override
    public float getFloat(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (float) bsonValue.doubleValue();
    }

    @Override
    public double getDouble(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonNumber bsonValue = castNonNull(currentDocument).getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.doubleValue();
    }

    @Override
    @Nullable
    public byte[] getBytes(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonBinary bsonValue = castNonNull(currentDocument).getBinary(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getData();
    }

    @Override
    @Nullable
    public Date getDate(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonDateTime bsonValue = castNonNull(currentDocument).getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Date(bsonValue.getValue());
    }

    @Override
    @Nullable
    public Time getTime(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonDateTime bsonValue = castNonNull(currentDocument).getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Time(bsonValue.getValue());
    }

    @Override
    @Nullable
    public Timestamp getTimestamp(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonDateTime bsonValue = castNonNull(currentDocument).getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Timestamp(bsonValue.getValue());
    }

    @Override
    @Nullable
    public BigDecimal getBigDecimal(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        BsonDecimal128 bsonValue = castNonNull(currentDocument).getDecimal128(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getValue().bigDecimalValue();
    }

    @Override
    public Array getArray(final int columnIndex) throws SimulatedSQLException {
        beforeAccessCurrentDocumentField();
        List<BsonValue> bsonValues = castNonNull(currentDocument).getArray(getKey(columnIndex)).getValues();
        return new ArrayAdapter() {

            @Override
            public int getBaseType() {
                return CollectionUtil.isEmpty(bsonValues)
                        ? Types.NULL
                        : TypeUtil.getJdbcType(bsonValues.get(0).getBsonType());
            }

            @Override
            public Object getArray() {
                return bsonValues.stream().map(TypeUtil::unwrap).toArray();
            }
        };
    }

    @Override
    @Nullable
    public Object getObject(final int columnIndex, final Class type) throws SimulatedSQLException {
        Assertions.notNull("type", type);
        beforeAccessCurrentDocumentField();
        var value = castNonNull(currentDocument).get(getKey(columnIndex));
        return value == null ? null : type.cast(value);
    }

    @Override
    public ResultSetMetaData getMetaData() {
        return new ResultSetMetaDataAdapter() {

            @Override
            public int getColumnCount() throws SimulatedSQLException {
                return fieldNames.size();
            }

            @Override
            public String getColumnLabel(final int column) throws SimulatedSQLException {
                return fieldNames.get(column - 1);
            }
        };
    }

    @Override
    public int findColumn(final String columnLabel) {
        Assertions.notNull("columnLabel", columnLabel);
        return fieldNames.indexOf(columnLabel) + 1;
    }

    private String getKey(final int columnIndex) {
        return fieldNames.get(columnIndex - 1);
    }

    private void beforeAccessCurrentDocumentField() throws ResultSetClosedSQLException, CurrentDocumentNullSQLException {
        if (closed) {
            throw new ResultSetClosedSQLException();
        }
        if (currentDocument == null) {
            throw new CurrentDocumentNullSQLException();
        }
    }

}
