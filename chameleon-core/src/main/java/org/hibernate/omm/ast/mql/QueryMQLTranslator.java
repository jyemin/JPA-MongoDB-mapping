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

import com.mongodb.lang.Nullable;
import org.hibernate.dialect.SelectItemReferenceStrategy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.exception.NotYetImplementedException;
import org.hibernate.omm.mongoast.AstAggregation;
import org.hibernate.omm.mongoast.AstAscendingSortOrder;
import org.hibernate.omm.mongoast.AstDescendingSortOrder;
import org.hibernate.omm.mongoast.AstPipeline;
import org.hibernate.omm.mongoast.AstSortField;
import org.hibernate.omm.mongoast.AstSortOrder;
import org.hibernate.omm.mongoast.AstValue;
import org.hibernate.omm.mongoast.expressions.AstFieldPathExpression;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperation;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperator;
import org.hibernate.omm.mongoast.filters.AstFieldOperationFilter;
import org.hibernate.omm.mongoast.filters.AstFilter;
import org.hibernate.omm.mongoast.filters.AstFilterField;
import org.hibernate.omm.mongoast.stages.AstLookupStage;
import org.hibernate.omm.mongoast.stages.AstLookupStageEqualityMatch;
import org.hibernate.omm.mongoast.stages.AstLookupStageMatch;
import org.hibernate.omm.mongoast.stages.AstMatchStage;
import org.hibernate.omm.mongoast.stages.AstProjectStage;
import org.hibernate.omm.mongoast.stages.AstProjectStageSpecification;
import org.hibernate.omm.mongoast.stages.AstSortStage;
import org.hibernate.omm.mongoast.stages.AstStage;
import org.hibernate.omm.mongoast.stages.AstUnwindStage;
import org.hibernate.omm.util.CollectionUtil;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.query.NullPrecedence;
import org.hibernate.query.SortDirection;
import org.hibernate.query.sqm.ComparisonOperator;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.QueryLiteral;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.expression.SqlTupleContainer;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.from.LazyTableGroup;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.insert.InsertSelectStatement;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.hibernate.sql.ast.tree.predicate.ComparisonPredicate;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.select.QueryGroup;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.hibernate.sql.ast.tree.select.SelectClause;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.sql.exec.spi.JdbcOperationQuerySelect;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;

/**
 * Contains MQL query type rendering overriding logic.
 *
 * @see MutationMQLTranslator
 * @author Nathan Xu
 * @since 1.0.0
 */
public class QueryMQLTranslator extends AbstractMQLTranslator<JdbcOperationQuerySelect> {

    private static class PathTracker {
        private final Map<String, String> pathByQualifier = new HashMap<>();

        @Nullable
        private String rootQualifier;

        public void setRootQualifier(final String rootQualifier) {
            this.rootQualifier = rootQualifier;
        }

        public @Nullable String getRootQualifier() {
            return rootQualifier;
        }

        public void trackPath(final String sourceQualifier, final String targetQualifier) {
            var sourcePath = pathByQualifier.get(sourceQualifier);
            if (sourcePath != null) {
                pathByQualifier.put(targetQualifier, sourcePath + "." + targetQualifier);
            } else {
                pathByQualifier.put(targetQualifier, targetQualifier);
            }
        }

        public String renderColumnReference(final ColumnReference columnReference) {
            var path = pathByQualifier.get(columnReference.getQualifier());
            String result;
            if (path == null) {
                if (columnReference.isColumnExpressionFormula()) {
                    return columnReference.getColumnExpression().replace(columnReference.getQualifier() + ".", "");
                } else {
                    result = columnReference.getColumnExpression();
                }
            } else {
                result = path + "." + columnReference.getColumnExpression();
            }
            return "$" + result;
        }
    }

    private final PathTracker pathTracker = new PathTracker();

    @Nullable
    private String targetQualifier;

    public QueryMQLTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    public void visitQuerySpec(final QuerySpec querySpec) {
        final QueryPart queryPartForRowNumbering = this.queryPartForRowNumbering;
        final int queryPartForRowNumberingClauseDepth = this.queryPartForRowNumberingClauseDepth;
        final boolean needsSelectAliases = this.needsSelectAliases;
        final Predicate additionalWherePredicate = this.additionalWherePredicate;
        final ForUpdateClause forUpdate = this.forUpdate;
        try {
            this.additionalWherePredicate = null;
            this.forUpdate = null;
            // See the field documentation of queryPartForRowNumbering etc. for an explanation about this
            // In addition, we also reset the row numbering if the currently row numbered query part is a query group
            // which means this query spec is a part of that query group.
            // We want the row numbering to happen on the query group level, not on the query spec level, so we reset
            final QueryPart currentQueryPart = queryPartStack.getCurrent();
            if (currentQueryPart != null && (queryPartForRowNumbering instanceof QueryGroup || queryPartForRowNumberingClauseDepth != clauseStack.depth())) {
                this.queryPartForRowNumbering = null;
                this.queryPartForRowNumberingClauseDepth = -1;
            }
            String queryGroupAlias = null;
            if (currentQueryPart instanceof QueryGroup) {
                // We always need query wrapping if we are in a query group and this query spec has a fetch or order by
                // clause, because of order by precedence in SQL
                if (querySpec.hasOffsetOrFetchClause() || querySpec.hasSortSpecifications()) {
                    queryGroupAlias = "";
                    // If the parent is a query group with a fetch clause we must use a select wrapper,
                    // or if the database does not support simple query grouping, we must use a select wrapper
                    if ((!supportsSimpleQueryGrouping() || currentQueryPart.hasOffsetOrFetchClause())
                            // We can skip it though if this query spec is being row numbered,
                            // because then we already have a wrapper
                            && queryPartForRowNumbering != querySpec) {
                        queryGroupAlias = " grp_" + queryGroupAliasCounter + '_';
                        queryGroupAliasCounter++;
                        appendMql("select");
                        appendMql(queryGroupAlias);
                        appendMql(".* from ");
                        // We need to assign aliases when we render a query spec as subquery to avoid clashing aliases
                        this.needsSelectAliases = this.needsSelectAliases || hasDuplicateSelectItems(querySpec);
                    } else if (!supportsDuplicateSelectItemsInQueryGroup()) {
                        this.needsSelectAliases = this.needsSelectAliases || hasDuplicateSelectItems(querySpec);
                    }
                }
            }
            queryPartStack.push(querySpec);
            if (queryGroupAlias != null) {
                throw new NotSupportedRuntimeException("query group not supported");
            }
            appendMql("{ aggregate: ");
            CollectionNameAndJoinStages collectionNameAndJoinStages = mqlAstState.expect(AttachmentKeys.collectionNameAndJoinStages(), () ->
                    visitFromClause(querySpec.getFromClause()));

            List<AstStage> stageList = new ArrayList<>(collectionNameAndJoinStages.joinStages());

            appendMql("{ $match: ");
            AstFilter filter = mqlAstState.expect(AttachmentKeys.filter(), () ->
                    visitWhereClause(querySpec.getWhereClauseRestrictions()));
            stageList.add(new AstMatchStage(filter));

            if (CollectionUtil.isNotEmpty(querySpec.getSortSpecifications())) {
                appendMql(" }, { $sort: ");
                List<AstSortField> sortFields = mqlAstState.expect(AttachmentKeys.sortFields(), () ->
                        visitOrderBy(querySpec.getSortSpecifications()));
                stageList.add(new AstSortStage(sortFields));
            }

            appendMql(" }, { $project: ");
            List<AstProjectStageSpecification> projectStageSpecifications =
                    mqlAstState.expect(AttachmentKeys.projectStageSpecifications(), () -> visitSelectClause(querySpec.getSelectClause()));
            stageList.add(new AstProjectStage(projectStageSpecifications));

            //visitGroupByClause( querySpec, dialect.getGroupBySelectItemReferenceStrategy() );
            //visitHavingClause( querySpec );
            visitOffsetFetchClause(querySpec);
            // We render the FOR UPDATE clause in the parent query
            //if ( queryPartForRowNumbering == null ) {
            //visitForUpdateClause( querySpec );
            //}
            appendMql(" } ] }");
            AstPipeline pipeline = new AstPipeline(stageList);
            root = new AstAggregation(collectionNameAndJoinStages.collectionName(), pipeline);
        } finally {
            this.queryPartStack.pop();
            this.queryPartForRowNumbering = queryPartForRowNumbering;
            this.queryPartForRowNumberingClauseDepth = queryPartForRowNumberingClauseDepth;
            this.needsSelectAliases = needsSelectAliases;
            this.additionalWherePredicate = additionalWherePredicate;
            if (queryPartForRowNumbering == null) {
                this.forUpdate = forUpdate;
            }
        }
    }

    @Override
    public void visitFromClause(@Nullable final FromClause fromClause) {
        if (fromClause == null || fromClause.getRoots().isEmpty()) {
            throw new NotSupportedRuntimeException("null fromClause or empty root not supported");
        } else {
            renderFromClauseSpaces(fromClause);
        }
    }

    @Override
    protected void renderFromClauseSpaces(final FromClause fromClause) {
        try {
            clauseStack.push(Clause.FROM);
            if (fromClause.getRoots().size() > 1) {
                throw new NotYetImplementedException();
            }
            pathTracker.setRootQualifier(fromClause.getRoots().get(0).getPrimaryTableReference().getIdentificationVariable());
            renderFromClauseRoot(fromClause.getRoots().get(0));
        } finally {
            clauseStack.pop();
        }
    }

    private void renderFromClauseRoot(final TableGroup root) {
        if (root.isVirtual()) {
            throw new NotYetImplementedException();
        } else if (root.isInitialized()) {
            renderRootTableGroup(root, null);
        }
    }

    @Override
    protected void renderRootTableGroup(final TableGroup tableGroup, @Nullable final List<TableGroupJoin> tableGroupJoinCollector) {

        renderTableReferenceJoins(tableGroup);
        processNestedTableGroupJoins(tableGroup, tableGroupJoinCollector);
        if (tableGroupJoinCollector != null) {
            tableGroupJoinCollector.addAll(tableGroup.getTableGroupJoins());
        } else {
            String collectionName = mqlAstState.expect(AttachmentKeys.collectionName(), () ->
                    tableGroup.getPrimaryTableReference().accept(this));
            appendMql(", pipeline: [ ");
            List<AstStage> joinStages = mqlAstState.expect(AttachmentKeys.joinStages(), () ->
                    processTableGroupJoins(tableGroup));
            mqlAstState.attach(AttachmentKeys.collectionNameAndJoinStages(), new CollectionNameAndJoinStages(collectionName, joinStages));
        }
        ModelPartContainer modelPart = tableGroup.getModelPart();
        if (modelPart instanceof AbstractEntityPersister) {
            String[] querySpaces = (String[]) ((AbstractEntityPersister) modelPart).getQuerySpaces();
            for (String querySpace : querySpaces) {
                registerAffectedTable(querySpace);
            }
        }
    }

    @Override
    protected void processTableGroupJoins(final TableGroup source) {
        if (!source.getTableGroupJoins().isEmpty()) {
            for (int i = 0; i < source.getTableGroupJoins().size(); i++) {
                processTableGroupJoin(source, source.getTableGroupJoins().get(i), null);
                if (i + 1 < source.getTableGroupJoins().size()) {
                    appendMql(", ");
                }
            }
        } else {
            mqlAstState.attach(AttachmentKeys.joinStages(), List.of());
        }
    }

    @Override
    public void visitSelectClause(final SelectClause selectClause) {
        clauseStack.push(Clause.SELECT);
        appendMql("{ ");
        try {
            /*if ( selectClause.isDistinct() ) {
                appendMql( "distinct " );
            }*/
            visitSqlSelections(selectClause);
            appendMql(" }");
        } finally {
            clauseStack.pop();
        }
    }

    @Override
    protected void visitSqlSelections(final SelectClause selectClause) {
        final List<SqlSelection> sqlSelections = selectClause.getSqlSelections();
        final int size = sqlSelections.size();
        List<AstProjectStageSpecification> projectStageSpecifications = new ArrayList<>(size);
        final SelectItemReferenceStrategy referenceStrategy = dialect.getGroupBySelectItemReferenceStrategy();
        // When the dialect needs to render the aliased expression and there are aliased group by items,
        // we need to inline parameters as the database would otherwise not be able to match the group by item
        // to the select item, ultimately leading to a query error
        final BitSet selectItemsToInline;
        if (referenceStrategy == SelectItemReferenceStrategy.EXPRESSION) {
            selectItemsToInline = getSelectItemsToInline();
        } else {
            selectItemsToInline = null;
        }
        final SqlAstNodeRenderingMode original = parameterRenderingMode;
        final SqlAstNodeRenderingMode defaultRenderingMode;
        if (getStatement() instanceof InsertSelectStatement && clauseStack.depth() == 1 && queryPartStack.depth() == 1) {
            // Databases support inferring parameter types for simple insert-select statements
            defaultRenderingMode = SqlAstNodeRenderingMode.DEFAULT;
        } else {
            defaultRenderingMode = SqlAstNodeRenderingMode.NO_PLAIN_PARAMETER;
        }
        if (needsSelectAliases || referenceStrategy == SelectItemReferenceStrategy.ALIAS && hasSelectAliasInGroupByClause()) {
            String separator = NO_SEPARATOR;
            int offset = 0;
            for (int i = 0; i < size; i++) {
                final SqlSelection sqlSelection = sqlSelections.get(i);
                if (sqlSelection.isVirtual()) {
                    continue;
                }
                if (selectItemsToInline != null && selectItemsToInline.get(i)) {
                    parameterRenderingMode = SqlAstNodeRenderingMode.INLINE_ALL_PARAMETERS;
                } else {
                    parameterRenderingMode = defaultRenderingMode;
                }
                final Expression expression = sqlSelection.getExpression();
                final SqlTuple sqlTuple = SqlTupleContainer.getSqlTuple(expression);
                if (sqlTuple != null) {
                    final List<? extends Expression> expressions = sqlTuple.getExpressions();
                    for (Expression e : expressions) {
                        appendMql(separator);
                        renderSelectExpression(e);
                        appendMql(WHITESPACE);
                        if (columnAliases == null) {
                            appendMql('c');
                            appendMql(offset);
                        } else {
                            appendMql(columnAliases.get(offset));
                        }
                        offset++;
                        separator = COMMA_SEPARATOR;
                    }
                } else {
                    appendMql(separator);
                    renderSelectExpression(expression);
                    appendMql(WHITESPACE);
                    if (columnAliases == null) {
                        appendMql('c');
                        appendMql(offset);
                    } else {
                        appendMql(columnAliases.get(offset));
                    }
                    offset++;
                    separator = COMMA_SEPARATOR;
                }
                parameterRenderingMode = original;
            }
            if (queryPartForRowNumbering != null) {
                renderRowNumberingSelectItems(selectClause, queryPartForRowNumbering);
            }
        } else {
            assert columnAliases == null;
            String separator = NO_SEPARATOR;
            for (int i = 0; i < size; i++) {
                final SqlSelection sqlSelection = sqlSelections.get(i);
                if (sqlSelection.isVirtual()) {
                    continue;
                }
                appendMql(separator);
                if (selectItemsToInline != null && selectItemsToInline.get(i)) {
                    parameterRenderingMode = SqlAstNodeRenderingMode.INLINE_ALL_PARAMETERS;
                } else {
                    parameterRenderingMode = defaultRenderingMode;
                }
                if (sqlSelection.getExpression() instanceof ColumnReference columnReference) {
                    appendMql("f" + i); // field name doesn't matter for Hibernate ResultSet retrieval only relies on order since v6
                    appendMql(": ");
                    appendMql('"' + pathTracker.renderColumnReference(columnReference) + '"');
                    projectStageSpecifications.add(
                            AstProjectStageSpecification.Set("f" + i,
                            new AstFieldPathExpression(pathTracker.renderColumnReference(columnReference))));
                } else {
                    visitSqlSelection(sqlSelection);
                    appendMql(": 1");
                }
                parameterRenderingMode = original;
                separator = ", ";
            }
            appendMql(", _id: 0");

            projectStageSpecifications.add(AstProjectStageSpecification.ExcludeId());
            mqlAstState.attach(AttachmentKeys.projectStageSpecifications(), projectStageSpecifications);
        }
    }

    private boolean hasSelectAliasInGroupByClause() {
        final QuerySpec querySpec = (QuerySpec) getQueryPartStack().getCurrent();
        for (Expression groupByClauseExpression : querySpec.getGroupByClauseExpressions()) {
            if (getSelectItemReference(groupByClauseExpression) != null) {
                return true;
            }
        }
        return false;
    }

    private void processTableGroupJoin(final TableGroup source, final TableGroupJoin tableGroupJoin,
            @Nullable final List<TableGroupJoin> tableGroupJoinCollector) {
        final TableGroup joinedGroup = tableGroupJoin.getJoinedGroup();

        if (joinedGroup.isVirtual()) {
            processNestedTableGroupJoins(joinedGroup, tableGroupJoinCollector);
            if (tableGroupJoinCollector != null) {
                tableGroupJoinCollector.addAll(joinedGroup.getTableGroupJoins());
            } else {
                processTableGroupJoins(joinedGroup);
            }
        } else if (joinedGroup.isInitialized()) {
            renderTableGroupJoin(
                    source,
                    tableGroupJoin,
                    tableGroupJoinCollector
            );
        }
        // A lazy table group, even if uninitialized, might contain table group joins
        else if (joinedGroup instanceof LazyTableGroup) {
            processNestedTableGroupJoins(joinedGroup, tableGroupJoinCollector);
            if (tableGroupJoinCollector != null) {
                tableGroupJoinCollector.addAll(joinedGroup.getTableGroupJoins());
            } else {
                processTableGroupJoins(joinedGroup);
            }
        }
    }

    private void renderTableGroupJoin(final TableGroup source, final TableGroupJoin tableGroupJoin,
            @Nullable final List<TableGroupJoin> tableGroupJoinCollector) {
        //appendMql(tableGroupJoin.getJoinType().getText());

        final Predicate predicate;
        if (tableGroupJoin.getPredicate() == null) {
            if (tableGroupJoin.getJoinType() == SqlAstJoinType.CROSS) {
                predicate = null;
            } else {
                predicate = new BooleanExpressionPredicate(new QueryLiteral<>(true, getBooleanType()));
            }
        } else {
            predicate = tableGroupJoin.getPredicate();
        }
        if (predicate != null && !predicate.isEmpty()) {
            renderTableGroup(source, tableGroupJoin.getJoinedGroup(), predicate, tableGroupJoinCollector);
        } else {
            renderTableGroup(source, tableGroupJoin.getJoinedGroup(), null, tableGroupJoinCollector);
        }
    }

    private void renderTableGroup(
            final TableGroup source,
            final TableGroup tableGroup,
            @Nullable final Predicate predicate,
            @Nullable final List<TableGroupJoin> tableGroupJoinCollector) {

        // Without reference joins or nested join groups, even a real table group does not need parenthesis
        final boolean realTableGroup = tableGroup.isRealTableGroup()
                && (CollectionHelper.isNotEmpty(tableGroup.getTableReferenceJoins())
                || hasNestedTableGroupsToRender(tableGroup.getNestedTableGroupJoins()));
        if (realTableGroup) {
            //appendMql(OPEN_PARENTHESIS);
        }

        //final boolean usesLockHint = renderPrimaryTableReference(tableGroup, effectiveLockMode);
        final List<TableGroupJoin> tableGroupJoins;

        if (realTableGroup) {
            // For real table groups, we collect all normal table group joins within that table group
            // The purpose of that is to render them in-order outside of the group/parenthesis
            // This is necessary for at least Derby but is also a lot easier to read
            renderTableReferenceJoins(tableGroup);
            if (tableGroupJoinCollector == null) {
                tableGroupJoins = new ArrayList<>();
                processNestedTableGroupJoins(tableGroup, tableGroupJoins);
            } else {
                tableGroupJoins = null;
                processNestedTableGroupJoins(tableGroup, tableGroupJoinCollector);
            }
            //appendMql(CLOSE_PARENTHESIS);
        } else {
            tableGroupJoins = null;
        }

        AstLookupStage lookupStage = mqlAstState.expect(AttachmentKeys.lookupStage(), () -> simulateTableJoining(source, tableGroup, predicate));

        if (tableGroup.isLateral() && !dialect.supportsLateral()) {
            final Predicate lateralEmulationPredicate = determineLateralEmulationPredicate(tableGroup);
            if (lateralEmulationPredicate != null) {
                if (predicate == null) {
                    appendMql(" on ");
                } else {
                    appendMql(" and ");
                }
                lateralEmulationPredicate.accept(this);
            }
        }

        if (!realTableGroup) {
            renderTableReferenceJoins(tableGroup);
            processNestedTableGroupJoins(tableGroup, tableGroupJoinCollector);
        }
        if (tableGroupJoinCollector != null) {
            tableGroupJoinCollector.addAll(tableGroup.getTableGroupJoins());
        } else {
            if (tableGroupJoins != null) {
                for (TableGroupJoin tableGroupJoin : tableGroupJoins) {
                    processTableGroupJoin(source, tableGroupJoin, null);
                }
            }
            if (!tableGroup.getTableGroupJoins().isEmpty()) {
                appendMql(", pipeline: [");
                List<AstStage> joinStages = mqlAstState.expect(AttachmentKeys.joinStages(), () ->
                        processTableGroupJoins(tableGroup));
                lookupStage = lookupStage.addPipeline(joinStages);
                appendMql(" ]");
            }
        }

        appendMql(" } }, { $unwind: " + writeStringHelper("$" + tableGroup.getPrimaryTableReference().getIdentificationVariable()) + " }");

        mqlAstState.attach(AttachmentKeys.joinStages(), List.of(lookupStage, new AstUnwindStage(tableGroup.getPrimaryTableReference().getIdentificationVariable())));
        ModelPartContainer modelPart = tableGroup.getModelPart();
        if (modelPart instanceof AbstractEntityPersister) {
            String[] querySpaces = (String[]) ((AbstractEntityPersister) modelPart).getQuerySpaces();
            for (String querySpace : querySpaces) {
                registerAffectedTable(querySpace);
            }
        }
    }

    private void simulateTableJoining(final TableGroup sourceTableGroup, final TableGroup targetTableGroup,
            @Nullable final Predicate predicate) {
        if (targetTableGroup.getPrimaryTableReference() instanceof NamedTableReference namedTargetTableReference) {
            var sourceQualifier = sourceTableGroup.getPrimaryTableReference().getIdentificationVariable();
            appendMql("{ $lookup: { from: ");
            appendMql(writeStringHelper(namedTargetTableReference.getTableExpression()));

            if (predicate instanceof ComparisonPredicate comparisonPredicate) {
                var targetQualifier = targetTableGroup.getPrimaryTableReference().getIdentificationVariable();

                pathTracker.trackPath(sourceQualifier, targetQualifier);

                ColumnReference sourceColumnReference = null, targetColumnReference = null;
                var leftHandColumnReference = comparisonPredicate.getLeftHandExpression().getColumnReference();
                var rightHandColumnReference = comparisonPredicate.getRightHandExpression().getColumnReference();
                if (leftHandColumnReference.getQualifier().equals(targetQualifier)
                        && rightHandColumnReference.getQualifier().equals(sourceQualifier)) {
                    targetColumnReference = leftHandColumnReference;
                    sourceColumnReference = rightHandColumnReference;
                } else if (leftHandColumnReference.getQualifier().equals(sourceQualifier)
                        && rightHandColumnReference.getQualifier().equals(targetQualifier)) {
                    sourceColumnReference = leftHandColumnReference;
                    targetColumnReference = rightHandColumnReference;
                }
                AstLookupStageMatch lookupStageMatch;
                if (sourceColumnReference != null && targetColumnReference != null) {
                    appendMql(", localField: ");
                    appendMql(writeStringHelper(sourceColumnReference.getColumnExpression()));
                    appendMql(", foreignField: ");
                    appendMql(writeStringHelper(targetColumnReference.getColumnExpression()));
                    lookupStageMatch = new AstLookupStageEqualityMatch(sourceColumnReference.getColumnExpression(),
                            targetColumnReference.getColumnExpression());
                } else {
                    var sourceColumnsInPredicate = getSourceColumnsInPredicate(comparisonPredicate, sourceQualifier);
                    if (!sourceColumnsInPredicate.isEmpty()) {
                        appendMql(", let: {");
                        for (int i = 0; i < sourceColumnsInPredicate.size(); i++) {
                            if (i == 0) {
                                appendMql(' ');
                            } else {
                                appendMql(", ");
                            }
                            var sourceColumn = sourceColumnsInPredicate.get(i);
                            appendMql(String.format("%s_%s: \"$%s\"", sourceQualifier, sourceColumn, sourceColumn));
                        }
                        appendMql(" }");
                    }
                    appendMql(", pipeline: [ { $match: { $expr: ");
                    try {
                        this.targetQualifier = sourceQualifier;
                        setInAggregateExpressionScope(true);
                        predicate.accept(this);
                    } finally {
                        setInAggregateExpressionScope(false);
                        this.targetQualifier = null;
                    }
                    throw new NotYetImplementedException("This appears to be untested code, so haven't added MQL AST support yet");
                }
                appendMql(", as: ");
                appendMql(writeStringHelper(targetQualifier));

                mqlAstState.attach(AttachmentKeys.lookupStage(),
                        new AstLookupStage(namedTargetTableReference.getTableExpression(), targetQualifier, lookupStageMatch, List.of()));
            } else {
                throw new NotYetImplementedException("currently only comparison predicate supported for table joining");
            }
        } else {
            throw new NotYetImplementedException("currently only NamedTableReference supported for table joining");
        }
    }

    private List<String> getSourceColumnsInPredicate(final ComparisonPredicate comparisonPredicate, final String sourceAlias) {
        List<String> sourceColumns = new ArrayList<>();
        List<ColumnReference> columnReferences = new ArrayList<>();
        if (comparisonPredicate.getLeftHandExpression() instanceof ColumnReference columnReference) {
            columnReferences.add(columnReference);
        }
        if (comparisonPredicate.getRightHandExpression() instanceof ColumnReference columnReference) {
            columnReferences.add(columnReference);
        }

        for (ColumnReference columnReference : columnReferences) {
            if (columnReference.getQualifier().equals(sourceAlias) && !columnReference.isColumnExpressionFormula()) {
                sourceColumns.add(columnReference.getColumnExpression());
            }
        }
        return sourceColumns;
    }

    @Override
    public void visitColumnReference(final ColumnReference columnReference) {
        if (targetQualifier != null && !columnReference.isColumnExpressionFormula()) {
            appendMql('"');
            appendMql(columnReference.getQualifier().equals(targetQualifier) ? "$" : ("$$" + columnReference.getQualifier() + "_"));
            appendMql(columnReference.getColumnExpression());
            appendMql('"');
        } else if (queryPartStack.getCurrent() instanceof QuerySpec) {
            if (!columnReference.getQualifier().equals(pathTracker.getRootQualifier())) {
                appendMql('"');
                appendMql(columnReference.getQualifier() + "." + columnReference.getColumnExpression());
                appendMql('"');
            } else {
                appendMql(columnReference.getColumnExpression());
                mqlAstState.attach(AttachmentKeys.fieldName(), columnReference.getColumnExpression());
            }
        } else {
            columnReference.appendReadExpression(this, null);
        }
    }

    @Override
    protected void renderComparisonStandard(final Expression lhs, final ComparisonOperator operator, final Expression rhs) {
        if (isInAggregateExpressionScope()) {
            appendMql("{ ");
            appendMql(getMongoOperatorText(operator));
            appendMql(": [ ");
            lhs.accept(this);
            appendMql(", ");
            rhs.accept(this);
            appendMql(" ] }");
        } else {
            appendMql("{ ");
            String fieldName = mqlAstState.expect(AttachmentKeys.fieldName(), () -> lhs.accept(this));
            appendMql(": { ");
            appendMql(getMongoOperatorText(operator));
            appendMql(": ");
            AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () -> rhs.accept(this));
            appendMql(" } }");
            mqlAstState.attach(AttachmentKeys.filter(),
                    new AstFieldOperationFilter(new AstFilterField(fieldName),
                            new AstComparisonFilterOperation(getComparisonFilterOperator(operator), value)));
        }
    }

    @Override
    protected void renderOrderBy(final boolean addWhitespace, final List<SortSpecification> sortSpecifications) {
        List<AstSortField> sortFields = new ArrayList<>();
        if (sortSpecifications != null && !sortSpecifications.isEmpty()) {
            if (addWhitespace) {
                appendMql(WHITESPACE);
            }
            appendMql(" { ");

            clauseStack.push(Clause.ORDER);
            try {
                String separator = NO_SEPARATOR;
                for (SortSpecification sortSpecification : sortSpecifications) {
                    appendMql(separator);
                    sortFields.add(mqlAstState.expect(AttachmentKeys.sortField(), () -> visitSortSpecification(sortSpecification)));
                    separator = COMMA_SEPARATOR;
                }
            } finally {
                appendMql(" } ");
                clauseStack.pop();
            }
        }
        mqlAstState.attach(AttachmentKeys.sortFields(), sortFields);
    }

    @Override
    protected void visitSortSpecification(
            final Expression sortExpression,
            final SortDirection sortOrder,
            final NullPrecedence nullPrecedence,
            final boolean ignoreCase) {
        if (nullPrecedence == NullPrecedence.LAST) {
            throw new NotSupportedRuntimeException("Mongo only supports 'null goes first'");
        }

        String fieldName = mqlAstState.expect(AttachmentKeys.fieldName(), () -> renderSortExpression(sortExpression, ignoreCase));

        appendMql(": ");

        AstSortOrder astSortOrder;
        if (sortOrder == SortDirection.DESCENDING) {
            appendMql("-1");
            astSortOrder = new AstDescendingSortOrder();
        } else if (sortOrder == SortDirection.ASCENDING) {
            appendMql("1");
            astSortOrder = new AstAscendingSortOrder();
        } else {
            throw new NotYetImplementedException("Unclear if there are any other sort orders");
        }
        mqlAstState.attach(AttachmentKeys.sortField(), new AstSortField(fieldName, astSortOrder));
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

    private AstComparisonFilterOperator getComparisonFilterOperator(final ComparisonOperator operator) {
        return switch (operator) {
            case EQUAL -> AstComparisonFilterOperator.EQ;
            case NOT_EQUAL -> AstComparisonFilterOperator.NE;
            case LESS_THAN -> AstComparisonFilterOperator.LT;
            case LESS_THAN_OR_EQUAL -> AstComparisonFilterOperator.LTE;
            case GREATER_THAN -> AstComparisonFilterOperator.GT;
            case GREATER_THAN_OR_EQUAL -> AstComparisonFilterOperator.GTE;
            default -> throw new NotSupportedRuntimeException("unsupported operator: " + operator.name());
        };
    }

}
