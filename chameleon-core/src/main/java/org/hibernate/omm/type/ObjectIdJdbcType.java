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
package org.hibernate.omm.type;

import com.mongodb.lang.Nullable;
import org.bson.BsonObjectId;
import org.bson.assertions.Assertions;
import org.bson.types.ObjectId;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterCharacterData;
import org.hibernate.type.spi.TypeConfiguration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class ObjectIdJdbcType implements JdbcType {

    private static final ObjectIdJdbcType INSTANCE = new ObjectIdJdbcType();

    public static ObjectIdJdbcType getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> JavaType<T> getJdbcRecommendedJavaTypeMapping(
            @Nullable final Integer length,
            @Nullable final Integer scale,
            final TypeConfiguration typeConfiguration) {
        Assertions.notNull("typeConfiguration", typeConfiguration);
        return typeConfiguration.getJavaTypeRegistry().getDescriptor(ObjectId.class);
    }

    @Override
    public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(final JavaType<T> javaType) {
        Assertions.notNull("javaType", javaType);
        return new JdbcLiteralFormatterCharacterData<>(javaType, false);
    }

    @Override
    public Class<?> getPreferredJavaTypeClass(@Nullable final WrapperOptions options) {
        return ObjectId.class;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
        Assertions.notNull("javaType", javaType);
        return new BasicBinder<>(javaType, this) {

            @Override
            protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
                st.setObject(index, value, MongoSqlType.OBJECT_ID);
            }

            @Override
            protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
                    throws SQLException {
                st.setObject(name, value, MongoSqlType.OBJECT_ID);
            }
        };
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
        Assertions.notNull("javaType", javaType);
        return new BasicExtractor<>(javaType, this) {

            @Override
            @Nullable
            protected X doExtract(final ResultSet rs, final int paramIndex, final WrapperOptions options) throws SQLException {
                var obj = rs.getObject(paramIndex, BsonObjectId.class);
                var value = obj == null ? null : obj.getValue();
                return javaType.getJavaTypeClass().cast(value);
            }

            @Override
            @Nullable
            protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options) throws SQLException {
                var obj = statement.getObject(index, BsonObjectId.class);
                var value = obj == null ? null : obj.getValue();
                return javaType.getJavaTypeClass().cast(value);
            }

            @Override
            @Nullable
            protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options) throws SQLException {
                var value = statement.getObject(name, ObjectId.class);
                return javaType.getJavaTypeClass().cast(value);
            }
        };
    }

    @Override
    public String getFriendlyName() {
        return "OBJECT_ID";
    }

    @Override
    public int getJdbcTypeCode() {
        return SqlTypes.JAVA_OBJECT;
    }

    @Override
    public String toString() {
        return "ObjectIdTypeDescriptor(" + getFriendlyName() + ")";
    }
}
