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
import org.bson.Document;
import org.hibernate.omm.jdbc.adapter.ResultSetAdapter;
import org.hibernate.omm.jdbc.exception.SimulateSQLException;

public class MongodbResultSet extends ResultSetAdapter {

  private final MongoCursor<? extends Document> mongoCursor;
  private Document currentDocument;
  private List<String> currentDocumentKeys = Collections.emptyList();
  private boolean wasNull;

  public MongodbResultSet(MongoCursor<? extends Document> mongoCursor) {
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
  public boolean wasNull() throws SQLException {
    return wasNull;
  }

  @Override
  public String getString(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, String.class);
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Boolean.class);
  }

  @Override
  public byte getByte(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Byte.class);
  }

  @Override
  public short getShort(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Short.class);
  }

  @Override
  public int getInt(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Integer.class);
  }

  @Override
  public long getLong(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Long.class);
  }

  @Override
  public float getFloat(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Float.class);
  }

  @Override
  public double getDouble(int columnIndex) throws SQLException {
    return getPrimitiveTypeValue(columnIndex, Double.class);
  }

  @Override
  public byte[] getBytes(int columnIndex) throws SQLException {
    return (byte[]) getReferenceTypeValue(columnIndex, Object.class);
  }

  @Override
  public Date getDate(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, Date.class);
  }

  @Override
  public Time getTime(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, Time.class);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, Timestamp.class);
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, BigDecimal.class);
  }

  private <T> T getPrimitiveTypeValue(int columnIndex, Class<T> type) throws SQLException {
    if (currentDocument == null) {
      throw new SimulateSQLException("current document is null");
    }
    T value = currentDocument.get(currentDocumentKeys.get(columnIndex), type);
    wasNull = value == null;
    if (value == null) {
      return type.cast(0);
    }
    return value;
  }

  private <T> T getReferenceTypeValue(int columnIndex, Class<T> type) throws SQLException {
    if (currentDocument == null) {
      throw new SimulateSQLException("current document is null");
    }
    try {
      T value = currentDocument.get(currentDocumentKeys.get(columnIndex), type);
      wasNull = value == null;
      return value;
    } catch (ClassCastException cce) {
      throw new SimulateSQLException("class cast failure", cce);
    }
  }
}
