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
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface ResultSetAdapter extends ResultSet {

    @Override
    default boolean next() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void close() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean wasNull() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getString(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default byte[] getBytes(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getAsciiStream(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getUnicodeStream(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getBinaryStream(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getString(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default byte[] getBytes(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getAsciiStream(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getUnicodeStream(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getBinaryStream(final String columnLabel) throws SimulatedSQLException {
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
    default String getCursorName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSetMetaData getMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int findColumn(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getCharacterStream(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getCharacterStream(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isBeforeFirst() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isAfterLast() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isFirst() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isLast() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void beforeFirst() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void afterLast() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean first() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean last() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean absolute(final int row) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean relative(final int rows) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean previous() throws SimulatedSQLException {
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
    default int getType() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getConcurrency() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean rowUpdated() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean rowInserted() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean rowDeleted() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNull(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBoolean(final int columnIndex, final boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateByte(final int columnIndex, final byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateShort(final int columnIndex, final short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateInt(final int columnIndex, final int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateLong(final int columnIndex, final long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateFloat(final int columnIndex, final float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDouble(final int columnIndex, final double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBigDecimal(final int columnIndex, @Nullable final BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(final int columnIndex, @Nullable final String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(final int columnIndex, @Nullable final byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(final int columnIndex, @Nullable final Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(final int columnIndex, @Nullable final Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(final int columnIndex, @Nullable final Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final int columnIndex, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final int columnIndex, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final int columnIndex, @Nullable final Reader x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(final int columnIndex, @Nullable final Object x, final int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(final int columnIndex, @Nullable final Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNull(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBoolean(final String columnLabel, final boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateByte(final String columnLabel, final byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateShort(final String columnLabel, final short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateInt(final String columnLabel, final int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateLong(final String columnLabel, final long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateFloat(final String columnLabel, final float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDouble(final String columnLabel, final double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBigDecimal(final String columnLabel, @Nullable final BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(final String columnLabel, @Nullable final String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(final String columnLabel, @Nullable final byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(final String columnLabel, @Nullable final Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(final String columnLabel, @Nullable final Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(final String columnLabel, @Nullable final Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final String columnLabel, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final String columnLabel, @Nullable final InputStream x, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final String columnLabel, @Nullable final Reader reader, final int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(final String columnLabel, @Nullable final Object x, final int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(final String columnLabel, @Nullable final Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void insertRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void deleteRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void refreshRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void cancelRowUpdates() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void moveToInsertRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void moveToCurrentRow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Statement getStatement() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(final int columnIndex, final Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Ref getRef(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Blob getBlob(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Clob getClob(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Array getArray(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(final String columnLabel, final Map<String, Class<?>> map)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Ref getRef(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Blob getBlob(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Clob getClob(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Array getArray(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(final int columnIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(final String columnLabel, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(final int columnIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(final String columnLabel, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(final int columnIndex, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(final String columnLabel, @Nullable final Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default URL getURL(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default URL getURL(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(final int columnIndex, @Nullable final Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(final String columnLabel, @Nullable final Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final int columnIndex, @Nullable final Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final String columnLabel, @Nullable final Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final int columnIndex, @Nullable final Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final String columnLabel, @Nullable final Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(final int columnIndex, @Nullable final Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(final String columnLabel, @Nullable final Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default RowId getRowId(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default RowId getRowId(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(final int columnIndex, @Nullable final RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(final String columnLabel, @Nullable final RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getHoldability() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isClosed() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNString(final int columnIndex, @Nullable final String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNString(final String columnLabel, @Nullable final String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final int columnIndex, @Nullable final NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final String columnLabel, @Nullable final NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default NClob getNClob(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default NClob getNClob(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default SQLXML getSQLXML(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default SQLXML getSQLXML(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(final int columnIndex, @Nullable final SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(final String columnLabel, @Nullable final SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getNString(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getNString(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getNCharacterStream(final int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getNCharacterStream(final String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(final int columnIndex, @Nullable final Reader x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(final String columnLabel, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final int columnIndex, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final int columnIndex, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final int columnIndex, @Nullable final Reader x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final String columnLabel, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final String columnLabel, @Nullable final InputStream x, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final String columnLabel, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final int columnIndex, @Nullable final InputStream inputStream, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final String columnLabel, @Nullable final InputStream inputStream, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final int columnIndex, @Nullable final Reader reader, final long length) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final String columnLabel, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final int columnIndex, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final String columnLabel, @Nullable final Reader reader, final long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(final int columnIndex, @Nullable final Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(final String columnLabel, @Nullable final Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final int columnIndex, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final int columnIndex, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final int columnIndex, @Nullable final Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(final String columnLabel, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(final String columnLabel, @Nullable final InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(final String columnLabel, @Nullable final Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final int columnIndex, @Nullable final InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(final String columnLabel, @Nullable final InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final int columnIndex, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(final String columnLabel, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final int columnIndex, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(final String columnLabel, @Nullable final Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default <T> T getObject(final int columnIndex, final Class<T> type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default <T> T getObject(final String columnLabel, final Class<T> type) throws SimulatedSQLException {
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
