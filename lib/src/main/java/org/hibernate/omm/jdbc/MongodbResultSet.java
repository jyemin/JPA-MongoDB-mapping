package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoCursor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.bson.*;
import org.hibernate.omm.jdbc.adapter.ResultSetAdapter;
import org.hibernate.omm.jdbc.exception.BsonNullValueException;

public class MongodbResultSet extends ResultSetAdapter {

  private final MongoCursor<BsonDocument> mongoCursor;
  private BsonDocument currentDocument;
  private List<String> currentDocumentKeys = Collections.emptyList();
  private BsonValue lastRead;

  public MongodbResultSet(MongoCursor<BsonDocument> mongoCursor) {
    this.mongoCursor = mongoCursor;
  }

  @Override
  public boolean next() {
    try {
      currentDocument = mongoCursor.next();
      currentDocumentKeys = new ArrayList<>(currentDocument.keySet());
      return true;
    } catch (NoSuchElementException ignored) {
      return false;
    }
  }

  @Override
  public void close() {
    mongoCursor.close();
  }

  @Override
  public boolean wasNull() {
    return lastRead != null && lastRead.isNull();
  }

  @Override
  public String getString(int columnIndex) {
    BsonString bsonValue = currentDocument.getString(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : bsonValue.getValue();
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SQLException {
    BsonBoolean bsonValue = currentDocument.getBoolean(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return bsonValue.getValue();
  }

  @Override
  public byte getByte(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return (byte) bsonValue.intValue();
  }

  @Override
  public short getShort(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return (short) bsonValue.intValue();
  }

  @Override
  public int getInt(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return bsonValue.intValue();
  }

  @Override
  public long getLong(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return bsonValue.longValue();
  }

  @Override
  public float getFloat(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return (float) bsonValue.doubleValue();
  }

  @Override
  public double getDouble(int columnIndex) throws SQLException {
    BsonNumber bsonValue = currentDocument.getNumber(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    if (bsonValue.isNull()) {
      throw new BsonNullValueException();
    }
    return bsonValue.doubleValue();
  }

  @Override
  public byte[] getBytes(int columnIndex) {
    BsonBinary bsonValue = currentDocument.getBinary(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : bsonValue.getData();
  }

  @Override
  public Date getDate(int columnIndex) {
    BsonDateTime bsonValue = currentDocument.getDateTime(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : new Date(bsonValue.getValue());
  }

  @Override
  public Time getTime(int columnIndex) {
    BsonDateTime bsonValue = currentDocument.getDateTime(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : new Time(bsonValue.getValue());
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) {
    BsonDateTime bsonValue = currentDocument.getDateTime(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : new Timestamp(bsonValue.getValue());
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) {
    BsonDecimal128 bsonValue = currentDocument.getDecimal128(currentDocumentKeys.get(columnIndex));
    lastRead = bsonValue;
    return bsonValue.isNull() ? null : bsonValue.getValue().bigDecimalValue();
  }
}
