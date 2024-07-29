package org.hibernate.omm.ast;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.util.CollectionUtil;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.delete.DeleteStatement;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.insert.InsertSelectStatement;
import org.hibernate.sql.ast.tree.insert.Values;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.hibernate.sql.ast.tree.update.UpdateStatement;
import org.hibernate.sql.exec.spi.JdbcOperation;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.ast.RestrictedTableMutation;
import org.hibernate.sql.model.internal.TableDeleteStandard;
import org.hibernate.sql.model.internal.TableInsertStandard;
import org.hibernate.sql.model.internal.TableUpdateStandard;

import java.util.List;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;

public class MongoMutationQuerySqlAstTranslator<T extends JdbcOperation> extends AbstractMongoQuerySqlTranslator<T> {

    public MongoMutationQuerySqlAstTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    public void visitStandardTableInsert(TableInsertStandard tableInsert) {
        getCurrentClauseStack().push(Clause.INSERT);
        try {
            renderInsertInto(tableInsert);
            if (tableInsert.getNumberOfReturningColumns() > 0) {
                visitReturningColumns(tableInsert::getReturningColumns);
            }
        } finally {
            getCurrentClauseStack().pop();
        }
    }

    private void renderInsertInto(TableInsertStandard tableInsert) {
        if (tableInsert.getNumberOfValueBindings() == 0) {
            renderInsertIntoNoColumns(tableInsert);
            return;
        }
        appendSql("{ insert: ");
        renderIntoIntoAndTable(tableInsert);
        appendSql(", documents: [ { ");

        tableInsert.forEachValueBinding((columnPosition, columnValueBinding) -> {
            if (columnPosition != 0) {
                appendSql(", ");
            }
            appendSql(columnValueBinding.getColumnReference().getColumnExpression());
            appendSql(": ");
            columnValueBinding.getValueExpression().accept(this);
        });

        appendSql(" } ]");
        if (tableInsert.getMutationComment() != null) {
            appendSql(", comment: ");
            appendSql(writeStringHelper(tableInsert.getMutationComment()));
        }
        appendSql(" }");
    }

    @Override
    protected void renderIntoIntoAndTable(TableInsertStandard tableInsert) {
        appendSql(writeStringHelper(tableInsert.getMutatingTable().getTableName()));
        registerAffectedTable(tableInsert.getMutatingTable().getTableName());
    }

    @Override
    protected void visitInsertStatementOnly(InsertSelectStatement statement) {
        getClauseStack().push(Clause.INSERT);
        appendSql("{ ");

        registerAffectedTable(statement.getTargetTable());

        getClauseStack().push(Clause.VALUES);

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
        getClauseStack().pop();

        appendSql(" }");
        getClauseStack().pop();
    }

    @Override
    public void visitStandardTableDelete(TableDeleteStandard tableDelete) {
        getClauseStack().push(Clause.DELETE);
        try {
            appendSql("{ delete: ");
            appendSql(writeStringHelper(tableDelete.getMutatingTable().getTableName()));
            registerAffectedTable(tableDelete.getMutatingTable().getTableName());

            getClauseStack().push(Clause.WHERE);
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
                getClauseStack().pop();
            }
        } finally {
            appendSql(" }");
            getClauseStack().pop();
        }
    }

    @Override
    protected void visitDeleteStatementOnly(DeleteStatement statement) {
        if (CollectionUtil.isNotEmpty(statement.getReturningColumns())) {
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

    @Override
    protected void renderDeleteClause(DeleteStatement statement) {
        appendSql("{ delete: ");
        try {
            getClauseStack().push(Clause.DELETE);
            renderDmlTargetTableExpression(statement.getTargetTable());
        } finally {
            getClauseStack().pop();
        }
    }

    @Override
    public void visitStandardTableUpdate(TableUpdateStandard tableUpdate) {
        getClauseStack().push(Clause.UPDATE);
        try {
            visitTableUpdate(tableUpdate);
        } finally {
            getClauseStack().pop();
        }
    }

    private void visitTableUpdate(RestrictedTableMutation<? extends MutationOperation> tableUpdate) {
        appendSql("{ update: ");
        appendSql(writeStringHelper(tableUpdate.getMutatingTable().getTableName()));
        registerAffectedTable(tableUpdate.getMutatingTable().getTableName());

        appendSql(", updates: [ {");
        {
            getClauseStack().push(Clause.WHERE);
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
                getClauseStack().pop();
            }

            appendSql(", u: { $set: {");
            {
                getClauseStack().push(Clause.SET);
                try {
                    tableUpdate.forEachValueBinding((columnPosition, columnValueBinding) -> {
                        if (columnPosition == 0) {
                            appendSql(' ');
                        } else {
                            appendSql(", ");
                        }
                        appendSql(columnValueBinding.getColumnReference().getColumnExpression());
                        appendSql(": ");
                        columnValueBinding.getValueExpression().accept(this);
                    });
                } finally {
                    getClauseStack().pop();
                }
                appendSql(" } }");
            }
        }
        appendSql(" } ] }");
    }

    @Override
    protected void visitUpdateStatementOnly(UpdateStatement statement) {
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

    @Override
    protected void renderUpdateClause(UpdateStatement updateStatement) {
        appendSql("update: ");
        try {
            getClauseStack().push(Clause.UPDATE);
            renderDmlTargetTableExpression(updateStatement.getTargetTable());
        } finally {
            getClauseStack().pop();
        }
    }

    @Override
    protected void renderSetClause(List<Assignment> assignments) {
        appendSql(" , u: { ");
        var separator = " ";
        try {
            getClauseStack().push(Clause.SET);
            for (Assignment assignment : assignments) {
                appendSql(separator);
                separator = ", ";
                visitSetAssignment(assignment);
            }
        } finally {
            appendSql(" }");
            getClauseStack().pop();
        }
    }
}
