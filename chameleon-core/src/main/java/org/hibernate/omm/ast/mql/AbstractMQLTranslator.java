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

import org.hibernate.LockMode;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.ast.AbstractSqlAstTranslator;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.query.sqm.ComparisonOperator;
import org.hibernate.query.sqm.sql.internal.SqmParameterInterpretation;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.ast.tree.expression.Literal;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.expression.SqlTupleContainer;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.predicate.ExistsPredicate;
import org.hibernate.sql.ast.tree.predicate.InListPredicate;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.hibernate.sql.ast.tree.predicate.NegatedPredicate;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.exec.spi.JdbcOperation;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import static org.hibernate.omm.util.StringUtil.writeStringHelper;

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
        appendMql(writeStringHelper(tableReference.getTableExpression()));
        registerAffectedTable(tableReference);
    }

    @Override
    protected boolean renderNamedTableReference(final NamedTableReference tableReference, final LockMode lockMode) {
        appendMql(writeStringHelper(tableReference.getTableExpression()));
        registerAffectedTable(tableReference);
        return false;
    }

    @Override
    public void visitNamedTableReference(final NamedTableReference tableReference) {
        appendMql(writeStringHelper(tableReference.getTableExpression()));
    }

    @Override
    protected void visitWhereClause(final Predicate whereClauseRestrictions) {
        final Predicate additionalWherePredicate = this.additionalWherePredicate;
        final boolean existsWhereClauseRestrictions = whereClauseRestrictions != null && !whereClauseRestrictions.isEmpty();
        final boolean existsAdditionalWherePredicate = additionalWherePredicate != null;
        final boolean requiredAndPredicate = existsWhereClauseRestrictions && existsAdditionalWherePredicate;
        if (existsWhereClauseRestrictions || existsAdditionalWherePredicate) {
            if (requiredAndPredicate) {
                appendMql("{ $and: [ ");
            }
            getClauseStack().push(Clause.WHERE);
            try {
                if (existsWhereClauseRestrictions) {
                    whereClauseRestrictions.accept(this);
                }
                if (requiredAndPredicate) {
                    appendMql(", ");
                }
                if (additionalWherePredicate != null) {
                    this.additionalWherePredicate = null;
                    additionalWherePredicate.accept(this);
                }
                if (requiredAndPredicate) {
                    appendMql(" ] }");
                }
            } finally {
                getClauseStack().pop();
            }
        } else {
            appendMql("{ }");
        }
    }

    @Override
    public void visitInListPredicate(final InListPredicate inListPredicate) {
        final List<Expression> listExpressions = inListPredicate.getListExpressions();
        if (listExpressions.isEmpty()) {
            emptyInList(inListPredicate);
            return;
        }
        Function<Expression, Expression> itemAccessor = Function.identity();
        final SqlTuple lhsTuple = SqlTupleContainer.getSqlTuple(inListPredicate.getTestExpression());
        if (lhsTuple != null) {
            if (lhsTuple.getExpressions().size() == 1) {
                // Special case for tuples with arity 1 as any DBMS supports scalar IN predicates
                itemAccessor = listExpression -> SqlTupleContainer.getSqlTuple(listExpression)
                        .getExpressions()
                        .get(0);
            } else if (!supportsRowValueConstructorSyntaxInInList()) {
                final ComparisonOperator comparisonOperator = inListPredicate.isNegated()
                        ? ComparisonOperator.NOT_EQUAL
                        : ComparisonOperator.EQUAL;
                // Some DBs like Oracle support tuples only for the IN subquery predicate
                if (supportsRowValueConstructorSyntaxInInSubQuery() && dialect.supportsUnionAll()) {
                    inListPredicate.getTestExpression().accept(this);
                    if (inListPredicate.isNegated()) {
                        appendMql(" not");
                    }
                    appendMql(" in (");
                    String separator = NO_SEPARATOR;
                    for (Expression expression : listExpressions) {
                        appendMql(separator);
                        renderExpressionsAsSubquery(
                                SqlTupleContainer.getSqlTuple(expression).getExpressions()
                        );
                        separator = " union all ";
                    }
                    appendMql(CLOSE_PARENTHESIS);
                } else {
                    String separator = NO_SEPARATOR;
                    appendMql(OPEN_PARENTHESIS);
                    for (Expression expression : listExpressions) {
                        appendMql(separator);
                        emulateTupleComparison(
                                lhsTuple.getExpressions(),
                                SqlTupleContainer.getSqlTuple(expression).getExpressions(),
                                comparisonOperator,
                                true
                        );
                        separator = " or ";
                    }
                    appendMql(CLOSE_PARENTHESIS);
                }
                return;
            }
        }

        int bindValueCount = listExpressions.size();
        int bindValueCountWithPadding = bindValueCount;

        int inExprLimit = dialect.getInExpressionCountLimit();

        if (getSessionFactory().getSessionFactoryOptions().inClauseParameterPaddingEnabled()) {
            bindValueCountWithPadding = addPadding(bindValueCount, inExprLimit);
        }

        final boolean parenthesis = !inListPredicate.isNegated()
                && inExprLimit > 0 && listExpressions.size() > inExprLimit;
        if (parenthesis) {
            //appendMql( OPEN_PARENTHESIS );
        }

        appendMql("{ ");

        inListPredicate.getTestExpression().accept(this);
        appendMql(": {");
        if (inListPredicate.isNegated()) {
            appendMql("\"$nin\": ");
            //appendMql( " not" );
        }
        //appendMql( " in (" );
        appendMql("\"$in\": ");
        appendMql("[");
        String separator = NO_SEPARATOR;

        final Iterator<Expression> iterator = listExpressions.iterator();
        Expression listExpression = null;
        int clauseItemNumber = 0;
        for (int i = 0; i < bindValueCountWithPadding; i++, clauseItemNumber++) {
            if (inExprLimit > 0 && inExprLimit == clauseItemNumber) {
                clauseItemNumber = 0;
                appendInClauseSeparator(inListPredicate);
                separator = NO_SEPARATOR;
            }

            if (iterator.hasNext()) { // If the iterator is exhausted, reuse the last expression for padding.
                listExpression = itemAccessor.apply(iterator.next());
            }
            // The only way for expression to be null is if listExpressions is empty,
            // but if that is the case the code takes an early exit.
            assert listExpression != null;

            appendMql(separator);
            castNonNull(listExpression).accept(this);
            separator = COMMA_SEPARATOR;

            // If we encounter an expression that is not a parameter or literal, we reset the inExprLimit and
            // bindValueMaxCount and just render through the in list expressions as they are without padding/splitting
            if (!(listExpression instanceof JdbcParameter || listExpression instanceof SqmParameterInterpretation || listExpression instanceof Literal)) {
                inExprLimit = 0;
                bindValueCountWithPadding = bindValueCount;
            }
        }

        appendMql("] }");
        //appendMql( CLOSE_PARENTHESIS );
        if (parenthesis) {
            //appendMql( CLOSE_PARENTHESIS );
        }
    }

    protected void emptyInList(final InListPredicate inListPredicate) {
        appendMql("(");
        appendMql(inListPredicate.isNegated() ? "0" : "1");
        appendMql(" = case when ");
        inListPredicate.getTestExpression().accept(this);
        appendMql(" is not null then 0");
        //dialect.appendBooleanValueString( this, inListPredicate.isNegated() );
        appendMql(" end)");
    }

    @Override
    public void visitExistsPredicate(final ExistsPredicate existsPredicate) {
        appendMql("{ ");
        if (existsPredicate.isNegated()) {
            appendMql(" $not: {");
        }
        appendMql("$exists: ");
        existsPredicate.getExpression().accept(this);
        if (existsPredicate.isNegated()) {
            appendMql("} ");
        }
        appendMql(" }");
    }

    @Override
    public void visitJunction(final Junction junction) {
        if (junction.isEmpty()) {
            return;
        }

        appendMql("{ ");
        final Junction.Nature nature = junction.getNature();
        if (nature == Junction.Nature.CONJUNCTION) {
            appendMql("$and: ");
        } else {
            appendMql("$or: ");
        }
        appendMql("[");
        final List<Predicate> predicates = junction.getPredicates();
        visitJunctionPredicate(nature, predicates.get(0));
        for (int i = 1; i < predicates.size(); i++) {
            appendMql(", ");
            visitJunctionPredicate(nature, predicates.get(i));
        }
        appendMql("] }");
    }

    @Override
    public void visitNegatedPredicate(final NegatedPredicate negatedPredicate) {
        if (negatedPredicate.isEmpty()) {
            return;
        }
        appendMql("{ $not: ");
        negatedPredicate.getPredicate().accept(this);
        appendMql(" }");
    }

    protected void appendMql(final String value) {
        sqlBuffer.append(value);
    }

    protected void appendMql(final char fragment) {
        sqlBuffer.append(fragment);
    }

    protected void appendMql(final int value) {
        sqlBuffer.append(value);
    }

    protected void appendMql(final long value) {
        sqlBuffer.append(value);
    }

    protected void appendMql(final boolean value) {
        sqlBuffer.append(value);
    }

    protected void renderComparisonStandard(final Expression lhs, final ComparisonOperator operator, final Expression rhs) {
        if (inAggregateExpressionScope) {
            appendMql("{ ");
            appendMql(getMongoOperatorText(operator));
            appendMql(": [ ");
            lhs.accept(this);
            appendMql(", ");
            rhs.accept(this);
            appendMql(" ] }");
        } else {
            appendMql("{ ");
            lhs.accept(this);
            appendMql(": { ");
            appendMql(getMongoOperatorText(operator));
            appendMql(": ");
            rhs.accept(this);
            appendMql(" } }");
        }
    }

    private String getMongoOperatorText(final ComparisonOperator operator) {
        return switch (operator) {
            case EQUAL -> "$eq";
            case NOT_EQUAL -> "$ne";
            case LESS_THAN -> "$lt";
            case LESS_THAN_OR_EQUAL -> "$lte";
            case GREATER_THAN -> "$gt";
            case GREATER_THAN_OR_EQUAL -> "gte";
            default -> throw new NotSupportedRuntimeException("unsupported operator: " + operator.name());
        };
    }

}
