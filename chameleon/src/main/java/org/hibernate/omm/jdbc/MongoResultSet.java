package org.hibernate.omm.jdbc;

import com.mongodb.lang.Nullable;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDecimal128;
import org.bson.BsonDocument;
import org.bson.BsonNumber;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.assertions.Assertions;
import org.hibernate.omm.jdbc.adapter.ArrayAdapter;
import org.hibernate.omm.jdbc.adapter.ResultSetAdapter;
import org.hibernate.omm.jdbc.adapter.ResultSetMetaDataAdapter;
import org.hibernate.omm.jdbc.exception.BsonNullValueSQLException;
import org.hibernate.omm.jdbc.exception.ColumnInfoUnknownSQLException;
import org.hibernate.omm.jdbc.exception.ResultSetClosedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.util.TypeUtil;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoResultSet implements ResultSetAdapter {

    private final Iterator<Document> documentsIterator;
    private @Nullable BsonDocument currentDocument;
    private @Nullable List<String> currentDocumentKeys;
    private @Nullable BsonValue lastRead;
    private volatile boolean closed;

    public MongoResultSet(Document commandResult) {
        Assertions.notNull("commandResult", commandResult);
        var firstBatch = commandResult
                .get("cursor", Document.class)
                .getList("firstBatch", Document.class);
        this.documentsIterator = firstBatch.iterator();
        if (!firstBatch.isEmpty()) {
            this.currentDocumentKeys = List.copyOf(firstBatch.get(0).keySet());
        } else {
            this.currentDocumentKeys = null;
        }
    }

    public MongoResultSet(Iterable<Document> documentIterable) {
        Assertions.notNull("documentIterable", documentIterable);
        this.documentsIterator = documentIterable.iterator();
    }

    @Override
    public boolean next() throws SimulatedSQLException {
        throwExceptionIfClosed();
        if (documentsIterator.hasNext()) {
            currentDocument = documentsIterator.next().toBsonDocument();
            currentDocumentKeys = List.copyOf(currentDocument.keySet());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean wasNull() throws SimulatedSQLException {
        throwExceptionIfClosed();
        return lastRead != null && lastRead.isNull();
    }

    @Override
    public @Nullable String getString(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonString bsonValue = currentDocument.getString(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getValue();
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonBoolean bsonValue = currentDocument.getBoolean(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.getValue();
    }

    @Override
    public byte getByte(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (byte) bsonValue.intValue();
    }

    @Override
    public short getShort(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (short) bsonValue.intValue();
    }

    @Override
    public int getInt(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.intValue();
    }

    @Override
    public long getLong(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.longValue();
    }

    @Override
    public float getFloat(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return (float) bsonValue.doubleValue();
    }

    @Override
    public double getDouble(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonNumber bsonValue = currentDocument.getNumber(getKey(columnIndex));
        lastRead = bsonValue;
        if (bsonValue.isNull()) {
            throw new BsonNullValueSQLException();
        }
        return bsonValue.doubleValue();
    }

    @Override
    public @Nullable byte[] getBytes(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonBinary bsonValue = currentDocument.getBinary(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getData();
    }

    @Override
    public @Nullable Date getDate(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonDateTime bsonValue = currentDocument.getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Date(bsonValue.getValue());
    }

    @Override
    public @Nullable Time getTime(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonDateTime bsonValue = currentDocument.getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Time(bsonValue.getValue());
    }

    @Override
    public @Nullable Timestamp getTimestamp(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonDateTime bsonValue = currentDocument.getDateTime(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : new Timestamp(bsonValue.getValue());
    }

    @Override
    public @Nullable BigDecimal getBigDecimal(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        throwExceptionIfClosed();
        BsonDecimal128 bsonValue = currentDocument.getDecimal128(getKey(columnIndex));
        lastRead = bsonValue;
        return bsonValue.isNull() ? null : bsonValue.getValue().bigDecimalValue();
    }

    @Override
    public Array getArray(int columnIndex) throws SimulatedSQLException {
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        List<BsonValue> bsonValues = currentDocument.getArray(getKey(columnIndex)).getValues();
        return new ArrayAdapter() {
            @Override
            public int getBaseType() {
                return bsonValues == null || bsonValues.isEmpty() ?
                        Types.NULL :
                        TypeUtil.getJdbcType(bsonValues.get(0).getBsonType());
            }

            @Override
            public Object getArray() {
                return bsonValues.stream().map(TypeUtil::unwrap).toArray();
            }
        };
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SimulatedSQLException {
        Assertions.notNull("type", type);
        Assertions.isTrue("currentDocument not null", currentDocument != null);
        return type.cast(currentDocument.get(getKey(columnIndex)));
    }

    @Override
    public ResultSetMetaData getMetaData() {
        return new ResultSetMetaDataAdapter() {
            @Override
            public int getColumnCount() throws SimulatedSQLException {
                if (currentDocumentKeys == null) {
                    throw new ColumnInfoUnknownSQLException();
                }
                return currentDocumentKeys.size();
            }

            @Override
            public String getColumnLabel(final int column) throws SimulatedSQLException {
                if (currentDocumentKeys == null) {
                    throw new ColumnInfoUnknownSQLException();
                }
                return currentDocumentKeys.get(column - 1);
            }
        };
    }

    @Override
    public int findColumn(String columnLabel) throws SimulatedSQLException {
        if (currentDocumentKeys == null) {
            throw new ColumnInfoUnknownSQLException();
        }
        return currentDocumentKeys.indexOf(columnLabel) + 1;
    }

    private String getKey(int columnIndex) throws SimulatedSQLException {
        if (currentDocumentKeys == null) {
            throw new ColumnInfoUnknownSQLException();
        }
        return currentDocumentKeys.get(columnIndex - 1);
    }

    private void throwExceptionIfClosed() throws ResultSetClosedSQLException {
        if (closed) {
            throw new ResultSetClosedSQLException();
        }
    }
}
