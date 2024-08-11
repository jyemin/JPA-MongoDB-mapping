/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.jdbc;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import org.bson.codecs.configuration.CodecRegistry;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.omm.cfg.MongoAvailableSettings;
import org.hibernate.omm.exception.MongoConfigMissingException;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.hibernate.internal.util.NullnessUtil.castNonNull;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoConnectionProvider implements ConnectionProvider, Configurable, Stoppable {

    private @MonotonicNonNull MongoDatabase mongoDatabase;

    private @MonotonicNonNull MongoClient mongoClient;

    @Override
    public void configure(final Map<String, Object> configurationValues) {
        List<String> missingConfigurations = new ArrayList<>(2);

        String mongodbConnectionURL =
                (String) configurationValues.get(MongoAvailableSettings.MONGODB_CONNECTION_URL);
        if (mongodbConnectionURL == null) {
            missingConfigurations.add(MongoAvailableSettings.MONGODB_CONNECTION_URL);
        }

        String mongodbDatabaseName =
                (String) configurationValues.get(MongoAvailableSettings.MONGODB_DATABASE);
        if (mongodbDatabaseName == null) {
            missingConfigurations.add(MongoAvailableSettings.MONGODB_DATABASE);
        }

        if (!missingConfigurations.isEmpty()) {
            throw new MongoConfigMissingException(missingConfigurations);
        }

        // MongoConfigMissingException would have been thrown if mongodbConnectionURL is null
        ConnectionString connectionString = new ConnectionString(castNonNull(mongodbConnectionURL));
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry()
        );

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        mongoClient = MongoClients.create(clientSettings);

        // MongoConfigMissingException would have been thrown if mongodbDatabaseName is null
        mongoDatabase = mongoClient.getDatabase(castNonNull(mongodbDatabaseName));

    }

    @Override
    public Connection getConnection() {
        if (mongoDatabase == null) {
            throw new IllegalStateException(
                    "mongoDatabase instance should have been configured during Configurable mechanism");
        }
        if (mongoClient == null) {
            throw new IllegalStateException(
                    "mongoClient instance should have been configured during Configurable mechanism");
        }
        ClientSession clientSession = mongoClient.startSession();
        return new MongoConnection(mongoDatabase, clientSession);
    }

    @Override
    public void closeConnection(final Connection conn) throws SQLException {
        conn.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(@NonNull final Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(@NonNull final Class<T> unwrapType) {
        throw new UnknownUnwrapTypeException(unwrapType);
    }

    @Override
    public void stop() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Nullable
    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

}
