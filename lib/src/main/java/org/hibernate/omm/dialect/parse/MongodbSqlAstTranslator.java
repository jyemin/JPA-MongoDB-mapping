package org.hibernate.omm.dialect.parse;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.internal.util.collections.StandardStack;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.exec.spi.JdbcOperation;

public class MongodbSqlAstTranslator<T extends JdbcOperation>
        implements SqlAstTranslator<T>, SqlAppender {

    private final SessionFactoryImplementor sessionFactory;
    private final StringBuilder sqlBuffer = new StringBuilder();

    private final Stack<Statement> statementStack = new StandardStack<>(Statement.class);
    private final Dialect dialect;

    private final Set<String> affectedTableNames = new HashSet<>();

    public MongodbSqlAstTranslator(SessionFactoryImplementor sessionFactory, Statement statement) {
        this.sessionFactory = sessionFactory;
        this.statementStack.push(statement);
        final JdbcServices jdbcServices = sessionFactory.getJdbcServices();
        this.dialect = jdbcServices.getDialect();
    }

    public String getSql() {
        return sqlBuffer.toString();
    }

    @Override
    public SessionFactoryImplementor getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Set<String> getAffectedTableNames() {
        return affectedTableNames;
    }

    protected Statement getStatement() {
        return statementStack.getRoot();
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
