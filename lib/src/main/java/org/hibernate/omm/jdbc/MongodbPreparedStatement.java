package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.omm.jdbc.adapter.PreparedStatementAdapter;
import org.hibernate.omm.jdbc.exception.CommandRunFailSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;

public class MongodbPreparedStatement extends PreparedStatementAdapter {

  private final MongoDatabase database;
  private final Bson command;

  public MongodbPreparedStatement(MongoDatabase database, Bson command) {
    this.database = database;
    this.command = command;
  }

  @Override
  public ResultSet executeQuery() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public int executeUpdate() throws SQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public boolean execute() throws SQLException {
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
    Document result = database.runCommand(command);
    if (result.get("ok", Integer.class) != 1) {
      throw new CommandRunFailSQLException();
    }
  }
}
