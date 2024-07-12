package org.hibernate.omm.jdbc.adapter;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSqlClientInfoSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class ConnectionAdapter implements Connection {
  @Override
  public Statement createStatement() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String nativeSQL(String sql) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean getAutoCommit() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void commit() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void rollback() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void close() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isClosed() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isReadOnly() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setCatalog(String catalog) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getCatalog() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setTransactionIsolation(int level) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getTransactionIsolation() throws SimulatedSQLException {
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
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setHoldability(int holdability) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getHoldability() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Savepoint setSavepoint() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void rollback(Savepoint savepoint) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Statement createStatement(
      int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(
      String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public CallableStatement prepareCall(
      String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames)
      throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Clob createClob() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Blob createBlob() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public NClob createNClob() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLXML createSQLXML() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isValid(int timeout) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    throw new NotSupportedSqlClientInfoSQLException();
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    throw new NotSupportedSqlClientInfoSQLException();
  }

  @Override
  public String getClientInfo(String name) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Properties getClientInfo() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setSchema(String schema) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String getSchema() throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void abort(Executor executor) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getNetworkTimeout() throws SimulatedSQLException {
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
