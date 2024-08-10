package org.hibernate.omm.dialect.function;

import org.hibernate.dialect.function.array.AbstractArrayContainsFunction;
import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class MongoArrayIncludesFunction extends AbstractArrayContainsFunction {

    public MongoArrayIncludesFunction(TypeConfiguration typeConfiguration) {
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

        sqlAppender.append("{ ");
        haystackExpression.accept(walker);
        sqlAppender.append(": { $all: ");
        needleExpression.accept(walker);
        sqlAppender.append(" } }");
    }
}
