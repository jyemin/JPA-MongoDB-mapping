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
import org.hibernate.omm.mongoast.AstFieldUpdate;
import org.hibernate.omm.mongoast.AstInsertCommand;
import org.hibernate.omm.mongoast.AstValue;
import org.hibernate.omm.mongoast.DeleteCommand;
import org.hibernate.omm.mongoast.UpdateCommand;
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
        String collectionName = mqlAstState.expect(AttachmentKeys.collectionName(), () -> renderIntoIntoAndTable(tableInsert));

        List<AstElement> elements = new ArrayList<>();
        tableInsert.forEachValueBinding((columnPosition, columnValueBinding) -> {
            AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                    columnValueBinding.getValueExpression().accept(this));
            elements.add(new AstElement(columnValueBinding.getColumnReference().getColumnExpression(), value));
        });

        root = new AstInsertCommand(collectionName, elements);
    }

    @Override
    protected void renderIntoIntoAndTable(final TableInsertStandard tableInsert) {
        mqlAstState.attach(AttachmentKeys.collectionName(), tableInsert.getMutatingTable().getTableName());
        registerAffectedTable(tableInsert.getMutatingTable().getTableName());
    }

    @Override
    protected void visitInsertStatementOnly(final InsertSelectStatement statement) {
        getClauseStack().push(Clause.INSERT);

        registerAffectedTable(statement.getTargetTable());

        getClauseStack().push(Clause.VALUES);

        if (statement.getSourceSelectStatement() != null) {
            // TODO implement using aggregate command
        } else {
            List<ColumnReference> targetColumnReferences = statement.getTargetColumns();

            if (targetColumnReferences != null) {
                List<Values> targetColumnValuesList = statement.getValuesList();
                for (int valuesIndex = 0; valuesIndex < statement.getValuesList().size(); valuesIndex++) {
                    for (int columnIndex = 0; columnIndex < targetColumnReferences.size(); columnIndex++) {
                        targetColumnValuesList.get(valuesIndex).getExpressions().get(columnIndex).accept(this);
                    }
                }
            }
        }
        getClauseStack().pop();
        getClauseStack().pop();
    }

    @Override
    public void visitStandardTableDelete(final TableDeleteStandard tableDelete) {
        getClauseStack().push(Clause.DELETE);
        try {
            registerAffectedTable(tableDelete.getMutatingTable().getTableName());

            getClauseStack().push(Clause.WHERE);
            try {
                List<AstFilter> filters = new ArrayList<>();
                tableDelete.forEachKeyBinding((columnPosition, columnValueBinding) -> {
                    AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                            columnValueBinding.getValueExpression().accept(this));
                    filters.add(new AstFieldOperationFilter(
                            new AstFilterField(columnValueBinding.getColumnReference().getColumnExpression()),
                            new AstComparisonFilterOperation(AstComparisonFilterOperator.EQ, value)));
                });

//                if (tableDelete.getNumberOfOptimisticLockBindings() > 0) {
//                    // TODO: untested path
//                }
//
//                if (tableDelete.getWhereFragment() != null) {
//                    // TODO: untested path
//                }
                root = new DeleteCommand(tableDelete.getMutatingTable().getTableName(), new AstAndFilter(filters));
            } finally {
                getClauseStack().pop();
            }
        } finally {
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
        AstFilter filter = statement.getRestriction() != null
                ? mqlAstState.expect(AttachmentKeys.filter(), () -> visitWhereClause(statement.getRestriction()))
                : new AstMatchesEverythingFilter();
        root = new DeleteCommand(statement.getTargetTable().getTableExpression(), filter);
    }

    @Override
    protected void renderDeleteClause(final DeleteStatement statement) {
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
        registerAffectedTable(tableUpdate.getMutatingTable().getTableName());

        getClauseStack().push(Clause.WHERE);
        List<AstFilter> filters = new ArrayList<>();
        List<AstFieldUpdate> updates = new ArrayList<>();
        try {
            int predicates = tableUpdate.getNumberOfKeyBindings() + tableUpdate.getNumberOfOptimisticLockBindings();
            boolean hasWhereFragment =
                    tableUpdate instanceof TableUpdateStandard tableUpdateStandard && tableUpdateStandard.getWhereFragment() != null;
            if (hasWhereFragment) {
                predicates++;
            }
            if (predicates == 0) {
                // TODO: untested
            } else {
                tableUpdate.forEachKeyBinding((position, columnValueBinding) -> {
                    AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                            columnValueBinding.getValueExpression().accept(this));
                    filters.add(new AstFieldOperationFilter(
                            new AstFilterField(columnValueBinding.getColumnReference().getColumnExpression()),
                            new AstComparisonFilterOperation(AstComparisonFilterOperator.EQ, value)));
                });

//                if (tableUpdate.getNumberOfOptimisticLockBindings() > 0) {
//                    // TODO: untested
//                }
//
//                if (hasWhereFragment) {
//                    // TODO: untested
//                }
//                if (predicates > 1) {
//                    // TODO: untested
//                }
            }
        } finally {
            getClauseStack().pop();
        }
        getClauseStack().push(Clause.SET);
        try {
            tableUpdate.forEachValueBinding((columnPosition, columnValueBinding) -> {
                AstValue value = mqlAstState.expect(AttachmentKeys.fieldValue(), () ->
                        columnValueBinding.getValueExpression().accept(this));
                updates.add(new AstFieldUpdate(columnValueBinding.getColumnReference().getColumnExpression(), value));
            });
        } finally {
            getClauseStack().pop();
        }
        root = new UpdateCommand(tableUpdate.getMutatingTable().getTableName(), new AstAndFilter(filters),
                updates);
    }

    @Override
    protected void visitUpdateStatementOnly(final UpdateStatement statement) {
        // TODO: untested
        throw new NotSupportedRuntimeException("updates not supported yet");
//        if (statement.getFromClause().getRoots().size() > 1) {
//            throw new NotSupportedRuntimeException("update statement with multiple roots not supported");
//        }
//        if (statement.getFromClause().getRoots().get(0).hasRealJoins()) {
//            throw new NotSupportedRuntimeException("update statement with root having real joins not supported");
//        }
//        renderUpdateClause(statement);
//        visitWhereClause(statement.getRestriction());
//        renderSetClause(statement.getAssignments());
    }

    @Override
    protected void renderUpdateClause(final UpdateStatement updateStatement) {
        try {
            getClauseStack().push(Clause.UPDATE);
            renderDmlTargetTableExpression(updateStatement.getTargetTable());
        } finally {
            getClauseStack().pop();
        }
    }

    @Override
    protected void renderSetClause(final List<Assignment> assignments) {
        try {
            getClauseStack().push(Clause.SET);
            for (Assignment assignment : assignments) {
                visitSetAssignment(assignment);
            }
        } finally {
            getClauseStack().pop();
        }
    }
}
