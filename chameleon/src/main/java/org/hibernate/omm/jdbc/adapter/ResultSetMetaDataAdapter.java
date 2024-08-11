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
package org.hibernate.omm.jdbc.adapter;

import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.ResultSetMetaData;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface ResultSetMetaDataAdapter extends ResultSetMetaData {

    @Override
    default int getColumnCount() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isAutoIncrement(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isCaseSensitive(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isSearchable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isCurrency(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int isNullable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isSigned(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getColumnDisplaySize(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getColumnLabel(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getColumnName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSchemaName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getPrecision(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getScale(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getTableName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getCatalogName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getColumnType(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getColumnTypeName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isReadOnly(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isWritable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isDefinitelyWritable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getColumnClassName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T unwrap(final Class<T> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isWrapperFor(final Class<?> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
