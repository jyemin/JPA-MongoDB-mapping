package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoCursor;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import org.bson.Document;
import org.hibernate.omm.jdbc.adapter.ResultSetAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulateSQLException;

public class MongodbResultSet extends ResultSetAdapter {

  private final MongoCursor<? extends Document> mongoCursor;
  private Document currentDocument;
  private List<String> currentDocumentKeys = Collections.emptyList();

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
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public Object getObject(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, Object.class);
  }

  @Override
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, BigDecimal.class);
  }

  @Override
  public Blob getBlob(int columnIndex) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Clob getClob(int columnIndex) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Array getArray(int columnIndex) throws SQLException {
    return getReferenceTypeValue(columnIndex, Array.class);
  }

  @Override
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public URL getURL(int columnIndex) throws SQLException {
    try {
      return new URL(getString(columnIndex));
    } catch (MalformedURLException e) {
      throw new SimulateSQLException("invalid URL", e);
    }
  }

  @Override
  public NClob getNClob(int columnIndex) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return getReferenceTypeValue(columnIndex, type);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new NotSupportedSQLException();
  }

  private <T> T getPrimitiveTypeValue(int columnIndex, Class<T> type) throws SQLException {
    if (currentDocument == null) {
      throw new SimulateSQLException("current document is null");
    }
    T value = currentDocument.get(currentDocumentKeys.get(columnIndex), type);
    if (value == null) {
      throw new SimulateSQLException("null value at column index: " + columnIndex);
    }
    return value;
  }

  private <T> T getReferenceTypeValue(int columnIndex, Class<T> type) throws SQLException {
    if (currentDocument == null) {
      throw new SimulateSQLException("current document is null");
    }
    try {
      return currentDocument.get(currentDocumentKeys.get(columnIndex), type);
    } catch (ClassCastException cce) {
      throw new SimulateSQLException("class cast failure", cce);
    }
  }
}
