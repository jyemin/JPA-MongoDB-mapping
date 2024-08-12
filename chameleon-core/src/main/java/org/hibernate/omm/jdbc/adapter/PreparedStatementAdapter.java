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
    default void setNull(final int parameterIndex, final int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBoolean(final int parameterIndex, final boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setByte(final int parameterIndex, final byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setShort(final int parameterIndex, final short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setInt(final int parameterIndex, final int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setLong(final int parameterIndex, final long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFloat(final int parameterIndex, final float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDouble(final int parameterIndex, final double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBigDecimal(final int parameterIndex, @Nullable final BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setString(final int parameterIndex, @Nullable final String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBytes(final int parameterIndex, @Nullable final byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(final int parameterIndex, @Nullable final Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(final int parameterIndex, @Nullable final Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(final int parameterIndex, @Nullable final Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final int parameterIndex, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setUnicodeStream(final int parameterIndex, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final int parameterIndex, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void clearParameters() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(final int parameterIndex, @Nullable final Object x) throws SimulatedSQLException {
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
    default void setCharacterStream(final int parameterIndex, @Nullable final Reader reader, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setRef(final int parameterIndex, final Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final int parameterIndex, @Nullable final Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final int parameterIndex, @Nullable final Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setArray(final int parameterIndex, final Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSetMetaData getMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(final int parameterIndex, @Nullable final Date x, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(final int parameterIndex, @Nullable final Time x, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(final int parameterIndex, @Nullable final Timestamp x, @Nullable final Calendar cal)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNull(final int parameterIndex, final int sqlType, final String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setURL(final int parameterIndex, @Nullable final URL x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ParameterMetaData getParameterMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setRowId(final int parameterIndex, final RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNString(final int parameterIndex, @Nullable final String value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(final int parameterIndex, @Nullable final Reader value, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final int parameterIndex, @Nullable final NClob value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final int parameterIndex, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final int parameterIndex, @Nullable final InputStream inputStream, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final int parameterIndex, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(final int parameterIndex, @Nullable final Object x, final int targetSqlType, final int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final int parameterIndex, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final int parameterIndex, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(final int parameterIndex, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final int parameterIndex, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final int parameterIndex, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(final int parameterIndex, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(final int parameterIndex, @Nullable final Reader value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final int parameterIndex, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final int parameterIndex, @Nullable final InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final int parameterIndex, @Nullable final Reader reader) throws SimulatedSQLException {
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
    default void setCursorName(final String name) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
