package org.hibernate.omm.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class MongodbConnectionProvider implements ConnectionProvider {

  @Override
  public Connection getConnection() throws SQLException {
    return null;
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
}
