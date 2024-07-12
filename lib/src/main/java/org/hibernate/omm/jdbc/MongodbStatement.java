package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import org.bson.Document;
import org.hibernate.omm.jdbc.adapter.StatementAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.jdbc.exception.StatementClosedSQLException;

public class MongodbStatement extends StatementAdapter {

  private final MongoDatabase mongoDatabase;
  private final String collection;

  private volatile boolean closed;

  public MongodbStatement(MongoDatabase mongoDatabase, String collection) {
    this.mongoDatabase = mongoDatabase;
    this.collection = collection;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document commandResult = mongoDatabase.runCommand(command);
    return new MongodbResultSet(mongoDatabase, collection, commandResult);
  }

  @Override
  public int executeUpdate(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document commandResult = mongoDatabase.runCommand(command);
    return commandResult.getInteger("n");
  }

  @Override
  public boolean execute(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document result = mongoDatabase.runCommand(command);
    return result.containsKey("cursor");
  }

  @Override
  public void close() {
    closed = true;
  }

  @Override
  public void cancel() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public SQLWarning getWarnings() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public void clearWarnings() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public ResultSet getResultSet() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public int getUpdateCount() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean getMoreResults() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public void setFetchSize(int rows) throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public int getFetchSize() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public void addBatch(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public void clearBatch() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public int[] executeBatch() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public Connection getConnection() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public ResultSet getGeneratedKeys() throws SimulatedSQLException {
    throwExceptionIfClosed();
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  private void throwExceptionIfClosed() throws StatementClosedSQLException {
    if (closed) {
      throw new StatementClosedSQLException();
    }
  }
}
