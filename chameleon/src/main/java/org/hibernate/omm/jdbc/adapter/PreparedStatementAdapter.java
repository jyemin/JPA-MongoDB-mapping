package org.hibernate.omm.jdbc.adapter;

import com.mongodb.lang.Nullable;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface PreparedStatementAdapter extends StatementAdapter, PreparedStatement {

    @Override
    default ResultSet executeQuery() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int executeUpdate() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNull(int parameterIndex, int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setShort(int parameterIndex, short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setInt(int parameterIndex, int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setLong(int parameterIndex, long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBigDecimal(int parameterIndex, @Nullable BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setString(int parameterIndex, @Nullable String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBytes(int parameterIndex, @Nullable byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(int parameterIndex, @Nullable Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(int parameterIndex, @Nullable Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(int parameterIndex, @Nullable Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(int parameterIndex, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setUnicodeStream(int parameterIndex, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(int parameterIndex, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearParameters() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(int parameterIndex, @Nullable Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean execute() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void addBatch() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(int parameterIndex, @Nullable Reader reader, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setRef(int parameterIndex, Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(int parameterIndex, @Nullable Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(int parameterIndex, @Nullable Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setArray(int parameterIndex, Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSetMetaData getMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(int parameterIndex, @Nullable Date x, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(int parameterIndex, @Nullable Time x, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(int parameterIndex, @Nullable Timestamp x, @Nullable Calendar cal)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNull(int parameterIndex, int sqlType, String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setURL(int parameterIndex, @Nullable URL x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ParameterMetaData getParameterMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setRowId(int parameterIndex, RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNString(int parameterIndex, @Nullable String value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(int parameterIndex, @Nullable Reader value, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(int parameterIndex, @Nullable NClob value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(int parameterIndex, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(int parameterIndex, @Nullable InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(int parameterIndex, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(int parameterIndex, @Nullable Object x, int targetSqlType, int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(int parameterIndex, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(int parameterIndex, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(int parameterIndex, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(int parameterIndex, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(int parameterIndex, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(int parameterIndex, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(int parameterIndex, @Nullable Reader value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(int parameterIndex, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(int parameterIndex, @Nullable InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(int parameterIndex, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxFieldSize() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setMaxFieldSize(int max) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxRows() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setMaxRows(int max) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setEscapeProcessing(boolean enable) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getQueryTimeout() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setQueryTimeout(int seconds) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCursorName(String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
