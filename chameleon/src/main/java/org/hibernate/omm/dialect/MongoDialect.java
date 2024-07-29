package org.hibernate.omm.dialect;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.omm.ast.MongoSqlAstTranslatorFactory;
import org.hibernate.omm.type.ObjectIdJavaType;
import org.hibernate.omm.type.ObjectIdJdbcType;
import org.hibernate.omm.util.StringUtil;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.spi.TypeConfiguration;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoDialect extends Dialect {

    public static final int MINIMUM_MONGODB_MAJOR_VERSION_SUPPORTED = 3;

    private final static DatabaseVersion MINIMUM_VERSION = DatabaseVersion.make(MINIMUM_MONGODB_MAJOR_VERSION_SUPPORTED);

    public MongoDialect() {
        this(MINIMUM_VERSION);
    }

    public MongoDialect(DatabaseVersion version) {
        super(version);
    }

    public MongoDialect(DialectResolutionInfo dialectResolutionInfo) {
        super(dialectResolutionInfo);
    }

    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
        return new MongoSqlAstTranslatorFactory();
    }

    @Override
    public void appendLiteral(SqlAppender appender, String literal) {
        appender.appendSql(StringUtil.writeStringHelper(literal));
    }

    @Override
    public boolean supportsNullPrecedence() {
        return false;
    }

    @Override
    public boolean supportsStandardArrays() {
        return true;
    }

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contribute(typeContributions, serviceRegistry); // need to call the super method to enable Array support
        TypeConfiguration typeConfiguration = typeContributions.getTypeConfiguration();
        typeConfiguration.getJavaTypeRegistry().addDescriptor(ObjectIdJavaType.INSTANCE);
        typeConfiguration.getJdbcTypeRegistry().addDescriptor(ObjectIdJdbcType.INSTANCE);
    }

}
