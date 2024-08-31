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
package org.hibernate.omm.type.array.function;

import org.hibernate.dialect.function.array.AbstractArrayContainsFunction;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.omm.ast.mql.AbstractMQLTranslator;
import org.hibernate.omm.ast.mql.Attachment;
import org.hibernate.omm.ast.mql.AttachmentKeys;
import org.hibernate.omm.mongoast.AstValue;
import org.hibernate.omm.mongoast.filters.AstAllFilterOperation;
import org.hibernate.omm.mongoast.filters.AstFieldOperationFilter;
import org.hibernate.omm.mongoast.filters.AstFilterField;
import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.BasicPluralType;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class MongoArrayContainsFunction extends AbstractArrayContainsFunction {

    public MongoArrayContainsFunction(final TypeConfiguration typeConfiguration) {
        super(true, typeConfiguration);
    }

    @Override
    public void render(
            final SqlAppender sqlAppender,
            final List<? extends SqlAstNode> sqlAstArguments,
            final ReturnableType<?> returnType,
            final SqlAstTranslator<?> walker) {
        final Expression haystackExpression = (Expression) sqlAstArguments.get(0);
        final Expression needleExpression = (Expression) sqlAstArguments.get(1);

        final JdbcMappingContainer needleTypeContainer = needleExpression.getExpressionType();
        final JdbcMapping needleType = needleTypeContainer == null ? null : needleTypeContainer.getSingleJdbcMapping();

        Attachment mqlAstState = ((AbstractMQLTranslator<?>) walker).getMqlAstState();

        if (needleType == null || needleType instanceof BasicPluralType<?, ?>) {
            sqlAppender.append("{ ");
            String fieldName = mqlAstState.expect(AttachmentKeys.fieldName(), () -> haystackExpression.accept(walker));
            sqlAppender.append(": { $all: ");
            AstValue fieldValue = mqlAstState.expect(AttachmentKeys.fieldValue(), () -> needleExpression.accept(walker));
            sqlAppender.append(" } }");
            mqlAstState.attach(AttachmentKeys.filter(), new AstFieldOperationFilter(new AstFilterField(fieldName),
                    new AstAllFilterOperation(fieldValue)));
        } else {
            // TODO: this is not tested, so no MQL AST support yet
            sqlAppender.append("{ ");
            haystackExpression.accept(walker);
            sqlAppender.append(": ");
            needleExpression.accept(walker);
            sqlAppender.append(" }");
        }
    }
}
