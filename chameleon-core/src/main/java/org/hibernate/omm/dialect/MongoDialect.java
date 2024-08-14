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
package org.hibernate.omm.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.omm.array.function.MongoArrayContainsFunction;
import org.hibernate.omm.array.function.MongoArrayIncludesFunction;
import org.hibernate.omm.ast.MQLAstTranslatorFactory;
import org.hibernate.omm.type.ObjectIdJavaType;
import org.hibernate.omm.type.ObjectIdJdbcType;
import org.hibernate.omm.util.StringUtil;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.spi.TypeConfiguration;

import static org.hibernate.type.SqlTypes.ARRAY;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class MongoDialect extends Dialect {

    public static final int MINIMUM_MONGODB_MAJOR_VERSION_SUPPORTED = 3;

    private static final DatabaseVersion MINIMUM_VERSION = DatabaseVersion.make(MINIMUM_MONGODB_MAJOR_VERSION_SUPPORTED);

    public MongoDialect() {
        this(MINIMUM_VERSION);
    }

    public MongoDialect(final DatabaseVersion version) {
        super(version);
    }

    public MongoDialect(final DialectResolutionInfo dialectResolutionInfo) {
        super(dialectResolutionInfo);
    }

    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
        return new MQLAstTranslatorFactory();
    }

    @Override
    public void appendLiteral(final SqlAppender appender, final String literal) {
        appender.appendSql(StringUtil.writeStringHelper(literal));
    }

    @Override
    public boolean supportsNullPrecedence() {
        return false;
    }

    @Override
    public boolean supportsStandardArrays() {
        return true;
    }

    @Override
    public int getPreferredSqlTypeCodeForArray() {
        return ARRAY;
    }

    @Override
    public void contribute(final TypeContributions typeContributions, final ServiceRegistry serviceRegistry) {
        contributeTypes(typeContributions, serviceRegistry);
        TypeConfiguration typeConfiguration = typeContributions.getTypeConfiguration();
        typeConfiguration.getJavaTypeRegistry().addDescriptor(ObjectIdJavaType.getInstance());
        typeConfiguration.getJdbcTypeRegistry().addDescriptor(ObjectIdJdbcType.getInstance());
    }

    @Override
    public void initializeFunctionRegistry(final FunctionContributions functionContributions) {
        var functionRegistry = functionContributions.getFunctionRegistry();
        var typeConfiguration = functionContributions.getTypeConfiguration();
        functionRegistry.register("array_contains", new MongoArrayContainsFunction(typeConfiguration));
        functionRegistry.register("array_includes", new MongoArrayIncludesFunction(typeConfiguration));
    }

}
