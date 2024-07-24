package org.hibernate.omm.dialect;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.type.ObjectIdJavaType;
import org.hibernate.omm.type.ObjectIdJdbcType;
import org.hibernate.omm.util.StringUtil;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.spi.StandardSqlAstTranslatorFactory;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.exec.spi.JdbcOperation;
import org.hibernate.type.spi.TypeConfiguration;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoDialect extends Dialect {

    private final static DatabaseVersion MINIMUM_VERSION = DatabaseVersion.make(3);

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

        return new StandardSqlAstTranslatorFactory() {
            @Override
            protected <T extends JdbcOperation> SqlAstTranslator<T> buildTranslator(
                    SessionFactoryImplementor sessionFactory, Statement statement) {
                return new MongoJsonAstTranslator<>(sessionFactory, statement);
            }
        };
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
        final TypeConfiguration typeConfiguration = typeContributions.getTypeConfiguration();
        typeConfiguration.getJavaTypeRegistry().addDescriptor(ObjectIdJavaType.INSTANCE);
        typeConfiguration.getJdbcTypeRegistry().addDescriptor(ObjectIdJdbcType.INSTANCE);
    }

}
