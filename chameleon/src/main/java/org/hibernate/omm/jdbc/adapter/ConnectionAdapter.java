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
    default PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String nativeSQL(String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAutoCommit(boolean autoCommit) throws SimulatedSQLException {
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
    default void setReadOnly(boolean readOnly) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isReadOnly() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCatalog(String catalog) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getCatalog() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTransactionIsolation(int level) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getTransactionIsolation() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    @SuppressWarnings("nullness")
    default SQLWarning getWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearWarnings() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Map<String, Class<?>> getTypeMap() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTypeMap(Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setHoldability(int holdability) throws SimulatedSQLException {
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
    default Savepoint setSavepoint(String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void rollback(Savepoint savepoint) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void releaseSavepoint(Savepoint savepoint) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Statement createStatement(
            int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default PreparedStatement prepareStatement(String sql, String[] columnNames)
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
    default boolean isValid(int timeout) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new NotSupportedSqlClientInfoSQLException();
    }

    @Override
    default void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new NotSupportedSqlClientInfoSQLException();
    }

    @Override
    default String getClientInfo(String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Properties getClientInfo() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array createArrayOf(String typeName, Object[] elements) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Struct createStruct(String typeName, Object[] attributes) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setSchema(String schema) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSchema() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void abort(Executor executor) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNetworkTimeout(Executor executor, int milliseconds) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getNetworkTimeout() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T unwrap(Class<T> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isWrapperFor(Class<?> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
