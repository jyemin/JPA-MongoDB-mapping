package org.hibernate.omm.jdbc.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import org.bson.BsonValue;
import org.hibernate.omm.jdbc.JdbcCodec;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class JdbcCodecImpl implements JdbcCodec {

  @Override
  public boolean isNull(BsonValue bsonValue) throws SimulatedSQLException {
    return false;
  }

  @Override
  public void setNull(BsonValue bsonValue) throws SimulatedSQLException {}

  @Override
  public String getString(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setString(BsonValue bsonValue, String x) throws SimulatedSQLException {}

  @Override
  public boolean getBoolean(BsonValue bsonValue) throws SimulatedSQLException {
    return false;
  }

  @Override
  public void setBoolean(BsonValue bsonValue, boolean x) throws SimulatedSQLException {}

  @Override
  public byte getByte(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setByte(BsonValue bsonValue, byte x) throws SimulatedSQLException {}

  @Override
  public short getShort(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setShort(BsonValue bsonValue, short x) throws SimulatedSQLException {}

  @Override
  public int getInt(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setInt(BsonValue bsonValue, int x) throws SimulatedSQLException {}

  @Override
  public long getLong(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setLong(BsonValue bsonValue, long x) throws SimulatedSQLException {}

  @Override
  public float getFloat(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setFloat(BsonValue bsonValue, float x) throws SimulatedSQLException {}

  @Override
  public double getDouble(BsonValue bsonValue) throws SimulatedSQLException {
    return 0;
  }

  @Override
  public void setDouble(BsonValue bsonValue, double x) throws SimulatedSQLException {}

  @Override
  public BigDecimal getBigDecimal(BsonValue bsonValue, int scale) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setBigDecimal(BsonValue bsonValue, BigDecimal x) throws SimulatedSQLException {}

  @Override
  public byte[] getBytes(BsonValue bsonValue) throws SimulatedSQLException {
    return new byte[0];
  }

  @Override
  public void setBytes(BsonValue bsonValue, byte[] x) throws SimulatedSQLException {}

  @Override
  public Date getDate(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setDate(BsonValue bsonValue, Date x) throws SimulatedSQLException {}

  @Override
  public Timestamp getTimestamp(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setTimestamp(BsonValue bsonValue, Timestamp x) throws SimulatedSQLException {}

  @Override
  public InputStream getAsciiStream(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setAsciiStream(BsonValue bsonValue, InputStream x) throws SimulatedSQLException {}

  @Override
  public InputStream getUnicodeStream(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setUnicodeStream(BsonValue bsonValue, InputStream x) throws SimulatedSQLException {}

  @Override
  public InputStream getBinaryStream(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setBinaryStream(BsonValue bsonValue, InputStream x) throws SimulatedSQLException {}

  @Override
  public Object getObject(BsonValue bsonValue) throws SimulatedSQLException {
    return null;
  }

  @Override
  public void setObject(BsonValue bsonValue, Object x) throws SimulatedSQLException {}
}
