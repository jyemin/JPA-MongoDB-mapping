package org.hibernate.omm.jdbc.adapter;

import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.ResultSetMetaData;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class ResultSetMetaDataAdapter implements ResultSetMetaData {
    @Override
    public int getColumnCount() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isAutoIncrement(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isCaseSensitive(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isSearchable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isCurrency(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public int isNullable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isSigned(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public int getColumnDisplaySize(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getColumnLabel(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getColumnName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getSchemaName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public int getPrecision(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public int getScale(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getTableName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getCatalogName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public int getColumnType(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getColumnTypeName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isReadOnly(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isWritable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isDefinitelyWritable(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public String getColumnClassName(final int column) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
