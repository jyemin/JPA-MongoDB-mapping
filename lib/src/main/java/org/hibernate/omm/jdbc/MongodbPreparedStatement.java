package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import java.sql.ResultSet;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class MongodbPreparedStatement extends PreparedStatementAdapter {

  private final MongoDatabase mongoDatabase;
  private final String collection;
  private final Bson command;

  public MongodbPreparedStatement(MongoDatabase mongoDatabase, String collection, Bson command) {
    this.mongoDatabase = mongoDatabase;
    this.collection = collection;
    this.command = command;
  }

  @Override
  public ResultSet executeQuery() throws SimulatedSQLException {
    Document commandResult = runCommand();
    return new MongodbResultSet(mongoDatabase, collection, commandResult);
  }

  @Override
  public int executeUpdate() throws SimulatedSQLException {
    Document result = runCommand();
    return result.getInteger("n");
  }

  @Override
  public boolean execute() throws SimulatedSQLException {
    Document result = runCommand();
    return result.containsKey("cursor");
  }

  /**
   * @see org.hibernate.engine.jdbc.batch.internal.BatchImpl#addToBatch(JdbcValueBindings,
   *     TableInclusionChecker)
   */
  @Override
  public void addBatch() {
    // no-op
  }

  private Document runCommand() throws CommandRunFailSQLException {
    Document commandResult = mongoDatabase.runCommand(command);
    if (commandResult.getInteger("ok") != 1) {
      throw new CommandRunFailSQLException();
    }
    return commandResult;
  }
}
