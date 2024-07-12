package org.hibernate.omm.jdbc;

import java.math.BigDecimal;
import org.bson.BsonValue;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public interface JdbcCodec {

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
  // TODO add more accessors
}
