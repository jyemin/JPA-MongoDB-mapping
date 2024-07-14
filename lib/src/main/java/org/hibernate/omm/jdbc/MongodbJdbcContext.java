package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;
import java.sql.Connection;

public class MongodbJdbcContext {
  private final MongoDatabase mongoDatabase;
  private final String collection;
  private final Connection connection;

  public MongodbJdbcContext(MongoDatabase mongoDatabase, String collection, Connection connection) {
    this.mongoDatabase = mongoDatabase;
    this.collection = collection;
    this.connection = connection;
  }

  public MongoDatabase getMongoDatabase() {
    return mongoDatabase;
  }

  public String getCollection() {
    return collection;
  }

  public Connection getConnection() {
    return connection;
  }
}
