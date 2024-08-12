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

import com.mongodb.lang.Nullable;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.NotSupportedSqlClientInfoSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface ConnectionAdapter extends Connection {

    @Override
    default Statement createStatement() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String nativeSQL(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAutoCommit(final boolean autoCommit) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getAutoCommit() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void commit() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void rollback() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void close() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isClosed() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default DatabaseMetaData getMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setReadOnly(final boolean readOnly) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isReadOnly() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCatalog(final String catalog) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getCatalog() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTransactionIsolation(final int level) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getTransactionIsolation() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default SQLWarning getWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Statement createStatement(final int resultSetType, final int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Map<String, Class<?>> getTypeMap() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTypeMap(final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setHoldability(final int holdability) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getHoldability() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Savepoint setSavepoint() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Savepoint setSavepoint(final String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void rollback(final Savepoint savepoint) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void releaseSavepoint(final Savepoint savepoint) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Statement createStatement(
            final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(
            final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(
            final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(final String sql, final int[] columnIndexes)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(final String sql, final String[] columnNames)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Clob createClob() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Blob createBlob() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default NClob createNClob() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLXML createSQLXML() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isValid(final int timeout) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        throw new NotSupportedSqlClientInfoSQLException();
    }

    @Override
    default void setClientInfo(final Properties properties) throws SQLClientInfoException {
        throw new NotSupportedSqlClientInfoSQLException();
    }

    @Override
    default String getClientInfo(final String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Properties getClientInfo() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array createArrayOf(final String typeName, final Object[] elements) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Struct createStruct(final String typeName, final Object[] attributes) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setSchema(final String schema) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSchema() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void abort(final Executor executor) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNetworkTimeout(final Executor executor, final int milliseconds) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getNetworkTimeout() throws SimulatedSQLException {
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
