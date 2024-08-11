/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.ast;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.tree.MutationStatement;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import org.hibernate.sql.exec.spi.JdbcOperationQueryMutation;
import org.hibernate.sql.exec.spi.JdbcOperationQuerySelect;
import org.hibernate.sql.model.ast.TableMutation;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;

public class MongoSqlAstTranslatorFactory implements SqlAstTranslatorFactory {

    @Override
    public SqlAstTranslator<JdbcOperationQuerySelect> buildSelectTranslator(final SessionFactoryImplementor sessionFactory, final SelectStatement statement) {
        return new MongoSelectQueryAstTranslator(sessionFactory, statement);
    }

    @Override
    public SqlAstTranslator<? extends JdbcOperationQueryMutation> buildMutationTranslator(final SessionFactoryImplementor sessionFactory, final MutationStatement statement) {
        return new MongoMutationQuerySqlAstTranslator<>(sessionFactory, statement);
    }

    @Override
    public <O extends JdbcMutationOperation> SqlAstTranslator<O> buildModelMutationTranslator(final TableMutation<O> mutation, final SessionFactoryImplementor sessionFactory) {
        return new MongoMutationQuerySqlAstTranslator<>(sessionFactory, mutation);
    }
}
