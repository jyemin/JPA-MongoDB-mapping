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

package org.hibernate.omm.type.array;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.BasicPluralJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.AggregateJdbcType;
import org.hibernate.type.descriptor.jdbc.ArrayJdbcType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MongoSQLArrayJdbcType extends ArrayJdbcType {

    public MongoSQLArrayJdbcType(JdbcType elementJdbcType) {
        super( elementJdbcType );
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaType<X> javaTypeDescriptor) {
        //noinspection unchecked
        final ValueBinder<Object> elementBinder = getElementJdbcType().getBinder( ( (BasicPluralJavaType<Object>) javaTypeDescriptor ).getElementJavaType() );
        return new BasicBinder<>( javaTypeDescriptor, this ) {

            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setArray( index, getArray( value, options ) );
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                throw new NotSupportedSQLException();
            }

            @Override
            public Object getBindValue(X value, WrapperOptions options) throws SQLException {
                return ( (MongoSQLArrayJdbcType) getJdbcType() ).getArray( this, elementBinder, value, options );
            }

            private java.sql.Array getArray(X value, WrapperOptions options) throws SQLException {
                final MongoSQLArrayJdbcType arrayJdbcType = (MongoSQLArrayJdbcType) getJdbcType();
                final Object[] objects;

                final JdbcType elementJdbcType = arrayJdbcType.getElementJdbcType();
                if ( elementJdbcType instanceof AggregateJdbcType) {
                    // The PostgreSQL JDBC driver does not support arrays of structs, which contain byte[]
                    final AggregateJdbcType aggregateJdbcType = (AggregateJdbcType) elementJdbcType;
                    final Object[] domainObjects = getJavaType().unwrap(
                            value,
                            Object[].class,
                            options
                    );
                    objects = new Object[domainObjects.length];
                    for ( int i = 0; i < domainObjects.length; i++ ) {
                        objects[i] = aggregateJdbcType.createJdbcValue( domainObjects[i], options );
                    }
                }
                else {
                    objects = arrayJdbcType.getArray( this, elementBinder, value, options );
                }

                final SharedSessionContractImplementor session = options.getSession();
                final String typeName = arrayJdbcType.getElementTypeName( getJavaType(), session );
                return session.getJdbcCoordinator().getLogicalConnection().getPhysicalConnection()
                        .createArrayOf( typeName, objects );
            }
        };
    }

    @Override
    public String toString() {
        return "MongoSQLArrayTypeDescriptor(" + getElementJdbcType().toString() + ")";
    }

}
