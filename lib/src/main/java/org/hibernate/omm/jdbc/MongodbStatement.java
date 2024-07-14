package org.hibernate.omm.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.util.List;
import org.bson.BsonDocument;
import org.bson.Document;
import org.hibernate.omm.jdbc.adapter.StatementAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;
import org.hibernate.omm.jdbc.exception.StatementClosedSQLException;

public class MongodbStatement implements StatementAdapter, MongodbJdbcContextAware {

  private static class CurrentQueryResult {
    private final ResultSet resultSet;
    private final long cursorId;

    public CurrentQueryResult(ResultSet resultSet, long cursorId) {
      this.resultSet = resultSet;
      this.cursorId = cursorId;
    }
  }

  private final MongodbJdbcContext mongodbJdbcContext;
  private CurrentQueryResult currentQueryResult;
  private int currentUpdateCount;
  private int fetchSize;

  private volatile boolean closed;

  public MongodbStatement(MongodbJdbcContext mongodbJdbcContext) {
    this.mongodbJdbcContext = mongodbJdbcContext;
  }

  @Override
  public MongodbJdbcContext getMongodbJdbcContext() {
    return mongodbJdbcContext;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document commandResult = mongodbJdbcContext.getMongoDatabase().runCommand(command);
    if (commandResult.getInteger("ok") != 1) {
      throw new CommandRunFailSQLException();
    }
    return new MongodbResultSet(mongodbJdbcContext, commandResult);
  }

  @Override
  public int executeUpdate(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document commandResult = mongodbJdbcContext.getMongoDatabase().runCommand(command);
    if (commandResult.getInteger("ok") != 1) {
      throw new CommandRunFailSQLException();
    }
    return commandResult.getInteger("n");
  }

  @Override
  public boolean execute(String sql) throws SimulatedSQLException {
    throwExceptionIfClosed();
    Document command = Document.parse(sql);
    Document commandResult = mongodbJdbcContext.getMongoDatabase().runCommand(command);
    if (commandResult.getInteger("ok") != 1) {
      throw new CommandRunFailSQLException();
    }
    Document cursor = commandResult.get("cursor", Document.class);
    if (cursor != null) {
      long cursorId = cursor.getLong("id");
      ResultSet resultSet =
          new MongodbResultSet(
              mongodbJdbcContext, cursor.getList("firstBatch", BsonDocument.class));
      currentQueryResult = new CurrentQueryResult(resultSet, cursorId);
      currentUpdateCount = -1;
      return true;
    } else {
      currentQueryResult = null;
      currentUpdateCount = commandResult.getInteger("n");
      return false;
    }
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
  public ResultSet getResultSet() {
    return currentQueryResult == null ? null : currentQueryResult.resultSet;
  }

  @Override
  public int getUpdateCount() {
    return currentUpdateCount;
  }

  @Override
  public boolean getMoreResults() throws SimulatedSQLException {
    if (currentQueryResult != null) {
      Document moreResultsCommand =
          new Document()
              .append("getMore", currentQueryResult.cursorId)
              .append("collection", mongodbJdbcContext.getCollection());
      if (fetchSize != 0) {
        moreResultsCommand.append("batchSize", fetchSize);
      }
      Document moreResults = mongodbJdbcContext.getMongoDatabase().runCommand(moreResultsCommand);
      if (moreResults.getInteger("ok") != 1) {
        throw new CommandRunFailSQLException();
      }
      Document cursor = moreResults.get("cursor", Document.class);
      List<BsonDocument> nextBatch = cursor.getList("nextBatch", BsonDocument.class);
      long cursorId = cursor.getLong("id");
      currentQueryResult =
          new CurrentQueryResult(new MongodbResultSet(mongodbJdbcContext, nextBatch), cursorId);
      return !nextBatch.isEmpty();
    } else {
      return false;
    }
  }

  @Override
  public void setFetchSize(int rows) throws SimulatedSQLException {
    throwExceptionIfClosed();
    this.fetchSize = rows;
  }

  @Override
  public int getFetchSize() throws SimulatedSQLException {
    throwExceptionIfClosed();
    return this.fetchSize;
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
  public Connection getConnection() {
    return mongodbJdbcContext.getConnection();
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
