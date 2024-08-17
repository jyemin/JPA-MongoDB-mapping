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
import java.sql.DatabaseMetaData;

/**
 * Virtual Mongo JDBC Driver's minimal metadata feature set to ensure no exception is thrown during JDBC Environment
 * building.
 * <p/>
 * Needs to re-evaluate whether the provided metadata is correct. For the time being, they are
 * provided as dummy overriding to avoid awkward exception during {@link org.hibernate.engine.jdbc.env.spi.JdbcEnvironment}
 * building phase.
 *
 * @author Nathan Xu
 * @since 1.0.0
 * @see org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentImpl
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

    @Override
    public int getDriverMajorVersion() {
        return -1;
    }

    @Override
    public int getDriverMinorVersion() {
        return -1;
    }

    @Override
    public String getSQLKeywords() {
        return "";
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() {
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() {
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() {
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() {
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() {
        return true;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() {
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() {
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() {
        return true;
    }

    @Override
    public boolean supportsNamedParameters() {
        return true;
    }

    @Override
    public boolean supportsResultSetType(int type) {
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() {
        return true;
    }

    @Override
    public boolean supportsBatchUpdates() {
        return true;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() {
        return true;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() {
        return false;
    }

    @Override
    public int getSQLStateType() {
        return DatabaseMetaData.sqlStateSQL;
    }

    @Override
    public String getCatalogSeparator() {
        return "\n";
    }

    @Override
    public boolean isCatalogAtStart() {
        return true;
    }
}
