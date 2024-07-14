package org.hibernate.omm.jdbc.adapter;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public interface PreparedStatementAdapter extends StatementAdapter, PreparedStatement {

	@Override
  default ResultSet executeQuery() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default int executeUpdate() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNull(int parameterIndex, int sqlType) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setShort(int parameterIndex, short x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setInt(int parameterIndex, int x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setLong(int parameterIndex, long x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBigDecimal(int parameterIndex, BigDecimal x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setString(int parameterIndex, String x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBytes(int parameterIndex, byte[] x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setDate(int parameterIndex, Date x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setTime(int parameterIndex, Time x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setTimestamp(int parameterIndex, Timestamp x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setAsciiStream(int parameterIndex, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setUnicodeStream(int parameterIndex, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBinaryStream(int parameterIndex, InputStream x, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void clearParameters() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setObject(int parameterIndex, Object x, int targetSqlType)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setObject(int parameterIndex, Object x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default boolean execute() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void addBatch() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setCharacterStream(int parameterIndex, Reader reader, int length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setRef(int parameterIndex, Ref x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBlob(int parameterIndex, Blob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setClob(int parameterIndex, Clob x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setArray(int parameterIndex, Array x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default ResultSetMetaData getMetaData() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setDate(int parameterIndex, Date x, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setTime(int parameterIndex, Time x, Calendar cal) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNull(int parameterIndex, int sqlType, String typeName)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setURL(int parameterIndex, URL x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default ParameterMetaData getParameterMetaData() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setRowId(int parameterIndex, RowId x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNString(int parameterIndex, String value) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNCharacterStream(int parameterIndex, Reader value, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNClob(int parameterIndex, NClob value) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setClob(int parameterIndex, Reader reader, long length) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBlob(int parameterIndex, InputStream inputStream, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNClob(int parameterIndex, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setAsciiStream(int parameterIndex, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBinaryStream(int parameterIndex, InputStream x, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setCharacterStream(int parameterIndex, Reader reader, long length)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setAsciiStream(int parameterIndex, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBinaryStream(int parameterIndex, InputStream x) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setCharacterStream(int parameterIndex, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNCharacterStream(int parameterIndex, Reader value) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setClob(int parameterIndex, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setBlob(int parameterIndex, InputStream inputStream) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setNClob(int parameterIndex, Reader reader) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default int getMaxFieldSize() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setMaxFieldSize(int max) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default int getMaxRows() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setMaxRows(int max) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setEscapeProcessing(boolean enable) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default int getQueryTimeout() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setQueryTimeout(int seconds) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  default void setCursorName(String name) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }
}
