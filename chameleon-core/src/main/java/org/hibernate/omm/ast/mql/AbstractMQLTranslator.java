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
package org.hibernate.omm.ast.mql;

import org.bson.BsonNull;
import org.hibernate.LockMode;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.ast.AbstractSqlAstTranslator;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.mongoast.AstLiteralValue;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperation;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperator;
import org.hibernate.omm.mongoast.filters.AstFieldOperationFilter;
import org.hibernate.omm.mongoast.filters.AstFilterField;
import org.hibernate.omm.mongoast.filters.AstMatchesEverythingFilter;
import org.hibernate.query.sqm.ComparisonOperator;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.SqlTupleContainer;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.predicate.ExistsPredicate;
import org.hibernate.sql.ast.tree.predicate.InListPredicate;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.hibernate.sql.ast.tree.predicate.NegatedPredicate;
import org.hibernate.sql.ast.tree.predicate.NullnessPredicate;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.exec.spi.JdbcOperation;

import static org.hibernate.omm.ast.mql.AttachmentKeys.fieldName;
import static org.hibernate.omm.ast.mql.AttachmentKeys.filter;

/**
 * Contains common stuff shared between {@link QueryMQLTranslator} and {@link MutationMQLTranslator}, e.g.:
 * <ul>
 *     <li>expression rendering</li>
 *     <li>predicate rendering</li>
 *     <li>util methods</li>
 * </ul>
 *
 * @author Nathan Xu
 * @since 1.0.0
 * @param <T> {@link JdbcOperation} generic type
 */
public class AbstractMQLTranslator<T extends JdbcOperation> extends AbstractSqlAstTranslator<T> {

    private boolean inAggregateExpressionScope;

    public AbstractMQLTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    public boolean isInAggregateExpressionScope() {
        return inAggregateExpressionScope;
    }

    public void setInAggregateExpressionScope(final boolean inAggregateExpressionScope) {
        this.inAggregateExpressionScope = inAggregateExpressionScope;
    }

    @Override
    protected void renderDmlTargetTableExpression(final NamedTableReference tableReference) {
        registerAffectedTable(tableReference);
    }

    @Override
    protected boolean renderNamedTableReference(final NamedTableReference tableReference, final LockMode lockMode) {
        registerAffectedTable(tableReference);
        return false;
    }

    @Override
    public void visitNamedTableReference(final NamedTableReference tableReference) {
        mqlAstState.attach(AttachmentKeys.collectionName(), tableReference.getTableExpression());
    }

    @Override
    protected void visitWhereClause(final Predicate whereClauseRestrictions) {
        final Predicate additionalWherePredicate = this.additionalWherePredicate;
        final boolean existsWhereClauseRestrictions = whereClauseRestrictions != null && !whereClauseRestrictions.isEmpty();
        final boolean existsAdditionalWherePredicate = additionalWherePredicate != null;
        if (existsWhereClauseRestrictions || existsAdditionalWherePredicate) {
            // TODO: entered but untested branch
            getClauseStack().push(Clause.WHERE);
            try {
                if (existsWhereClauseRestrictions) {
                    whereClauseRestrictions.accept(this);
                }
                if (additionalWherePredicate != null) {
                    this.additionalWherePredicate = null;
                    additionalWherePredicate.accept(this);
                }
            } finally {
                getClauseStack().pop();
            }
        } else {
            mqlAstState.attach(AttachmentKeys.filter(), new AstMatchesEverythingFilter());
        }
    }

    @Override
    public void visitNullnessPredicate(NullnessPredicate nullnessPredicate) {
        Expression expression = nullnessPredicate.getExpression();
        if (SqlTupleContainer.getSqlTuple(expression) != null) {
            throw new NotSupportedRuntimeException();
        }

        String fieldName = mqlAstState.expect(fieldName(), () -> expression.accept(this));

        mqlAstState.attach(filter(), new AstFieldOperationFilter(new AstFilterField(fieldName),
                new AstComparisonFilterOperation(
                        nullnessPredicate.isNegated() ? AstComparisonFilterOperator.NE : AstComparisonFilterOperator.EQ,
                        new AstLiteralValue(BsonNull.VALUE))));
    }

    @Override
    public void visitInListPredicate(final InListPredicate inListPredicate) {
        throw new NotSupportedRuntimeException();
    }

    @Override
    public void visitExistsPredicate(final ExistsPredicate existsPredicate) {
        throw new NotSupportedRuntimeException();
    }

    @Override
    public void visitJunction(final Junction junction) {
        throw new NotSupportedRuntimeException();
    }

    @Override
    public void visitNegatedPredicate(final NegatedPredicate negatedPredicate) {
        throw new NotSupportedRuntimeException();
    }

    protected void renderComparisonStandard(final Expression lhs, final ComparisonOperator operator, final Expression rhs) {
        throw new NotSupportedRuntimeException();
//        if (inAggregateExpressionScope) {
//        } else {
//        }
    }
}
