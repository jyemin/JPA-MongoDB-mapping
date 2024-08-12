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

import org.hibernate.omm.jdbc.adapter.DatabaseMetaDataAdapter;

import java.sql.Connection;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoDatabaseMetaData implements DatabaseMetaDataAdapter {
    private final String version;
    private final int majorVersion;
    private final int minorVersion;
    private final MongoConnection mongoConnection;

    public MongoDatabaseMetaData(final String version, final int majorVersion, final int minorVersion,
            final MongoConnection mongoConnection) {
        this.version = version;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.mongoConnection = mongoConnection;
    }

    @Override
    public String getDatabaseProductName() {
        return "MongoDB";
    }

    @Override
    public String getDatabaseProductVersion() {
        return version;
    }

    @Override
    public int getDatabaseMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getDatabaseMinorVersion() {
        return minorVersion;
    }

    @Override
    public Connection getConnection() {
        return mongoConnection;
    }

    @Override
    public String getDriverName() {
        return "Virtual JDBC Driver";
    }

}
