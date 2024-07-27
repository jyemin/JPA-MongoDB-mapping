package org.hibernate.omm.jdbc;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoConnectionProvider implements ConnectionProvider, Configurable, Stoppable {
    public static MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

    @Override
    public void configure(Map<String, Object> configurationValues) {
        String mongodbConnectionURL = (String) configurationValues.get(MongoAvailableSettings.MONGODB_CONNECTION_URL);
        String mongodbDatabaseName = (String) configurationValues.get(MongoAvailableSettings.MONGODB_DATABASE);
        ConnectionString connectionString = new ConnectionString(mongodbConnectionURL);
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry()
        );
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        mongoClient = MongoClients.create(clientSettings);
        mongoDatabase = mongoClient.getDatabase(mongodbDatabaseName);
    }

    @Override
    public Connection getConnection() {
        if (mongoDatabase == null) {
            throw new IllegalStateException(
                    "mongoDatabase instance should have been configured during Configurable mechanism ");
        }
        ClientSession clientSession = mongoClient.startSession();
        return new MongoConnection(mongoDatabase, clientSession);
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
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
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
