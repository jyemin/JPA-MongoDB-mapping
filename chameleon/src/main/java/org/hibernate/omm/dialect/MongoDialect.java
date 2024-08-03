package org.hibernate.omm.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.array.AbstractArrayContainsFunction;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.omm.ast.MongoSqlAstTranslatorFactory;
import org.hibernate.omm.type.ObjectIdJavaType;
import org.hibernate.omm.type.ObjectIdJdbcType;
import org.hibernate.omm.util.StringUtil;
import org.hibernate.query.ReturnableType;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.BasicPluralType;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

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

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        var functionRegistry = functionContributions.getFunctionRegistry();
        var typeConfiguration = functionContributions.getTypeConfiguration();
        functionRegistry.register("array_contains", new MongoArrayContainsFunction(typeConfiguration));
    }

    // https://www.mongodb.com/docs/manual/tutorial/query-arrays/
    private static class MongoArrayContainsFunction extends AbstractArrayContainsFunction {

        public MongoArrayContainsFunction(TypeConfiguration typeConfiguration) {
            super(true, typeConfiguration);
        }

        @Override
        public void render(
                SqlAppender sqlAppender,
                List<? extends SqlAstNode> sqlAstArguments,
                ReturnableType<?> returnType,
                SqlAstTranslator<?> walker) {
            final Expression haystackExpression = (Expression) sqlAstArguments.get(0);
            final Expression needleExpression = (Expression) sqlAstArguments.get(1);

            final JdbcMappingContainer needleTypeContainer = needleExpression.getExpressionType();
            final JdbcMapping needleType = needleTypeContainer == null ? null : needleTypeContainer.getSingleJdbcMapping();

            if (needleType == null || needleType instanceof BasicPluralType<?, ?>) {
                sqlAppender.append("{ ");
                haystackExpression.accept(walker);
                sqlAppender.append(": { $all: ");
                needleExpression.accept(walker);
                sqlAppender.append(" } }");
            } else {
                sqlAppender.append("{ ");
                haystackExpression.accept(walker);
                sqlAppender.append(": ");
                needleExpression.accept(walker);
                sqlAppender.append(" }");
            }
        }
    }
}
