package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.hibernate.omm.jdbc.adapter.ConnectionAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class MongodbConnection extends ConnectionAdapter {

  private final MongoDatabase mongoDatabase;

  public MongodbConnection(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
  }

  @Override
  public Statement createStatement() throws SimulatedSQLException {
    return new MongodbStatement(mongoDatabase);
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
    throw new MongodbPreparedStatement(mongoDatabase);
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }

  @Override
  public String nativeSQL(String sql) throws SimulatedSQLException {
    throw new NotSupportedSQLException();
  }
}
