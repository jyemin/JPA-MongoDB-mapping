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
