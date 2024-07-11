package org.hibernate.omm.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import org.hibernate.omm.jdbc.adapter.StatementAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;

public class MongodbStatement extends StatementAdapter {

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void close() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void cancel() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void clearWarnings() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getUpdateCount() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int getFetchSize() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public void clearBatch() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public Connection getConnection() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isClosed() throws SQLException {
    throw new NotSupportedSQLException();
  }
}
