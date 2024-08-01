package org.hibernate.omm.ast;

import com.mongodb.lang.Nullable;
import org.hibernate.dialect.SelectItemReferenceStrategy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.exception.NotYetImplementedException;
import org.hibernate.omm.util.CollectionUtil;
import org.hibernate.persister.entity.AbstractEntityPersister;
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
import org.hibernate.sql.exec.spi.JdbcOperationQuerySelect;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;

public class MongoSelectQueryAstTranslator extends AbstractMongoQuerySqlTranslator<JdbcOperationQuerySelect> {

    private static class PathTracker {
        private final Map<String, String> pathByQualifier = new HashMap<>();
        @Nullable
        private String rootQualifier;

        public void setRootQualifier(String rootQualifier) {
            this.rootQualifier = rootQualifier;
            pathByQualifier.put(rootQualifier, null);
        }

        public @Nullable String getRootQualifier() {
            return rootQualifier;
        }

        public void trackPath(String sourceQualifier, String targetQualifier) {
            var sourcePath = pathByQualifier.get(sourceQualifier);
            if (sourcePath != null) {
                pathByQualifier.put(targetQualifier, sourcePath + "." + targetQualifier);
            } else {
                pathByQualifier.put(targetQualifier, targetQualifier);
            }
        }

        public String renderColumnReference(ColumnReference columnReference) {
            var path = pathByQualifier.get(columnReference.getQualifier());
            if (path == null) {
                return columnReference.getColumnExpression();
            } else {
                return path + "." + columnReference.getColumnExpression();
            }
        }
    }

    private final PathTracker pathTracker = new PathTracker();

    @Nullable
    private String targetQualifier;
    private boolean inAggregateExpressionScope;

    public MongoSelectQueryAstTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    public void visitQuerySpec(QuerySpec querySpec) {
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
                        appendSql("select");
                        appendSql(queryGroupAlias);
                        appendSql(".* from ");
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
            appendSql("{ aggregate: ");
            visitFromClause(querySpec.getFromClause());
            appendSql(", { $match: ");
            visitWhereClause(querySpec.getWhereClauseRestrictions());

            if (CollectionUtil.isNotEmpty(querySpec.getSortSpecifications())) {
                appendSql(" }, { $sort: ");
                visitOrderBy(querySpec.getSortSpecifications());
            }
            append(" }, { $project: ");
            visitSelectClause(querySpec.getSelectClause());
            //visitGroupByClause( querySpec, dialect.getGroupBySelectItemReferenceStrategy() );
            //visitHavingClause( querySpec );
            visitOffsetFetchClause(querySpec);
            // We render the FOR UPDATE clause in the parent query
            //if ( queryPartForRowNumbering == null ) {
            //visitForUpdateClause( querySpec );
            //}
            appendSql(" } ], cursor: {} }");
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
    public void visitFromClause(@Nullable FromClause fromClause) {
        if (fromClause == null || fromClause.getRoots().isEmpty()) {
            throw new NotSupportedRuntimeException("null fromClause or empty root not supported");
        } else {
            renderFromClauseSpaces(fromClause);
        }
    }

    @Override
    protected void renderFromClauseSpaces(FromClause fromClause) {
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

    private void renderFromClauseRoot(TableGroup root) {
        if (root.isVirtual()) {
            throw new NotYetImplementedException();
        } else if (root.isInitialized()) {
            renderRootTableGroup(root, null);
        }
    }

    @Override
    protected void renderRootTableGroup(TableGroup tableGroup, @Nullable List<TableGroupJoin> tableGroupJoinCollector) {

        renderTableReferenceJoins(tableGroup);
        processNestedTableGroupJoins(tableGroup, tableGroupJoinCollector);
        if (tableGroupJoinCollector != null) {
            tableGroupJoinCollector.addAll(tableGroup.getTableGroupJoins());
        } else {
            tableGroup.getPrimaryTableReference().accept(this);
            appendSql(", pipeline: [");
            processTableGroupJoins(tableGroup);
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
    protected void processTableGroupJoins(TableGroup source) {
        if (!source.getTableGroupJoins().isEmpty()) {
            for (int i = 0; i < source.getTableGroupJoins().size(); i++) {
                if (i == 0) {
                    appendSql(' ');
                } else {
                    appendSql(", ");
                }
                processTableGroupJoin(source, source.getTableGroupJoins().get(i), null);
            }
        }
    }

    @Override
    public void visitSelectClause(SelectClause selectClause) {
        clauseStack.push(Clause.SELECT);
        appendSql("{ ");
        try {
			/*if ( selectClause.isDistinct() ) {
				appendSql( "distinct " );
			}*/
            visitSqlSelections(selectClause);
            appendSql(" }");
        } finally {
            clauseStack.pop();
        }
    }

    @Override
    protected void visitSqlSelections(SelectClause selectClause) {
        final List<SqlSelection> sqlSelections = selectClause.getSqlSelections();
        final int size = sqlSelections.size();
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
                        appendSql(separator);
                        renderSelectExpression(e);
                        appendSql(WHITESPACE);
                        if (columnAliases == null) {
                            appendSql('c');
                            appendSql(offset);
                        } else {
                            appendSql(columnAliases.get(offset));
                        }
                        offset++;
                        separator = COMMA_SEPARATOR;
                    }
                } else {
                    appendSql(separator);
                    renderSelectExpression(expression);
                    appendSql(WHITESPACE);
                    if (columnAliases == null) {
                        appendSql('c');
                        appendSql(offset);
                    } else {
                        appendSql(columnAliases.get(offset));
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
                appendSql(separator);
                if (selectItemsToInline != null && selectItemsToInline.get(i)) {
                    parameterRenderingMode = SqlAstNodeRenderingMode.INLINE_ALL_PARAMETERS;
                } else {
                    parameterRenderingMode = defaultRenderingMode;
                }
                if (sqlSelection.getExpression() instanceof ColumnReference columnReference) {
                    appendSql("f" + i); // field name doesn't matter for Hibernate ResultSet retrieval only relies on order since v6
                    appendSql(": \"$");
                    appendSql(pathTracker.renderColumnReference(columnReference));
                    appendSql('"');
                } else {
                    visitSqlSelection(sqlSelection);
                    appendSql(": 1");
                }
                parameterRenderingMode = original;
                separator = ", ";
            }
            appendSql(", _id: 0");
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

    private void processTableGroupJoin(TableGroup source, TableGroupJoin tableGroupJoin, @Nullable List<TableGroupJoin> tableGroupJoinCollector) {
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

    private void renderTableGroupJoin(TableGroup source, TableGroupJoin tableGroupJoin, @Nullable List<TableGroupJoin> tableGroupJoinCollector) {
        //appendSql(tableGroupJoin.getJoinType().getText());

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
            TableGroup source,
            TableGroup tableGroup,
            @Nullable Predicate predicate,
            @Nullable List<TableGroupJoin> tableGroupJoinCollector) {

        // Without reference joins or nested join groups, even a real table group does not need parenthesis
        final boolean realTableGroup = tableGroup.isRealTableGroup()
                && (CollectionHelper.isNotEmpty(tableGroup.getTableReferenceJoins())
                || hasNestedTableGroupsToRender(tableGroup.getNestedTableGroupJoins()));
        if (realTableGroup) {
            //appendSql(OPEN_PARENTHESIS);
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
            //appendSql(CLOSE_PARENTHESIS);
        } else {
            tableGroupJoins = null;
        }

        simulateTableJoining(source, tableGroup, predicate);

        if (tableGroup.isLateral() && !dialect.supportsLateral()) {
            final Predicate lateralEmulationPredicate = determineLateralEmulationPredicate(tableGroup);
            if (lateralEmulationPredicate != null) {
                if (predicate == null) {
                    appendSql(" on ");
                } else {
                    appendSql(" and ");
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
                appendSql(", pipeline: [");
                processTableGroupJoins(tableGroup);
                appendSql(" ]");
            }
        }

        appendSql(" } }, { $unwind: " + writeStringHelper("$" + tableGroup.getPrimaryTableReference().getIdentificationVariable()) + " }");

        ModelPartContainer modelPart = tableGroup.getModelPart();
        if (modelPart instanceof AbstractEntityPersister) {
            String[] querySpaces = (String[]) ((AbstractEntityPersister) modelPart).getQuerySpaces();
            for (String querySpace : querySpaces) {
                registerAffectedTable(querySpace);
            }
        }
    }

    private void simulateTableJoining(TableGroup sourceTableGroup, TableGroup targetTableGroup, @Nullable Predicate predicate) {
        if (targetTableGroup.getPrimaryTableReference() instanceof NamedTableReference namedTargetTableReference) {
            var sourceQualifier = sourceTableGroup.getPrimaryTableReference().getIdentificationVariable();
            appendSql("{ $lookup: { from: ");
            appendSql(writeStringHelper(namedTargetTableReference.getTableExpression()));

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
                if (sourceColumnReference != null && targetColumnReference != null) {
                    appendSql(", localField: ");
                    appendSql(writeStringHelper(sourceColumnReference.getColumnExpression()));
                    appendSql(", foreignField: ");
                    appendSql(writeStringHelper(targetColumnReference.getColumnExpression()));
                } else {
                    var sourceColumnsInPredicate = getSourceColumnsInPredicate(comparisonPredicate, sourceQualifier);
                    if (!sourceColumnsInPredicate.isEmpty()) {
                        appendSql(", let: {");
                        for (int i = 0; i < sourceColumnsInPredicate.size(); i++) {
                            if (i == 0) {
                                appendSql(' ');
                            } else {
                                appendSql(", ");
                            }
                            var sourceColumn = sourceColumnsInPredicate.get(i);
                            appendSql(String.format("%s_%s: \"$%s\"", sourceQualifier, sourceColumn, sourceColumn));
                        }
                        appendSql(" }");
                    }
                    appendSql(", pipeline: [ { $match: { $expr: ");
                    try {
                        this.targetQualifier = sourceQualifier;
                        inAggregateExpressionScope = true;
                        predicate.accept(this);
                    } finally {
                        inAggregateExpressionScope = false;
                        this.targetQualifier = null;
                    }
                }
                appendSql(", as: ");
                appendSql(writeStringHelper(targetQualifier));
            } else {
                throw new NotYetImplementedException("currently only comparison predicate supported for table joining");
            }
        } else {
            throw new NotYetImplementedException("currently only NamedTableReference supported for table joining");
        }
    }

    private List<String> getSourceColumnsInPredicate(ComparisonPredicate comparisonPredicate, String sourceAlias) {
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
    public void visitColumnReference(ColumnReference columnReference) {
        if (targetQualifier != null && !columnReference.isColumnExpressionFormula()) {
            appendSql('"');
            appendSql(columnReference.getQualifier().equals(targetQualifier) ? "$" : ("$$" + columnReference.getQualifier() + "_"));
            appendSql(columnReference.getColumnExpression());
            appendSql('"');
        } else if (queryPartStack.getCurrent() instanceof QuerySpec) {
            if (!columnReference.getQualifier().equals(pathTracker.getRootQualifier())) {
                appendSql('"');
                appendSql(columnReference.getQualifier() + "." + columnReference.getColumnExpression());
                appendSql('"');
            } else {
                appendSql(columnReference.getColumnExpression());
            }
        } else {
            columnReferenceAppendReadExpression(columnReference);
        }
    }

    @SuppressWarnings("nullness")
    private void columnReferenceAppendReadExpression(ColumnReference columnReference) {
        columnReference.appendReadExpression(this, null);
    }

    @Override
    protected void renderComparisonStandard(Expression lhs, ComparisonOperator operator, Expression rhs) {
        if (inAggregateExpressionScope) {
            appendSql("{ ");
            appendSql(getMongoOperatorText(operator));
            appendSql(": [ ");
            lhs.accept(this);
            appendSql(", ");
            rhs.accept(this);
            appendSql(" ] }");
        } else {
            appendSql("{ ");
            lhs.accept(this);
            appendSql(": { ");
            appendSql(getMongoOperatorText(operator));
            appendSql(": ");
            rhs.accept(this);
            appendSql(" } }");
        }
    }

    private String getMongoOperatorText(ComparisonOperator operator) {
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
