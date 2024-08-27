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

package org.hibernate.omm.type.struct;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.dialect.Dialect;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.AggregateJdbcType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MongoSQLStructCastingJdbcType extends AbstractMongoSQLStructJdbcType {
    public static final MongoSQLStructCastingJdbcType INSTANCE = new MongoSQLStructCastingJdbcType();
    public MongoSQLStructCastingJdbcType() {
        this( null, null, null );
    }

    private MongoSQLStructCastingJdbcType(
            EmbeddableMappingType embeddableMappingType,
            String typeName,
            int[] orderMapping) {
        super( embeddableMappingType, typeName, orderMapping );
    }

    @Override
    public AggregateJdbcType resolveAggregateJdbcType(
            EmbeddableMappingType mappingType,
            String sqlType,
            RuntimeModelCreationContext creationContext) {
        return new MongoSQLStructCastingJdbcType(
                mappingType,
                sqlType,
                creationContext.getBootModel()
                        .getDatabase()
                        .getDefaultNamespace()
                        .locateUserDefinedType( Identifier.toIdentifier( sqlType ) )
                        .getOrderMapping()
        );
    }

    @Override
    public void appendWriteExpression(
            String writeExpression,
            SqlAppender appender,
            Dialect dialect) {
        appender.append( "cast(" );
        appender.append( writeExpression );
        appender.append( " as " );
        appender.append( getStructTypeName() );
        appender.append( ')' );
    }

    @Override
    public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
        return new BasicBinder<>( javaType, this ) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
                    throws SQLException {
                final String stringValue = ( (MongoSQLStructCastingJdbcType) getJdbcType() ).toString(
                        value,
                        getJavaType(),
                        options
                );
                st.setString( index, stringValue );
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                final String stringValue = ( (MongoSQLStructCastingJdbcType) getJdbcType() ).toString(
                        value,
                        getJavaType(),
                        options
                );
                st.setString( name, stringValue );
            }

            @Override
            public Object getBindValue(X value, WrapperOptions options) throws SQLException {
                return ( (MongoSQLStructCastingJdbcType) getJdbcType() ).getBindValue( value, options );
            }
        };
    }

}
