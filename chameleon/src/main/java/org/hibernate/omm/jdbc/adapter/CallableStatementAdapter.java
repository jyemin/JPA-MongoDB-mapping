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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface CallableStatementAdapter extends CallableStatement {

    @Override
    default void registerOutParameter(final int parameterIndex, final int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void registerOutParameter(final int parameterIndex, final int sqlType, final int scale)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean wasNull() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getString(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte[] getBytes(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default BigDecimal getBigDecimal(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(final int parameterIndex, final Map<String, Class<?>> map)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Ref getRef(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Blob getBlob(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Clob getClob(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array getArray(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(final int parameterIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(final int parameterIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(final int parameterIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void registerOutParameter(final String parameterName, final int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void registerOutParameter(final String parameterName, final int sqlType, final int scale)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void registerOutParameter(final String parameterName, final int sqlType, final String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default URL getURL(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setURL(final String parameterName, @Nullable final URL val) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNull(final String parameterName, final int sqlType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBoolean(final String parameterName, final boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setByte(final String parameterName, final byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setShort(final String parameterName, final short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setInt(final String parameterName, final int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setLong(final String parameterName, final long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFloat(final String parameterName, final float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDouble(final String parameterName, final double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBigDecimal(final String parameterName, @Nullable final BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setString(final String parameterName, @Nullable final String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBytes(final String parameterName, @Nullable final byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(final String parameterName, @Nullable final Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(final String parameterName, @Nullable final Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(final String parameterName, @Nullable final Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final String parameterName, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final String parameterName, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(final String parameterName, @Nullable final Object x, final int targetSqlType, final int scale)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(final String parameterName, @Nullable final Object x, final int targetSqlType)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setObject(final String parameterName, @Nullable final Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(final String parameterName, @Nullable final Reader reader, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setDate(final String parameterName, @Nullable final Date x, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTime(final String parameterName, @Nullable final Time x, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setTimestamp(final String parameterName, @Nullable final Timestamp x, @Nullable final Calendar cal)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNull(final String parameterName, final int sqlType, final String typeName)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getString(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte[] getBytes(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default BigDecimal getBigDecimal(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(final String parameterName, final Map<String, Class<?>> map)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Ref getRef(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Blob getBlob(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Clob getClob(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array getArray(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(final String parameterName, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(final String parameterName, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(final String parameterName, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default URL getURL(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default RowId getRowId(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default RowId getRowId(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setRowId(final String parameterName, @Nullable final RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNString(final String parameterName, @Nullable final String value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(final String parameterName, @Nullable final Reader value, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final String parameterName, @Nullable final NClob value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final String parameterName, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final String parameterName, @Nullable final InputStream inputStream, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final String parameterName, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default NClob getNClob(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default NClob getNClob(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setSQLXML(final String parameterName, @Nullable final SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLXML getSQLXML(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLXML getSQLXML(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getNString(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getNString(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getNCharacterStream(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getNCharacterStream(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getCharacterStream(final int parameterIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getCharacterStream(final String parameterName) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final String parameterName, @Nullable final Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final String parameterName, @Nullable final Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final String parameterName, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final String parameterName, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(final String parameterName, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setAsciiStream(final String parameterName, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBinaryStream(final String parameterName, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setCharacterStream(final String parameterName, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNCharacterStream(final String parameterName, @Nullable final Reader value) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setClob(final String parameterName, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setBlob(final String parameterName, @Nullable final InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setNClob(final String parameterName, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T getObject(final int parameterIndex, final Class<T> type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T getObject(final String parameterName, final Class<T> type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

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
    default void setObject(final int parameterIndex, @Nullable final Object x, final int targetSqlType)
            throws SimulatedSQLException {
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
    default void setClob(final int parameterIndex, @Nullable final Reader reader, final long length) throws SimulatedSQLException {
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
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean execute(final String sql) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
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
