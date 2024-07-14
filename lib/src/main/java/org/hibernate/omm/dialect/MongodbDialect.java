package org.hibernate.omm.dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.omm.dialect.parse.MongodbSqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.StandardSqlAstTranslatorFactory;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.exec.spi.JdbcOperation;

public class MongodbDialect extends Dialect {

    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {

        return new StandardSqlAstTranslatorFactory() {
            @Override
            protected <T extends JdbcOperation> SqlAstTranslator<T> buildTranslator(
                    SessionFactoryImplementor sessionFactory, Statement statement) {
                return new MongodbSqlAstTranslator<>(sessionFactory, statement);
            }
        };
    }
}
