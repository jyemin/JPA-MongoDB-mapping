package org.hibernate.omm.array.function;

import org.hibernate.dialect.function.array.AbstractArrayContainsFunction;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.BasicPluralType;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class MongoArrayContainsFunction extends AbstractArrayContainsFunction {

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
