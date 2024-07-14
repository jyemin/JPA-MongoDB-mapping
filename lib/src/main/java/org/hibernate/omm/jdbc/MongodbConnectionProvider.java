package org.hibernate.omm.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class MongodbConnectionProvider implements ConnectionProvider, MongodbJdbcContextAware {
	private final MongodbJdbcContext mongodbJdbcContext;

	public MongodbConnectionProvider(MongodbJdbcContext mongodbJdbcContext) {
		this.mongodbJdbcContext = mongodbJdbcContext;
	}

	@Override
  public Connection getConnection() throws SQLException {
    return new MongodbConnection(mongodbJdbcContext);
  }

  @Override
  public void closeConnection(Connection conn) throws SQLException {}

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  public boolean isUnwrappableAs(Class<?> unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }

	@Override
	public MongodbJdbcContext getMongodbJdbcContext() {
		return mongodbJdbcContext;
	}
}
