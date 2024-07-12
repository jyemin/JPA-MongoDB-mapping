package org.hibernate.omm.jdbc.adapter;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class ResultSetAdapter implements ResultSet {

  @Override
  public boolean next() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void close() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean wasNull() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getString(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public byte getByte(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public short getShort(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getInt(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public long getLong(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public float getFloat(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public double getDouble(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public byte[] getBytes(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Date getDate(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Time getTime(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getAsciiStream(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getUnicodeStream(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getBinaryStream(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getString(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean getBoolean(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public byte getByte(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public short getShort(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getInt(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public long getLong(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public float getFloat(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public double getDouble(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel, int scale) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public byte[] getBytes(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Date getDate(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Time getTime(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getAsciiStream(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getUnicodeStream(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public InputStream getBinaryStream(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLWarning getWarnings() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void clearWarnings() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getCursorName() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public ResultSetMetaData getMetaData() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Object getObject(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Object getObject(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int findColumn(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Reader getCharacterStream(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isBeforeFirst() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isAfterLast() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isFirst() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isLast() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void beforeFirst() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void afterLast() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean first() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean last() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean absolute(int row) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean relative(int rows) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean previous() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setFetchDirection(int direction) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getFetchDirection() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setFetchSize(int rows) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getFetchSize() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getType() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getConcurrency() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean rowUpdated() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean rowInserted() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean rowDeleted() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNull(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBoolean(int columnIndex, boolean x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateByte(int columnIndex, byte x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateShort(int columnIndex, short x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateInt(int columnIndex, int x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateLong(int columnIndex, long x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateFloat(int columnIndex, float x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateDouble(int columnIndex, double x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateString(int columnIndex, String x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBytes(int columnIndex, byte[] x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateDate(int columnIndex, Date x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateTime(int columnIndex, Time x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateTimestamp(int columnIndex, Timestamp x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateObject(int columnIndex, Object x, int scaleOrLength)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateObject(int columnIndex, Object x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNull(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBoolean(String columnLabel, boolean x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateByte(String columnLabel, byte x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateShort(String columnLabel, short x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateInt(String columnLabel, int x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateLong(String columnLabel, long x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateFloat(String columnLabel, float x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateDouble(String columnLabel, double x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBigDecimal(String columnLabel, BigDecimal x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateString(String columnLabel, String x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBytes(String columnLabel, byte[] x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateDate(String columnLabel, Date x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateTime(String columnLabel, Time x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateTimestamp(String columnLabel, Timestamp x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateObject(String columnLabel, Object x, int scaleOrLength)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateObject(String columnLabel, Object x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void insertRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void deleteRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void refreshRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void cancelRowUpdates() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void moveToInsertRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void moveToCurrentRow() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Statement getStatement() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Ref getRef(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Blob getBlob(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Clob getClob(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Array getArray(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Object getObject(String columnLabel, Map<String, Class<?>> map)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Ref getRef(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Blob getBlob(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Clob getClob(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Array getArray(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Date getDate(int columnIndex, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Date getDate(String columnLabel, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Time getTime(int columnIndex, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Time getTime(String columnLabel, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public URL getURL(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public URL getURL(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateRef(int columnIndex, Ref x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateRef(String columnLabel, Ref x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(int columnIndex, Blob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(String columnLabel, Blob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(int columnIndex, Clob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(String columnLabel, Clob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateArray(int columnIndex, Array x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateArray(String columnLabel, Array x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public RowId getRowId(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public RowId getRowId(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateRowId(int columnIndex, RowId x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateRowId(String columnLabel, RowId x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getHoldability() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isClosed() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNString(int columnIndex, String nString) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNString(String columnLabel, String nString) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(int columnIndex, NClob nClob) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(String columnLabel, NClob nClob) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public NClob getNClob(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public NClob getNClob(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLXML getSQLXML(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLXML getSQLXML(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getNString(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getNString(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Reader getNCharacterStream(int columnIndex) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Reader getNCharacterStream(String columnLabel) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(int columnIndex, Reader reader, long length) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(String columnLabel, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(int columnIndex, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateClob(String columnLabel, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }
}
