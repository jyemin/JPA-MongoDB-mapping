package org.hibernate.omm.dialect;

import com.mongodb.lang.Nullable;
import org.hibernate.LockOptions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.internal.util.collections.StandardStack;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.persister.internal.SqlFragmentPredicate;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.sqm.tree.expression.Conversion;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.delete.DeleteStatement;
import org.hibernate.sql.ast.tree.expression.AggregateColumnWriteExpression;
import org.hibernate.sql.ast.tree.expression.Any;
import org.hibernate.sql.ast.tree.expression.BinaryArithmeticExpression;
import org.hibernate.sql.ast.tree.expression.CaseSearchedExpression;
import org.hibernate.sql.ast.tree.expression.CaseSimpleExpression;
import org.hibernate.sql.ast.tree.expression.CastTarget;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Distinct;
import org.hibernate.sql.ast.tree.expression.Duration;
import org.hibernate.sql.ast.tree.expression.DurationUnit;
import org.hibernate.sql.ast.tree.expression.EntityTypeLiteral;
import org.hibernate.sql.ast.tree.expression.Every;
import org.hibernate.sql.ast.tree.expression.ExtractUnit;
import org.hibernate.sql.ast.tree.expression.Format;
import org.hibernate.sql.ast.tree.expression.JdbcLiteral;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.ast.tree.expression.ModifiedSubQueryExpression;
import org.hibernate.sql.ast.tree.expression.Over;
import org.hibernate.sql.ast.tree.expression.Overflow;
import org.hibernate.sql.ast.tree.expression.QueryLiteral;
import org.hibernate.sql.ast.tree.expression.SelfRenderingExpression;
import org.hibernate.sql.ast.tree.expression.SqlSelectionExpression;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.expression.SqlTupleContainer;
import org.hibernate.sql.ast.tree.expression.Star;
import org.hibernate.sql.ast.tree.expression.Summarization;
import org.hibernate.sql.ast.tree.expression.TrimSpecification;
import org.hibernate.sql.ast.tree.expression.UnaryOperation;
import org.hibernate.sql.ast.tree.expression.UnparsedNumericLiteral;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.from.FunctionTableReference;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.from.QueryPartTableReference;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.from.TableReferenceJoin;
import org.hibernate.sql.ast.tree.from.ValuesTableReference;
import org.hibernate.sql.ast.tree.insert.InsertSelectStatement;
import org.hibernate.sql.ast.tree.insert.Values;
import org.hibernate.sql.ast.tree.predicate.BetweenPredicate;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.hibernate.sql.ast.tree.predicate.ComparisonPredicate;
import org.hibernate.sql.ast.tree.predicate.ExistsPredicate;
import org.hibernate.sql.ast.tree.predicate.FilterPredicate;
import org.hibernate.sql.ast.tree.predicate.GroupedPredicate;
import org.hibernate.sql.ast.tree.predicate.InArrayPredicate;
import org.hibernate.sql.ast.tree.predicate.InListPredicate;
import org.hibernate.sql.ast.tree.predicate.InSubQueryPredicate;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.hibernate.sql.ast.tree.predicate.LikePredicate;
import org.hibernate.sql.ast.tree.predicate.NegatedPredicate;
import org.hibernate.sql.ast.tree.predicate.NullnessPredicate;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.predicate.SelfRenderingPredicate;
import org.hibernate.sql.ast.tree.predicate.ThruthnessPredicate;
import org.hibernate.sql.ast.tree.select.QueryGroup;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.hibernate.sql.ast.tree.select.SelectClause;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.hibernate.sql.ast.tree.update.UpdateStatement;
import org.hibernate.sql.exec.internal.JdbcOperationQueryInsertImpl;
import org.hibernate.sql.exec.spi.JdbcLockStrategy;
import org.hibernate.sql.exec.spi.JdbcOperation;
import org.hibernate.sql.exec.spi.JdbcOperationQueryDelete;
import org.hibernate.sql.exec.spi.JdbcOperationQueryInsert;
import org.hibernate.sql.exec.spi.JdbcOperationQuerySelect;
import org.hibernate.sql.exec.spi.JdbcOperationQueryUpdate;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.exec.spi.JdbcParameterBinding;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.ast.ColumnWriteFragment;
import org.hibernate.sql.model.ast.RestrictedTableMutation;
import org.hibernate.sql.model.ast.TableMutation;
import org.hibernate.sql.model.internal.OptionalTableUpdate;
import org.hibernate.sql.model.internal.TableDeleteCustomSql;
import org.hibernate.sql.model.internal.TableDeleteStandard;
import org.hibernate.sql.model.internal.TableInsertCustomSql;
import org.hibernate.sql.model.internal.TableInsertStandard;
import org.hibernate.sql.model.internal.TableUpdateCustomSql;
import org.hibernate.sql.model.internal.TableUpdateStandard;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingProducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;
import static org.hibernate.sql.ast.SqlTreePrinter.logSqlAst;
import static org.hibernate.sql.results.graph.DomainResultGraphPrinter.logDomainResultGraph;

/**
 * Central class focusing on MongoDB specific BSON/JSON command string as per
 * <a href="https://www.mongodb.com/docs/manual/reference/command/">mongodb command references</a>
 *
 * @param <T> {@link JdbcOperation} generics type
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoBsonAstTranslator<T extends JdbcOperation> implements SqlAstTranslator<T>, SqlAppender {

    private final SessionFactoryImplementor sessionFactory;
    private final Statement statement;
    private final Dialect dialect;

    private final StringBuilder sqlBuffer = new StringBuilder();
    private final Stack<Clause> clauseStack = new StandardStack<>(Clause.class);
    private final Stack<QueryPart> queryPartStack = new StandardStack<>(QueryPart.class);
    private final Stack<Statement> statementStack = new StandardStack<>(Statement.class);
    private final Set<String> affectedTableNames = new HashSet<>();

    private final List<JdbcParameterBinder> parameterBinders = new ArrayList<>();
    private final Map<JdbcParameter, JdbcParameterBinding> appliedParameterBindings = new IdentityHashMap<>();

    private SqlAstNodeRenderingMode parameterRenderingMode = SqlAstNodeRenderingMode.DEFAULT;

    @Nullable
    private Predicate additionalWherePredicate;
    @Nullable
    private JdbcParameterBindings jdbcParameterBindings;
    @Nullable
    private LockOptions lockOptions; // TODO investigate the MongoDB counterpart
    @Nullable
    private Limit limit;
    @Nullable
    private JdbcParameter offsetParameter;
    @Nullable
    private JdbcParameter limitParameter;

    public MongoBsonAstTranslator(SessionFactoryImplementor sessionFactory, Statement statement) {
        this.sessionFactory = sessionFactory;
        this.statement = statement;
        this.dialect = sessionFactory.getJdbcServices().getDialect();
    }

    @Override
    public SessionFactoryImplementor getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void render(SqlAstNode sqlAstNode, SqlAstNodeRenderingMode renderingMode) {
        SqlAstNodeRenderingMode original = this.parameterRenderingMode;
        if (original != SqlAstNodeRenderingMode.INLINE_ALL_PARAMETERS) {
            this.parameterRenderingMode = renderingMode;
        }
        try {
            sqlAstNode.accept(this);
        } finally {
            this.parameterRenderingMode = original;
        }
    }

    @Override
    public boolean supportsFilterClause() {
        return false;
    }

    @Override
    public QueryPart getCurrentQueryPart() {
        return queryPartStack.getCurrent();
    }

    @Override
    public Stack<Clause> getCurrentClauseStack() {
        return clauseStack;
    }

    @Override
    public Set<String> getAffectedTableNames() {
        return affectedTableNames;
    }

    @Override
    public T translate(JdbcParameterBindings jdbcParameterBindings, QueryOptions queryOptions) {
        try {
            this.jdbcParameterBindings = jdbcParameterBindings;

            Statement statement = statementStack.pop();

            if (statement instanceof TableMutation) {
                return translateTableMutation((TableMutation<?>) statement);
            }

            this.lockOptions = queryOptions.getLockOptions().makeCopy();
            this.limit = queryOptions.getLimit() == null ? null : queryOptions.getLimit().makeCopy();
            final JdbcOperation jdbcOperation;
            if (statement instanceof DeleteStatement) {
                jdbcOperation = translateDelete((DeleteStatement) statement);
            } else if (statement instanceof UpdateStatement) {
                jdbcOperation = translateUpdate((UpdateStatement) statement);
            } else if (statement instanceof InsertSelectStatement) {
                jdbcOperation = translateInsert((InsertSelectStatement) statement);
            } else if (statement instanceof SelectStatement) {
                jdbcOperation = translateSelect((SelectStatement) statement);
            } else {
                throw new IllegalArgumentException("Unexpected statement - " + statement);
            }
            //noinspection unchecked
            return (T) jdbcOperation;
        } finally {
            cleanup();
        }
    }

    private JdbcOperationQueryDelete translateDelete(DeleteStatement sqlAst) {
        visitDeleteStatement(sqlAst);

        return new JdbcOperationQueryDelete(
                getSql(),
                parameterBinders,
                affectedTableNames,
                appliedParameterBindings
        );
    }

    private JdbcOperationQueryUpdate translateUpdate(UpdateStatement sqlAst) {
        visitUpdateStatement(sqlAst);

        return new JdbcOperationQueryUpdate(
                getSql(),
                parameterBinders,
                affectedTableNames,
                appliedParameterBindings
        );
    }

    private JdbcOperationQueryInsert translateInsert(InsertSelectStatement sqlAst) {
        visitInsertStatement(sqlAst);

        return new JdbcOperationQueryInsertImpl(
                getSql(),
                parameterBinders,
                affectedTableNames
        );
    }

    private JdbcOperationQuerySelect translateSelect(SelectStatement selectStatement) {
        logDomainResultGraph(selectStatement.getDomainResultDescriptors());
        logSqlAst(selectStatement);

        visitSelectStatement(selectStatement);

        return new JdbcOperationQuerySelect(
                getSql(),
                parameterBinders,
                buildJdbcValuesMappingProducer(selectStatement),
                affectedTableNames,
                0,
                0,
                appliedParameterBindings,
                getJdbcLockStrategy(),
                offsetParameter,
                limitParameter
        );
    }

    private JdbcValuesMappingProducer buildJdbcValuesMappingProducer(SelectStatement selectStatement) {
        return getSessionFactory()
                .getFastSessionServices()
                .getJdbcValuesMappingProducerProvider()
                .buildMappingProducer(selectStatement, getSessionFactory());
    }

    private boolean hasLimit() {
        return limit != null && !limit.isEmpty();
    }

    private JdbcLockStrategy getJdbcLockStrategy() {
        return lockOptions == null ? JdbcLockStrategy.FOLLOW_ON : JdbcLockStrategy.NONE;
    }

    private T translateTableMutation(TableMutation<?> mutation) {
        mutation.accept(this);
        //noinspection unchecked
        return (T) mutation.createMutationOperation(getSql(), parameterBinders);
    }

    protected void cleanup() {
        this.jdbcParameterBindings = null;
        this.lockOptions = null;
        this.limit = null;
        this.offsetParameter = null;
        this.limitParameter = null;
    }

    private String getSql() {
        return sqlBuffer.toString();
    }

    @Override
    public void visitSelectStatement(SelectStatement statement) {

    }

    @Override
    public void visitDeleteStatement(DeleteStatement statement) {
        try {
            statementStack.push(statement);
            //visitCteContainer( statement ); TODO render with aggregate
            visitDeleteStatementOnly(statement);
        } finally {
            statementStack.pop();
        }
    }

    private void visitDeleteStatementOnly(DeleteStatement statement) {
        if (statement.getReturningColumns() != null) {
            throw new NotSupportedRuntimeException("delete statement with returning columns not supported");
        }
        if (statement.getFromClause().getRoots().size() > 1) {
            throw new NotSupportedRuntimeException("delete statement with multiple roots not supported");
        }
        if (statement.getFromClause().getRoots().get(0).hasRealJoins()) {
            throw new NotSupportedRuntimeException("delete statement with root having real joins not supported");
        }
        renderDeleteClause(statement);
        appendSql(", deletes: [ { q: ");
        if (statement.getRestriction() != null) {
            visitWhereClause(statement.getRestriction());
        } else {
            appendSql("{ }");
        }
        appendSql(", limit: 0 } ] }");
    }

    private void renderDeleteClause(DeleteStatement statement) {
        appendSql("{ delete: ");
        try {
            clauseStack.push(Clause.DELETE);
            renderDmlTargetTableExpression(statement.getTargetTable());
        } finally {
            clauseStack.pop();
        }
    }

    private void renderDmlTargetTableExpression(NamedTableReference tableReference) {
        appendSql(writeStringHelper(tableReference.getTableExpression()));
        registerAffectedTable(tableReference);
    }

    private void visitWhereClause(Predicate whereClauseRestrictions) {
        final Predicate additionalWherePredicate = this.additionalWherePredicate;
        boolean existsWhereClauseRestrictions = whereClauseRestrictions != null && !whereClauseRestrictions.isEmpty();
        boolean existsAdditionalWherePredicate = additionalWherePredicate != null;
        boolean requiredAndPredicate = existsWhereClauseRestrictions && existsAdditionalWherePredicate;
        if (existsWhereClauseRestrictions || existsAdditionalWherePredicate) {
            if (requiredAndPredicate) {
                appendSql("{ $and: [ ");
            }
            clauseStack.push(Clause.WHERE);
            try {
                if (existsWhereClauseRestrictions) {
                    whereClauseRestrictions.accept(this);
                }
                if (requiredAndPredicate) {
                    appendSql(", ");
                }
                if (existsAdditionalWherePredicate) {
                    this.additionalWherePredicate = null;
                    additionalWherePredicate.accept(this);
                }
                if (requiredAndPredicate) {
                    appendSql(" ] }");
                }
            } finally {
                clauseStack.pop();
            }
        } else {
            appendSql("{ }");
        }
    }

    @Override
    public void visitUpdateStatement(UpdateStatement statement) {
        try {
            statementStack.push(statement);
            //visitCteContainer( statement ); // TODO implement using aggregate
            visitUpdateStatementOnly(statement);
        } finally {
            statementStack.pop();
        }
    }

    private void visitUpdateStatementOnly(UpdateStatement statement) {
        if (statement.getFromClause().getRoots().size() > 1) {
            throw new NotSupportedRuntimeException("update statement with multiple roots not supported");
        }
        if (statement.getFromClause().getRoots().get(0).hasRealJoins()) {
            throw new NotSupportedRuntimeException("update statement with root having real joins not supported");
        }
        appendSql("{ ");
        renderUpdateClause(statement);
        appendSql(", updates: [ ");
        visitWhereClause(statement.getRestriction());
        renderSetClause(statement.getAssignments());
        appendSql(" ] }");
    }

    private void renderSetClause(List<Assignment> assignments) {
        appendSql(" , u: { ");
        var separator = " ";
        try {
            clauseStack.push(Clause.SET);
            for (Assignment assignment : assignments) {
                appendSql(separator);
                separator = ", ";
                visitSetAssignment(assignment);
            }
        } finally {
            appendSql(" }");
            clauseStack.pop();
        }
    }

    private void visitSetAssignment(Assignment assignment) {
        var columnReferences = assignment.getAssignable().getColumnReferences();
        if (columnReferences.size() == 1) {
            columnReferences.get(0).appendColumnForWrite(this, null);
            appendSql(": ");
            var assignedValue = assignment.getAssignedValue();
            var sqlTuple = SqlTupleContainer.getSqlTuple(assignedValue);
            if (sqlTuple != null) {
                assert sqlTuple.getExpressions().size() == 1;
                sqlTuple.getExpressions().get(0).accept(this);
            } else {
                assignedValue.accept(this);
            }
        } else {
            throw new NotSupportedRuntimeException("multiple column assignment not supported");
        }
    }

    private void renderUpdateClause(UpdateStatement updateStatement) {
        appendSql("update: ");
        try {
            clauseStack.push(Clause.UPDATE);
            renderDmlTargetTableExpression(updateStatement.getTargetTable());
        } finally {
            clauseStack.pop();
        }
    }

    @Override
    public void visitInsertStatement(InsertSelectStatement statement) {
        try {
            statementStack.push(statement);
            //visitCteContainer( statement ); // TODO implement with aggregate
            visitInsertStatementOnly(statement);
        } finally {
            statementStack.pop();
        }
    }

    private void visitInsertStatementOnly(InsertSelectStatement statement) {
        clauseStack.push(Clause.INSERT);
        appendSql("{ ");

        registerAffectedTable(statement.getTargetTable());

        clauseStack.push(Clause.VALUES);

        {
            if (statement.getSourceSelectStatement() != null) {
                // TODO implement using aggregate command
            } else {
                appendSql("insert: ");
                appendSql(writeStringHelper(statement.getTargetTable().getTableExpression()));
                appendSql(", documents: [");
                List<ColumnReference> targetColumnReferences = statement.getTargetColumns();

                if (targetColumnReferences != null) {
                    List<Values> targetColumnValuesList = statement.getValuesList();
                    for (int valuesIndex = 0; valuesIndex < statement.getValuesList().size(); valuesIndex++) {
                        appendSql(valuesIndex == 0 ? " {" : ", {");
                        for (int columnIndex = 0; columnIndex < targetColumnReferences.size(); columnIndex++) {
                            if (columnIndex == 0) {
                                appendSql(' ');
                            } else {
                                appendSql(", ");
                            }
                            appendSql(targetColumnReferences.get(columnIndex).getColumnExpression());
                            appendSql(": ");
                            targetColumnValuesList.get(valuesIndex).getExpressions().get(columnIndex).accept(this);
                        }
                        appendSql(" }");
                    }
                }
                appendSql(" ]");
            }
            clauseStack.pop();
        }

        appendSql(" }");
        clauseStack.pop();
    }

    private void registerAffectedTable(NamedTableReference tableReference) {
        tableReference.applyAffectedTableNames(this::registerAffectedTable);
    }

    private void registerAffectedTable(String tableExpression) {
        affectedTableNames.add(tableExpression);
    }

    @Override
    public void visitAssignment(Assignment assignment) {

    }

    @Override
    public void visitQueryGroup(QueryGroup queryGroup) {

    }

    @Override
    public void visitQuerySpec(QuerySpec querySpec) {

    }

    @Override
    public void visitSortSpecification(SortSpecification sortSpecification) {

    }

    @Override
    public void visitOffsetFetchClause(QueryPart querySpec) {

    }

    @Override
    public void visitSelectClause(SelectClause selectClause) {

    }

    @Override
    public void visitSqlSelection(SqlSelection sqlSelection) {

    }

    @Override
    public void visitFromClause(FromClause fromClause) {

    }

    @Override
    public void visitTableGroup(TableGroup tableGroup) {

    }

    @Override
    public void visitTableGroupJoin(TableGroupJoin tableGroupJoin) {

    }

    @Override
    public void visitNamedTableReference(NamedTableReference tableReference) {

    }

    @Override
    public void visitValuesTableReference(ValuesTableReference tableReference) {

    }

    @Override
    public void visitQueryPartTableReference(QueryPartTableReference tableReference) {

    }

    @Override
    public void visitFunctionTableReference(FunctionTableReference tableReference) {

    }

    @Override
    public void visitTableReferenceJoin(TableReferenceJoin tableReferenceJoin) {

    }

    @Override
    public void visitColumnReference(ColumnReference columnReference) {

    }

    @Override
    public void visitAggregateColumnWriteExpression(AggregateColumnWriteExpression aggregateColumnWriteExpression) {

    }

    @Override
    public void visitExtractUnit(ExtractUnit extractUnit) {

    }

    @Override
    public void visitFormat(Format format) {

    }

    @Override
    public void visitDistinct(Distinct distinct) {

    }

    @Override
    public void visitOverflow(Overflow overflow) {

    }

    @Override
    public void visitStar(Star star) {

    }

    @Override
    public void visitTrimSpecification(TrimSpecification trimSpecification) {

    }

    @Override
    public void visitCastTarget(CastTarget castTarget) {

    }

    @Override
    public void visitBinaryArithmeticExpression(BinaryArithmeticExpression arithmeticExpression) {

    }

    @Override
    public void visitCaseSearchedExpression(CaseSearchedExpression caseSearchedExpression) {

    }

    @Override
    public void visitCaseSimpleExpression(CaseSimpleExpression caseSimpleExpression) {

    }

    @Override
    public void visitAny(Any any) {

    }

    @Override
    public void visitEvery(Every every) {

    }

    @Override
    public void visitSummarization(Summarization every) {

    }

    @Override
    public void visitOver(Over<?> over) {

    }

    @Override
    public void visitSelfRenderingExpression(SelfRenderingExpression expression) {

    }

    @Override
    public void visitSqlSelectionExpression(SqlSelectionExpression expression) {

    }

    @Override
    public void visitEntityTypeLiteral(EntityTypeLiteral expression) {

    }

    @Override
    public void visitTuple(SqlTuple tuple) {

    }

    @Override
    public void visitCollation(Collation collation) {

    }

    @Override
    public void visitParameter(JdbcParameter jdbcParameter) {

    }

    @Override
    public void visitJdbcLiteral(JdbcLiteral<?> jdbcLiteral) {

    }

    @Override
    public void visitQueryLiteral(QueryLiteral<?> queryLiteral) {

    }

    @Override
    public <N extends Number> void visitUnparsedNumericLiteral(UnparsedNumericLiteral<N> literal) {

    }

    @Override
    public void visitUnaryOperationExpression(UnaryOperation unaryOperationExpression) {

    }

    @Override
    public void visitModifiedSubQueryExpression(ModifiedSubQueryExpression expression) {

    }

    @Override
    public void visitBooleanExpressionPredicate(BooleanExpressionPredicate booleanExpressionPredicate) {

    }

    @Override
    public void visitBetweenPredicate(BetweenPredicate betweenPredicate) {

    }

    @Override
    public void visitFilterPredicate(FilterPredicate filterPredicate) {

    }

    @Override
    public void visitFilterFragmentPredicate(FilterPredicate.FilterFragmentPredicate fragmentPredicate) {

    }

    @Override
    public void visitSqlFragmentPredicate(SqlFragmentPredicate predicate) {

    }

    @Override
    public void visitGroupedPredicate(GroupedPredicate groupedPredicate) {

    }

    @Override
    public void visitInListPredicate(InListPredicate inListPredicate) {

    }

    @Override
    public void visitInSubQueryPredicate(InSubQueryPredicate inSubQueryPredicate) {

    }

    @Override
    public void visitInArrayPredicate(InArrayPredicate inArrayPredicate) {

    }

    @Override
    public void visitExistsPredicate(ExistsPredicate existsPredicate) {

    }

    @Override
    public void visitJunction(Junction junction) {

    }

    @Override
    public void visitLikePredicate(LikePredicate likePredicate) {

    }

    @Override
    public void visitNegatedPredicate(NegatedPredicate negatedPredicate) {

    }

    @Override
    public void visitNullnessPredicate(NullnessPredicate nullnessPredicate) {

    }

    @Override
    public void visitThruthnessPredicate(ThruthnessPredicate predicate) {

    }

    @Override
    public void visitRelationalPredicate(ComparisonPredicate comparisonPredicate) {

    }

    @Override
    public void visitSelfRenderingPredicate(SelfRenderingPredicate selfRenderingPredicate) {

    }

    @Override
    public void visitDurationUnit(DurationUnit durationUnit) {

    }

    @Override
    public void visitDuration(Duration duration) {

    }

    @Override
    public void visitConversion(Conversion conversion) {

    }

    @Override
    public void visitStandardTableInsert(TableInsertStandard tableInsert) {
        clauseStack.push(Clause.INSERT);
        try {
            renderInsertInto(tableInsert);

            if (tableInsert.getNumberOfReturningColumns() > 0) {
                throw new NotSupportedRuntimeException("table insert with returning columns not supported");
            }
        } finally {
            clauseStack.pop();
        }
    }

    private void renderInsertInto(TableInsertStandard tableInsert) {
        if (tableInsert.getNumberOfValueBindings() == 0) {
            throw new NotSupportedRuntimeException("no column info");
        }

        sqlBuffer.append("{ insert: }");

        appendSql(writeStringHelper(tableInsert.getMutatingTable().getTableName()));
        registerAffectedTable(tableInsert.getMutatingTable().getTableName());

        sqlBuffer.append(", documents: [ {");

        clauseStack.push(Clause.VALUES);
        {
            tableInsert.forEachValueBinding((position, columnValueBinding) -> {
                if (position == 0) {
                    appendSql(' ');
                } else {
                    appendSql(", ");
                }
                sqlBuffer.append(columnValueBinding.getColumnReference());
                sqlBuffer.append(": ");
                sqlBuffer.append(columnValueBinding.getValueExpression());
            });
        }
        sqlBuffer.append(" } ]");
        clauseStack.pop();
        if (tableInsert.getMutationComment() != null) {
            appendSql(", comment: ");
            appendSql(writeStringHelper(tableInsert.getMutationComment()));
        }
        sqlBuffer.append(" }");
    }

    @Override
    public void visitCustomTableInsert(TableInsertCustomSql tableInsert) {

    }

    @Override
    public void visitStandardTableDelete(TableDeleteStandard tableDelete) {
        clauseStack.push(Clause.DELETE);
        try {
            appendSql("{ delete: ");
            appendSql(writeStringHelper(tableDelete.getMutatingTable().getTableName()));
            registerAffectedTable(tableDelete.getMutatingTable().getTableName());

            clauseStack.push(Clause.WHERE);
            try {
                appendSql(", deletes: [");

                tableDelete.forEachKeyBinding((columnPosition, columnValueBinding) -> {
                    if (columnPosition == 0) {
                        appendSql(' ');
                    } else {
                        appendSql(", ");
                    }
                    appendSql(" { q: { ");
                    appendSql(columnValueBinding.getColumnReference().getColumnExpression());
                    appendSql(": { $eq: ");
                    columnValueBinding.getValueExpression().accept(this);
                    appendSql(" } }, limit: 0 }");
                });

                if (tableDelete.getNumberOfOptimisticLockBindings() > 0) {
                    appendSql(", ");

                    tableDelete.forEachOptimisticLockBinding((columnPosition, columnValueBinding) -> {
                        if (columnPosition == 0) {
                            appendSql(' ');
                        } else {
                            appendSql(", ");
                        }
                        appendSql(" { q: { ");
                        appendSql(columnValueBinding.getColumnReference().getColumnExpression());
                        appendSql(": { $eq: ");
                        columnValueBinding.getValueExpression().accept(this);
                        appendSql(" } }, limit: 0 }");
                    });
                }

                if (tableDelete.getWhereFragment() != null) {
                    appendSql(", { q: ");
                    appendSql(tableDelete.getWhereFragment());
                    appendSql(" }");
                }
            } finally {
                appendSql(" ]");
                if (tableDelete.getMutationComment() != null) {
                    appendSql(", comment: ");
                    appendSql(writeStringHelper(tableDelete.getMutationComment()));
                }
                clauseStack.pop();
            }
        } finally {
            appendSql(" }");
            clauseStack.pop();
        }
    }

    @Override
    public void visitCustomTableDelete(TableDeleteCustomSql tableDelete) {

    }

    @Override
    public void visitStandardTableUpdate(TableUpdateStandard tableUpdate) {
        clauseStack.push(Clause.UPDATE);
        try {
            visitTableUpdate(tableUpdate);
        } finally {
            clauseStack.pop();
        }
    }

    private void visitTableUpdate(RestrictedTableMutation<? extends MutationOperation> tableUpdate) {
        appendSql("{ update: ");
        appendSql(writeStringHelper(tableUpdate.getMutatingTable().getTableName()));
        registerAffectedTable(tableUpdate.getMutatingTable().getTableName());

        appendSql(", updates: [ {");
        {
            clauseStack.push(Clause.WHERE);
            try {
                appendSql(" q:");
                int predicates = tableUpdate.getNumberOfKeyBindings() + tableUpdate.getNumberOfOptimisticLockBindings();
                boolean hasWhereFragment =
                        tableUpdate instanceof TableUpdateStandard tableUpdateStandard && tableUpdateStandard.getWhereFragment() != null;
                if (hasWhereFragment) {
                    predicates++;
                }
                if (predicates == 0) {
                    appendSql("{ }");
                } else {
                    if (predicates > 1) {
                        appendSql(" { $and: [");
                    }
                    tableUpdate.forEachKeyBinding((position, columnValueBinding) -> {
                        if (position == 0) {
                            appendSql(' ');
                        } else {
                            appendSql(", ");
                        }
                        appendSql("{ ");
                        appendSql(columnValueBinding.getColumnReference().getColumnExpression());
                        appendSql(": { $eq: ");
                        columnValueBinding.getValueExpression().accept(this);
                        appendSql(" } }");
                    });

                    if (tableUpdate.getNumberOfOptimisticLockBindings() > 0) {
                        tableUpdate.forEachOptimisticLockBinding((position, columnValueBinding) -> {
                            appendSql(", { ");
                            appendSql(columnValueBinding.getColumnReference().getColumnExpression());
                            appendSql(": { $eq: ");
                            if (columnValueBinding.getValueExpression() == null) {
                                appendSql("null");
                            } else {
                                columnValueBinding.getValueExpression().accept(this);
                            }
                            appendSql(" } }");
                        });
                    }

                    if (hasWhereFragment) {
                        appendSql(", ");
                        appendSql(((TableUpdateStandard) tableUpdate).getWhereFragment());
                    }
                    if (predicates > 1) {
                        appendSql(" ] }");
                    }
                }
            } finally {
                clauseStack.pop();
            }

            appendSql(", u: { $set: {");
            {
                clauseStack.push(Clause.SET);
                try {
                    tableUpdate.forEachValueBinding((columnPosition, columnValueBinding) -> {
                        if (columnPosition == 0) {
                            appendSql(' ');
                        } else {
                            appendSql(", ");
                        }
                        sqlBuffer.append(columnValueBinding.getColumnReference().getColumnExpression());
                        sqlBuffer.append(": ");
                        columnValueBinding.getValueExpression().accept(this);
                    });
                } finally {
                    clauseStack.pop();
                }
                appendSql(" } }");
            }
        }
        appendSql(" } ] }");
    }

    @Override
    public void visitOptionalTableUpdate(OptionalTableUpdate tableUpdate) {

    }

    @Override
    public void visitCustomTableUpdate(TableUpdateCustomSql tableUpdate) {

    }

    @Override
    public void visitColumnWriteFragment(ColumnWriteFragment columnWriteFragment) {

    }

    @Override
    public void appendSql(String fragment) {
        sqlBuffer.append(fragment);
    }

    @Override
    public void appendSql(char fragment) {
        sqlBuffer.append(fragment);
    }

    @Override
    public void appendSql(int value) {
        sqlBuffer.append(value);
    }

    @Override
    public void appendSql(long value) {
        sqlBuffer.append(value);
    }

    @Override
    public void appendSql(boolean value) {
        sqlBuffer.append(value);
    }

    @Override
    public Appendable append(CharSequence csq) {
        sqlBuffer.append(csq);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) {
        sqlBuffer.append(csq, start, end);
        return this;
    }

    @Override
    public Appendable append(char c) {
        sqlBuffer.append(c);
        return this;
    }
}
