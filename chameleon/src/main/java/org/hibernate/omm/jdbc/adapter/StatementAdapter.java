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
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface StatementAdapter extends Statement {

    @Override
    default ResultSet executeQuery(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int executeUpdate(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void close() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxFieldSize() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setMaxFieldSize(final int max) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxRows() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setMaxRows(final int max) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setEscapeProcessing(final boolean enable) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getQueryTimeout() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setQueryTimeout(final int seconds) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void cancel() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLWarning getWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCursorName(final String name) throws SimulatedSQLException {
    }

    @Override
    default boolean execute(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default ResultSet getResultSet() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getUpdateCount() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getMoreResults() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFetchDirection(final int direction) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getFetchDirection() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFetchSize(final int rows) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getFetchSize() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getResultSetConcurrency() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getResultSetType() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void addBatch(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearBatch() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int[] executeBatch() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Connection getConnection() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getMoreResults(final int current) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getGeneratedKeys() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int executeUpdate(final String sql, final int autoGeneratedKeys) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int executeUpdate(final String sql, final int[] columnIndexes) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int executeUpdate(final String sql, final String[] columnNames) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean execute(final String sql, final int autoGeneratedKeys) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean execute(final String sql, final int[] columnIndexes) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean execute(final String sql, final String[] columnNames) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getResultSetHoldability() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isClosed() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setPoolable(final boolean poolable) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isPoolable() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void closeOnCompletion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isCloseOnCompletion() throws SimulatedSQLException {
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
