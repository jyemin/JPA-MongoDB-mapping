package org.hibernate.omm.dialect;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.spi.AbstractSqlAstTranslator;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.delete.DeleteStatement;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.insert.InsertSelectStatement;
import org.hibernate.sql.ast.tree.insert.Values;
import org.hibernate.sql.exec.spi.JdbcOperation;

import java.util.List;

public class MongodbSqlAstTranslator <T extends JdbcOperation> extends AbstractSqlAstTranslator<T> {


    protected MongodbSqlAstTranslator(SessionFactoryImplementor sessionFactory, Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    public void visitInsertStatement(InsertSelectStatement statement) {
        try {
            getStatementStack().push( statement );
            visitInsertStatementOnly( statement );
        }
        finally {
            getStatementStack().pop();
        }
    }

    protected void visitInsertStatementOnly(InsertSelectStatement statement) {
        getClauseStack().push( Clause.INSERT );

        String tableExpression = statement.getTargetTable().getTableExpression();
        appendSql("{insert: '");
        appendSql(tableExpression);
        appendSql('\'');
        appendSql(", documents: ");

        boolean firstPass = true;

        registerAffectedTable( tableExpression );

        final List<ColumnReference> targetColumnReferences = statement.getTargetColumns();
        if ( targetColumnReferences != null ) {
            for (ColumnReference targetColumnReference : targetColumnReferences) {
                if (firstPass) {
                    firstPass = false;
                } else {
                    appendSql( COMMA_SEPARATOR_CHAR );
                }
                appendSql(targetColumnReference.getColumnExpression());
                appendSql(": ?");
            }

        }

        appendSql("}");

        if ( statement.getSourceSelectStatement() != null ) {
            statement.getSourceSelectStatement().accept( this );
        }
        else {
            visitInsertValuesList( statement );
        }
        getClauseStack().pop();
    }

    private void visitInsertValuesList(InsertSelectStatement statement) {
        List<ColumnReference> targetColumnReferences = statement.getTargetColumns();
        List<Values> valuesList = statement.getValuesList();
        appendSql('[');
        final Stack<Clause> clauseStack = getClauseStack();
        try {
            clauseStack.push( Clause.VALUES );
            for ( int i = 0; i < valuesList.size(); i++ ) {
                if ( i != 0 ) {
                    appendSql( COMMA_SEPARATOR_CHAR );
                }
                appendSql( " {" );
                final List<Expression> expressions = valuesList.get( i ).getExpressions();
                for ( int j = 0; j < expressions.size(); j++ ) {
                    if ( j != 0 ) {
                        appendSql( COMMA_SEPARATOR_CHAR );
                    }
                    appendSql(targetColumnReferences.get(j).getColumnExpression());
                    appendSql(": ");
                    expressions.get( j ).accept( this );
                }
                appendSql( '}' );
            }
        }
        finally {
            appendSql(']');
            clauseStack.pop();
        }
    }

    @Override
    public void visitDeleteStatement(DeleteStatement statement) {
        try {
            getStatementStack().push( statement );
            visitDeleteStatementOnly( statement );
        }
        finally {
            getStatementStack().pop();
        }
    }

    @Override
    protected void visitDeleteStatementOnly(DeleteStatement statement) {
        renderDeleteClause( statement );
        String oldSql = getSql();
        visitWhereClause( determineWhereClauseRestrictionWithJoinEmulation( statement ) );
        String newSql = getSql();
        if ( newSql.startsWith( " where ", oldSql.length() ) ) {
            appendSql(", deletes: [ { q: ");
            appendSql(newSql.substring(oldSql.length() + " where ".length()));
            appendSql(" } ]");
        }
        appendSql("}");
    }

    @Override
    protected void renderDeleteClause(DeleteStatement statement) {
        appendSql( "{ delete: '" );
        final Stack<Clause> clauseStack = getClauseStack();
        try {
            clauseStack.push( Clause.DELETE );
            renderDmlTargetTableExpression( statement.getTargetTable() );
            appendSql('\'');
        }
        finally {
            clauseStack.pop();
        }
    }

}
