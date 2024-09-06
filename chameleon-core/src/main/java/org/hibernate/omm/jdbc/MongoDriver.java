/*
 *
 * Copyright 2008-present MongoDB, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.hibernate.omm.jdbc;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.hibernate.omm.jdbc.adapter.DriverAdapter;
import org.hibernate.omm.service.CommandRecorder;

import java.sql.Connection;
import java.util.Properties;

import static java.util.Objects.requireNonNull;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDriver implements DriverAdapter {

    // currently mongoDatabase is put to static variable so unit testing code could
    // use it to drop collection(s) during unit testing lifecycle events
    public static volatile @MonotonicNonNull MongoDatabase mongoDatabase;
    public static @MonotonicNonNull MongoClient mongoClient;

    public static CommandRecorder commandRecorder;

    // mainly used for getting MongoDatabase reference in 'chameleon-testing'
    public static MongoDatabase getMongoDatabase(String url) {
        initializeMongoDatabase(url);
        return mongoDatabase;
    }

    @Override
    public Connection connect(final String url, final Properties info) {
        if (mongoDatabase == null) {
            synchronized (this) {
                if (mongoDatabase == null) {
                    initializeMongoDatabase(url);
                }
            }
        }
        final var clientSession = mongoClient.startSession();
        return new MongoConnection(mongoDatabase, clientSession);
    }

    @Override
    public boolean acceptsURL(final String url) {
        return url != null && (url.startsWith("mongodb://") || url.startsWith("mongo+srv://"));
    }

    private static void initializeMongoDatabase(final String url) {
        final var mongoDatabaseName = requireNonNull(extractDatabaseFromConnectionString(url));
        final var connectionString = new ConnectionString(url);
        final var codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry()
        );

        // we need to figure out how to customize MongoClientSettings.Builder
        final var clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        mongoClient = MongoClients.create(clientSettings);
        mongoDatabase = mongoClient.getDatabase(mongoDatabaseName);
    }

    private static String extractDatabaseFromConnectionString(final String connectionString) {
        int startIndex = connectionString.lastIndexOf('/') + 1;
        int endIndex = connectionString.indexOf(startIndex, '?');
        if (endIndex < 0) {
            return connectionString.substring(startIndex);
        }
        else {
            return connectionString.substring(startIndex, endIndex);
        }
    }

}

