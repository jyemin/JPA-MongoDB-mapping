package org.hibernate.omm.dialect;

import com.mongodb.lang.Nullable;
import org.bson.assertions.Assertions;
import org.hibernate.LockOptions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.internal.util.collections.StandardStack;
import org.hibernate.persister.internal.SqlFragmentPredicate;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.sqm.tree.expression.Conversion;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.sql.ast.SqlAstTranslator;
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
import org.hibernate.sql.model.ast.ColumnWriteFragment;
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
public class MongoBsonAstTranslator<T extends JdbcOperation> implements SqlAstTranslator<T> {

    private final SessionFactoryImplementor sessionFactory;
    private final Statement statement;
    private final Dialect dialect;

    private final Stack<StringBuilder> bsonStringBuilderStack = new StandardStack<>(StringBuilder.class);
    private final Stack<Clause> clauseStack = new StandardStack<>(Clause.class);
    private final Stack<QueryPart> queryPartStack = new StandardStack<>(QueryPart.class);
    private final Stack<Statement> statementStack = new StandardStack<>(Statement.class);
    private final Set<String> affectedTableNames = new HashSet<>();

    private final List<JdbcParameterBinder> parameterBinders = new ArrayList<>();
    private final Map<JdbcParameter, JdbcParameterBinding> appliedParameterBindings = new IdentityHashMap<>();

    private SqlAstNodeRenderingMode parameterRenderingMode = SqlAstNodeRenderingMode.DEFAULT;
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
        return true;
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
        this.bsonStringBuilderStack.pop();
    }

    private String getSql() {
        Assertions.isTrue("bsonStringBuilderStack's depth should be 1", bsonStringBuilderStack.depth() == 1);
        return bsonStringBuilderStack.getCurrent().toString();
    }

    @Override
    public void visitSelectStatement(SelectStatement statement) {

    }

    @Override
    public void visitDeleteStatement(DeleteStatement statement) {

    }

    @Override
    public void visitUpdateStatement(UpdateStatement statement) {

    }

    @Override
    public void visitInsertStatement(InsertSelectStatement statement) {
        try {
            statementStack.push(statement);

            //visitCteContainer( statement ); // TODO investigate
            visitInsertStatementOnly(statement);
        } finally {
            statementStack.pop();
        }
    }

    private MongoBsonAstTranslator appendBson(String bson) {
        bsonStringBuilderStack.getCurrent().append(bson);
        return this;
    }

    private MongoBsonAstTranslator pushNewBson(String bson) {
        bsonStringBuilderStack.push(new StringBuilder(bson));
        return this;
    }

    private String popBson() {
        return bsonStringBuilderStack.pop().toString();
    }

    private void visitInsertStatementOnly(InsertSelectStatement statement) {
        clauseStack.push(Clause.INSERT);
        pushNewBson("{ ");

        registerAffectedTable(statement.getTargetTable());

        clauseStack.push(Clause.VALUES);

        {
            if (statement.getSourceSelectStatement() != null) {
                // TODO implement using aggregate command
            } else {
                pushNewBson("insert: ")
                        .appendBson(writeStringHelper(statement.getTargetTable().getTableExpression()))
                        .appendBson(", documents: [");
                List<ColumnReference> targetColumnReferences = statement.getTargetColumns();

                if (targetColumnReferences != null) {
                    List<Values> targetColumnValuesList = statement.getValuesList();
                    for (int i = 0; i < statement.getValuesList().size(); i++) {
                        appendBson(i == 0 ? " {" : ", {");
                        for (int j = 0; j < targetColumnReferences.size(); j++) {
                            appendBson(j == 0 ? " " : ", ")
                                    .appendBson(targetColumnReferences.get(j).getColumnExpression())
                                    .appendBson(": ");
                            targetColumnValuesList.get(i).getExpressions().get(j).accept(this);
                        }
                        appendBson(" }");
                    }
                }
                appendBson(" ]");
            }
            clauseStack.pop();
        }

        appendBson(" }");
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

    }

    @Override
    public void visitCustomTableInsert(TableInsertCustomSql tableInsert) {

    }

    @Override
    public void visitStandardTableDelete(TableDeleteStandard tableDelete) {

    }

    @Override
    public void visitCustomTableDelete(TableDeleteCustomSql tableDelete) {

    }

    @Override
    public void visitStandardTableUpdate(TableUpdateStandard tableUpdate) {

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
}
