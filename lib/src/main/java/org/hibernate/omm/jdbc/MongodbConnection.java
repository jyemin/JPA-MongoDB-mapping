package org.hibernate.omm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.hibernate.omm.jdbc.adapter.ConnectionAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class MongodbConnection extends ConnectionAdapter implements MongodbJdbcContextAware {

  private final MongodbJdbcContext mongodbJdbcContext;

  public MongodbConnection(MongodbJdbcContext mongodbJdbcContext) {
    this.mongodbJdbcContext = mongodbJdbcContext;
  }

  @Override
  public Statement createStatement() throws SimulatedSQLException {
    return new MongodbStatement(mongodbJdbcContext);
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
    return new MongodbPreparedStatement(mongodbJdbcContext, null);
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
	public MongodbJdbcContext getMongodbJdbcContext() {
		return mongodbJdbcContext;
	}
}
