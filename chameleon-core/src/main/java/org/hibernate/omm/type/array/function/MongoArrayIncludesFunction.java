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
import org.hibernate.omm.translate.translator.AbstractBsonTranslator;
import org.hibernate.omm.translate.translator.Attachment;
import org.hibernate.omm.translate.translator.AttachmentKeys;
import org.hibernate.omm.translate.translator.ast.AstValue;
import org.hibernate.omm.translate.translator.ast.filters.AstAllFilterOperation;
import org.hibernate.omm.translate.translator.ast.filters.AstFieldOperationFilter;
import org.hibernate.omm.translate.translator.ast.filters.AstFilterField;
import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class MongoArrayIncludesFunction extends AbstractArrayContainsFunction {

    public MongoArrayIncludesFunction(final TypeConfiguration typeConfiguration) {
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

        Attachment mqlAstState = ((AbstractBsonTranslator<?>) walker).getMqlAstState();

        sqlAppender.append("{ ");
        String fieldName = mqlAstState.expect(AttachmentKeys.fieldName(), () -> haystackExpression.accept(walker));
        sqlAppender.append(": { $all: ");
        AstValue fieldValue = mqlAstState.expect(AttachmentKeys.fieldValue(), () -> needleExpression.accept(walker));
        sqlAppender.append(" } }");
        mqlAstState.attach(AttachmentKeys.filter(), new AstFieldOperationFilter(new AstFilterField(fieldName),
                new AstAllFilterOperation(fieldValue)));
    }
}
