package org.hibernate.omm.jdbc;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import org.bson.BsonValue;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public interface JdbcTypeCodec {

  boolean isNull(BsonValue bsonValue) throws SimulatedSQLException;

  void setNull(BsonValue bsonValue) throws SimulatedSQLException;

  String getString(BsonValue bsonValue) throws SimulatedSQLException;

  void setString(BsonValue bsonValue, String x) throws SimulatedSQLException;

  boolean getBoolean(BsonValue bsonValue) throws SimulatedSQLException;

  void setBoolean(BsonValue bsonValue, boolean x) throws SimulatedSQLException;

  byte getByte(BsonValue bsonValue) throws SimulatedSQLException;

  void setByte(BsonValue bsonValue, byte x) throws SimulatedSQLException;

  short getShort(BsonValue bsonValue) throws SimulatedSQLException;

  void setShort(BsonValue bsonValue, short x) throws SimulatedSQLException;

  int getInt(BsonValue bsonValue) throws SimulatedSQLException;

  void setInt(BsonValue bsonValue, int x) throws SimulatedSQLException;

  long getLong(BsonValue bsonValue) throws SimulatedSQLException;

  void setLong(BsonValue bsonValue, long x) throws SimulatedSQLException;

  float getFloat(BsonValue bsonValue) throws SimulatedSQLException;

  void setFloat(BsonValue bsonValue, float x) throws SimulatedSQLException;

  double getDouble(BsonValue bsonValue) throws SimulatedSQLException;

  void setDouble(BsonValue bsonValue, double x) throws SimulatedSQLException;

  BigDecimal getBigDecimal(BsonValue bsonValue, int scale) throws SimulatedSQLException;

  void setBigDecimal(BsonValue bsonValue, BigDecimal x) throws SimulatedSQLException;

  byte[] getBytes(BsonValue bsonValue) throws SimulatedSQLException;

  void setBytes(BsonValue bsonValue, byte[] x) throws SimulatedSQLException;

  java.sql.Date getDate(BsonValue bsonValue) throws SimulatedSQLException;

  void setDate(BsonValue bsonValue, java.sql.Date x) throws SimulatedSQLException;

  java.sql.Timestamp getTimestamp(BsonValue bsonValue) throws SimulatedSQLException;

  void setTimestamp(BsonValue bsonValue, java.sql.Timestamp x) throws SimulatedSQLException;

  java.io.InputStream getAsciiStream(BsonValue bsonValue) throws SimulatedSQLException;

  void setAsciiStream(BsonValue bsonValue, java.io.InputStream x) throws SimulatedSQLException;

  java.io.InputStream getUnicodeStream(BsonValue bsonValue) throws SimulatedSQLException;

  void setUnicodeStream(BsonValue bsonValue, java.io.InputStream x) throws SimulatedSQLException;

  java.io.InputStream getBinaryStream(BsonValue bsonValue) throws SimulatedSQLException;

  void setBinaryStream(BsonValue bsonValue, java.io.InputStream x) throws SimulatedSQLException;

  Object getObject(BsonValue bsonValue) throws SimulatedSQLException;

  void setObject(BsonValue bsonValue, Object x) throws SimulatedSQLException;

  // --------------------------JDBC 2.0-----------------------------------
  java.io.Reader getCharacterStream(BsonValue bsonValue) throws SimulatedSQLException;

  void setCharacterStream(BsonValue bsonValue, java.io.Reader x) throws SimulatedSQLException;

  BigDecimal getBigDecimal(BsonValue bsonValue) throws SimulatedSQLException;

  Blob getBlob(BsonValue bsonValue) throws SimulatedSQLException;

  void setBlob(BsonValue bsonValue, Blob x) throws SimulatedSQLException;

  Clob getClob(BsonValue bsonValue) throws SimulatedSQLException;

  void setClob(BsonValue bsonValue, Clob x) throws SimulatedSQLException;

  Array getArray(BsonValue bsonValue) throws SimulatedSQLException;

  void setArray(BsonValue bsonValue, Array x) throws SimulatedSQLException;

  java.sql.Date getDate(BsonValue bsonValue, Calendar cal) throws SimulatedSQLException;

  java.sql.Time getTime(BsonValue bsonValue, Calendar cal) throws SimulatedSQLException;

  java.sql.Timestamp getTimestamp(BsonValue bsonValue, Calendar cal) throws SimulatedSQLException;

  // -------------------------- JDBC 3.0 ----------------------------------------
  java.net.URL getURL(BsonValue bsonValue) throws SimulatedSQLException;

  void setURL(BsonValue bsonValue, java.net.URL x) throws SimulatedSQLException;

  RowId getRowId(BsonValue bsonValue) throws SimulatedSQLException;

  void setRowId(BsonValue bsonValue, RowId x) throws SimulatedSQLException;

  NClob getNClob(BsonValue bsonValue) throws SimulatedSQLException;

  void setNClob(BsonValue bsonValue, NClob x) throws SimulatedSQLException;

  SQLXML getSQLXML(BsonValue bsonValue) throws SimulatedSQLException;

  void setSQLXML(BsonValue bsonValue, SQLXML x) throws SimulatedSQLException;

  String getNString(BsonValue bsonValue) throws SimulatedSQLException;

  void setNString(BsonValue bsonValue, String x) throws SimulatedSQLException;

  java.io.Reader getNCharacterStream(BsonValue bsonValue) throws SimulatedSQLException;

  void setNCharacterStream(BsonValue bsonValue, java.io.Reader x) throws SimulatedSQLException;

  // ------------------------- JDBC 4.1 -----------------------------------
  <T> T getBoject(BsonValue bsonValue, Class<T> type) throws SimulatedSQLException;

  void setObject(BsonValue bsonValue, Object x, int targetSqlType) throws SimulatedSQLException;

  // ------------------------- JDBC 4.2 -----------------------------------

}
