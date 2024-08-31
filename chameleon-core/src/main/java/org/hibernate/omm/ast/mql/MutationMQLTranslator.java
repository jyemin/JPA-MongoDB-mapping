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

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.mongoast.AstElement;
import org.hibernate.omm.mongoast.AstInsertCommand;
import org.hibernate.omm.mongoast.AstValue;
import org.hibernate.omm.mongoast.DeleteCommand;
import org.hibernate.omm.mongoast.filters.AstAndFilter;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperation;
import org.hibernate.omm.mongoast.filters.AstComparisonFilterOperator;
import org.hibernate.omm.mongoast.filters.AstFieldOperationFilter;
import org.hibernate.omm.mongoast.filters.AstFilter;
import org.hibernate.omm.mongoast.filters.AstFilterField;
import org.hibernate.omm.mongoast.filters.AstMatchesEverythingFilter;
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

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;

/**
 * Contains mutation MQL rendering overriding logic, including:
 * <ol>
 *     <li>insertion</li>
 *     <li>updating</li>
 *     <li>deletion</li>
 * </ol>
 *
 * @see QueryMQLTranslator
 * @author Nathan Xu
 * @param <T> {@link JdbcOperation} generic type
 */
public class MutationMQLTranslator<T extends JdbcOperation> extends AbstractMQLTranslator<T> {

    public MutationMQLTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    public void visitStandardTableInsert(final TableInsertStandard tableInsert) {
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

    private void renderInsertInto(final TableInsertStandard tableInsert) {
        if (tableInsert.getNumberOfValueBindings() == 0) {
            renderInsertIntoNoColumns(tableInsert);
            return;
        }
        appendMql("{ insert: ");
        String collectionName = mqlAstState.expect(AttachmentKeys.collectionName(), () -> renderIntoIntoAndTable(tableInsert));
        appendMql(", documents: [ { ");

        List<AstElement> elements = new ArrayList<>();
        tableInsert.forEachValueBinding((columnPosition, columnValueBinding) -> {
            if (columnPosition != 0) {
                appendMql(", ");
            }
            appendMql(columnValueBinding.getColumnReference().getColumnExpression());
            appendMql(": ");
            AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                    columnValueBinding.getValueExpression().accept(this));
            elements.add(new AstElement(columnValueBinding.getColumnReference().getColumnExpression(), value));
        });

        appendMql(" } ]");
        if (tableInsert.getMutationComment() != null) {
            appendMql(", comment: ");
            appendMql(writeStringHelper(tableInsert.getMutationComment()));
        }
        appendMql(" }");
        root = new AstInsertCommand(collectionName, elements);
    }

    @Override
    protected void renderIntoIntoAndTable(final TableInsertStandard tableInsert) {
        appendMql(writeStringHelper(tableInsert.getMutatingTable().getTableName()));
        mqlAstState.attach(AttachmentKeys.collectionName(), tableInsert.getMutatingTable().getTableName());
        registerAffectedTable(tableInsert.getMutatingTable().getTableName());
    }

    @Override
    protected void visitInsertStatementOnly(final InsertSelectStatement statement) {
        getClauseStack().push(Clause.INSERT);
        appendMql("{ ");

        registerAffectedTable(statement.getTargetTable());

        getClauseStack().push(Clause.VALUES);

        if (statement.getSourceSelectStatement() != null) {
            // TODO implement using aggregate command
        } else {
            appendMql("insert: ");
            appendMql(writeStringHelper(statement.getTargetTable().getTableExpression()));
            appendMql(", documents: [");
            List<ColumnReference> targetColumnReferences = statement.getTargetColumns();

            if (targetColumnReferences != null) {
                List<Values> targetColumnValuesList = statement.getValuesList();
                for (int valuesIndex = 0; valuesIndex < statement.getValuesList().size(); valuesIndex++) {
                    appendMql(valuesIndex == 0 ? " {" : ", {");
                    for (int columnIndex = 0; columnIndex < targetColumnReferences.size(); columnIndex++) {
                        if (columnIndex == 0) {
                            appendMql(' ');
                        } else {
                            appendMql(", ");
                        }
                        appendMql(targetColumnReferences.get(columnIndex).getColumnExpression());
                        appendMql(": ");
                        targetColumnValuesList.get(valuesIndex).getExpressions().get(columnIndex).accept(this);
                    }
                    appendMql(" }");
                }
            }
            appendMql(" ]");
        }
        getClauseStack().pop();

        appendMql(" }");
        getClauseStack().pop();
    }

    @Override
    public void visitStandardTableDelete(final TableDeleteStandard tableDelete) {
        getClauseStack().push(Clause.DELETE);
        try {
            appendMql("{ delete: ");
            appendMql(writeStringHelper(tableDelete.getMutatingTable().getTableName()));
            registerAffectedTable(tableDelete.getMutatingTable().getTableName());

            getClauseStack().push(Clause.WHERE);
            try {
                appendMql(", deletes: [");
                List<AstFilter> filters = new ArrayList<>();
                tableDelete.forEachKeyBinding((columnPosition, columnValueBinding) -> {
                    if (columnPosition == 0) {
                        appendMql(' ');
                    } else {
                        appendMql(", ");
                    }
                    appendMql(" { q: { ");
                    appendMql(columnValueBinding.getColumnReference().getColumnExpression());
                    String fieldName = columnValueBinding.getColumnReference().getColumnExpression();
                    appendMql(": { $eq: ");
                    AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                            columnValueBinding.getValueExpression().accept(this));
                    appendMql(" } }, limit: 0 }");
                    filters.add(new AstFieldOperationFilter(new AstFilterField(fieldName),
                            new AstComparisonFilterOperation(AstComparisonFilterOperator.EQ, value)));
                });

                if (tableDelete.getNumberOfOptimisticLockBindings() > 0) {
                    // TODO: untested path
                    appendMql(", ");

                    tableDelete.forEachOptimisticLockBinding((columnPosition, columnValueBinding) -> {
                        if (columnPosition == 0) {
                            appendMql(' ');
                        } else {
                            appendMql(", ");
                        }
                        appendMql(" { q: { ");
                        appendMql(columnValueBinding.getColumnReference().getColumnExpression());
                        appendMql(": { $eq: ");
                        columnValueBinding.getValueExpression().accept(this);
                        appendMql(" } }, limit: 0 }");
                    });
                }

                if (tableDelete.getWhereFragment() != null) {
                    // TODO: untested path
                    appendMql(", { q: ");
                    appendMql(tableDelete.getWhereFragment());
                    appendMql(" }");
                }
                root = new DeleteCommand(tableDelete.getMutatingTable().getTableName(), new AstAndFilter(filters));
            } finally {
                appendMql(" ]");
                if (tableDelete.getMutationComment() != null) {
                    appendMql(", comment: ");
                    appendMql(writeStringHelper(tableDelete.getMutationComment()));
                }
                getClauseStack().pop();
            }
        } finally {
            appendMql(" }");
            getClauseStack().pop();
        }
    }

    @Override
    protected void visitDeleteStatementOnly(final DeleteStatement statement) {
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
        appendMql(", deletes: [ { q: ");
        AstFilter filter;
        if (statement.getRestriction() != null) {
            filter = mqlAstState.expect(AttachmentKeys.filter(), () -> visitWhereClause(statement.getRestriction()));
        } else {
            appendMql("{ }");
            filter = new AstMatchesEverythingFilter();
        }
        appendMql(", limit: 0 } ] }");
        root = new DeleteCommand(statement.getTargetTable().getTableExpression(), filter);
    }

    @Override
    protected void renderDeleteClause(final DeleteStatement statement) {
        appendMql("{ delete: ");
        try {
            getClauseStack().push(Clause.DELETE);
            renderDmlTargetTableExpression(statement.getTargetTable());
        } finally {
            getClauseStack().pop();
        }
    }

    @Override
    public void visitStandardTableUpdate(final TableUpdateStandard tableUpdate) {
        getClauseStack().push(Clause.UPDATE);
        try {
            visitTableUpdate(tableUpdate);
        } finally {
            getClauseStack().pop();
        }
    }

    private void visitTableUpdate(final RestrictedTableMutation<? extends MutationOperation> tableUpdate) {
        appendMql("{ update: ");
        appendMql(writeStringHelper(tableUpdate.getMutatingTable().getTableName()));
        registerAffectedTable(tableUpdate.getMutatingTable().getTableName());

        appendMql(", updates: [ {");
        getClauseStack().push(Clause.WHERE);
        try {
            appendMql(" q:");
            int predicates = tableUpdate.getNumberOfKeyBindings() + tableUpdate.getNumberOfOptimisticLockBindings();
            boolean hasWhereFragment =
                    tableUpdate instanceof TableUpdateStandard tableUpdateStandard && tableUpdateStandard.getWhereFragment() != null;
            if (hasWhereFragment) {
                predicates++;
            }
            if (predicates == 0) {
                appendMql("{ }");
            } else {
                if (predicates > 1) {
                    appendMql(" { $and: [");
                }
                tableUpdate.forEachKeyBinding((position, columnValueBinding) -> {
                    if (position == 0) {
                        appendMql(' ');
                    } else {
                        appendMql(", ");
                    }
                    appendMql("{ ");
                    appendMql(columnValueBinding.getColumnReference().getColumnExpression());
                    appendMql(": { $eq: ");
                    columnValueBinding.getValueExpression().accept(this);
                    appendMql(" } }");
                });

                if (tableUpdate.getNumberOfOptimisticLockBindings() > 0) {
                    tableUpdate.forEachOptimisticLockBinding((position, columnValueBinding) -> {
                        appendMql(", { ");
                        appendMql(columnValueBinding.getColumnReference().getColumnExpression());
                        appendMql(": { $eq: ");
                        if (columnValueBinding.getValueExpression() == null) {
                            appendMql("null");
                        } else {
                            columnValueBinding.getValueExpression().accept(this);
                        }
                        appendMql(" } }");
                    });
                }

                if (hasWhereFragment) {
                    appendMql(", ");
                    appendMql(((TableUpdateStandard) tableUpdate).getWhereFragment());
                }
                if (predicates > 1) {
                    appendMql(" ] }");
                }
            }
        } finally {
            getClauseStack().pop();
        }
        appendMql(", u: { $set: {");
        getClauseStack().push(Clause.SET);
        try {
            tableUpdate.forEachValueBinding((columnPosition, columnValueBinding) -> {
                if (columnPosition == 0) {
                    appendMql(' ');
                } else {
                    appendMql(", ");
                }
                appendMql(columnValueBinding.getColumnReference().getColumnExpression());
                appendMql(": ");
                columnValueBinding.getValueExpression().accept(this);
            });
        } finally {
            getClauseStack().pop();
        }
        appendMql(" } }");
        appendMql(" } ] }");
    }

    @Override
    protected void visitUpdateStatementOnly(final UpdateStatement statement) {
        if (statement.getFromClause().getRoots().size() > 1) {
            throw new NotSupportedRuntimeException("update statement with multiple roots not supported");
        }
        if (statement.getFromClause().getRoots().get(0).hasRealJoins()) {
            throw new NotSupportedRuntimeException("update statement with root having real joins not supported");
        }
        appendMql("{ ");
        renderUpdateClause(statement);
        appendMql(", updates: [ ");
        visitWhereClause(statement.getRestriction());
        renderSetClause(statement.getAssignments());
        appendMql(" ] }");
    }

    @Override
    protected void renderUpdateClause(final UpdateStatement updateStatement) {
        appendMql("update: ");
        try {
            getClauseStack().push(Clause.UPDATE);
            renderDmlTargetTableExpression(updateStatement.getTargetTable());
        } finally {
            getClauseStack().pop();
        }
    }

    @Override
    protected void renderSetClause(final List<Assignment> assignments) {
        appendMql(" , u: { ");
        var separator = " ";
        try {
            getClauseStack().push(Clause.SET);
            for (Assignment assignment : assignments) {
                appendMql(separator);
                separator = ", ";
                visitSetAssignment(assignment);
            }
        } finally {
            appendMql(" }");
            getClauseStack().pop();
        }
    }
}
