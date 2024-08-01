package org.hibernate.omm.ast;

import org.hibernate.LockMode;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.exec.spi.JdbcOperation;

import static org.hibernate.omm.util.StringUtil.writeStringHelper;

public class AbstractMongoQuerySqlTranslator<T extends JdbcOperation> extends AbstractMongoSqlAstTranslator<T> {

    public AbstractMongoQuerySqlTranslator(final SessionFactoryImplementor sessionFactory, final Statement statement) {
        super(sessionFactory, statement);
    }

    @Override
    protected void renderDmlTargetTableExpression(NamedTableReference tableReference) {
        appendSql(writeStringHelper(tableReference.getTableExpression()));
        registerAffectedTable(tableReference);
    }

    @Override
    protected boolean renderNamedTableReference(NamedTableReference tableReference, LockMode lockMode) {
        appendSql(writeStringHelper(tableReference.getTableExpression()));
        registerAffectedTable(tableReference);
        return false;
    }

    @Override
    public void visitNamedTableReference(NamedTableReference tableReference) {
        appendSql(writeStringHelper(tableReference.getTableExpression()));
    }

    @Override
    protected void visitWhereClause(Predicate whereClauseRestrictions) {
        final Predicate additionalWherePredicate = this.additionalWherePredicate;
        final boolean existsWhereClauseRestrictions = whereClauseRestrictions != null && !whereClauseRestrictions.isEmpty();
        final boolean existsAdditionalWherePredicate = additionalWherePredicate != null;
        final boolean requiredAndPredicate = existsWhereClauseRestrictions && existsAdditionalWherePredicate;
        if (existsWhereClauseRestrictions || existsAdditionalWherePredicate) {
            if (requiredAndPredicate) {
                appendSql("{ $and: [ ");
            }
            getClauseStack().push(Clause.WHERE);
            try {
                if (existsWhereClauseRestrictions) {
                    whereClauseRestrictions.accept(this);
                }
                if (requiredAndPredicate) {
                    appendSql(", ");
                }
                if (additionalWherePredicate != null) {
                    this.additionalWherePredicate = null;
                    additionalWherePredicate.accept(this);
                }
                if (requiredAndPredicate) {
                    appendSql(" ] }");
                }
            } finally {
                getClauseStack().pop();
            }
        } else {
            appendSql("{ }");
        }
    }

}
