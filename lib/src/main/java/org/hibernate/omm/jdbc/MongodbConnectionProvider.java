package org.hibernate.omm.jdbc;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongodbConnectionProvider implements ConnectionProvider, Configurable, Stoppable {
    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

    @Override
    public void configure(Map<String, Object> configurationValues) {
        String mongodbConnectionURL = (String) configurationValues.get("mongodb.connection.url");
        String mongodbDatabaseName = (String) configurationValues.get("mongodb.database.name");
        ConnectionString connectionString = new ConnectionString(mongodbConnectionURL);
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry()
        );
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        mongoClient = MongoClients.create(clientSettings);
        this.mongoDatabase = mongoClient.getDatabase(mongodbDatabaseName);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return mongoDatabase == null ? null : new MongodbConnection(mongoDatabase);
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
    }

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
    public void stop() {
        if (mongoDatabase != null) {
            mongoDatabase.drop();
        }
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
