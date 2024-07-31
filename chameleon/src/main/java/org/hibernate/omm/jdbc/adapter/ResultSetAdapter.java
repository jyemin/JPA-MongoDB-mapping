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
    default String getString(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(int columnIndex, int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default byte[] getBytes(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getAsciiStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getUnicodeStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getBinaryStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getString(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean getBoolean(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte getByte(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default short getShort(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getInt(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default long getLong(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default float getFloat(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default double getDouble(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    @SuppressWarnings("nullness")
    default BigDecimal getBigDecimal(String columnLabel, int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default byte[] getBytes(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getAsciiStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default InputStream getUnicodeStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getBinaryStream(String columnLabel) throws SimulatedSQLException {
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
    default Object getObject(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int findColumn(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getCharacterStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getCharacterStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default BigDecimal getBigDecimal(String columnLabel) throws SimulatedSQLException {
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
    default boolean absolute(int row) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean relative(int rows) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean previous() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFetchDirection(int direction) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getFetchDirection() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void setFetchSize(int rows) throws SimulatedSQLException {
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
    default void updateNull(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBoolean(int columnIndex, boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateByte(int columnIndex, byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateShort(int columnIndex, short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateInt(int columnIndex, int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateLong(int columnIndex, long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateFloat(int columnIndex, float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDouble(int columnIndex, double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBigDecimal(int columnIndex, @Nullable BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(int columnIndex, @Nullable String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(int columnIndex, @Nullable byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(int columnIndex, @Nullable Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(int columnIndex, @Nullable Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(int columnIndex, @Nullable Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, @Nullable Reader x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(int columnIndex, @Nullable Object x, int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(int columnIndex, @Nullable Object x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNull(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBoolean(String columnLabel, boolean x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateByte(String columnLabel, byte x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateShort(String columnLabel, short x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateInt(String columnLabel, int x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateLong(String columnLabel, long x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateFloat(String columnLabel, float x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDouble(String columnLabel, double x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBigDecimal(String columnLabel, @Nullable BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(String columnLabel, @Nullable String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(String columnLabel, @Nullable byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(String columnLabel, @Nullable Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(String columnLabel, @Nullable Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(String columnLabel, @Nullable Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, @Nullable InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, @Nullable Reader reader, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(String columnLabel, @Nullable Object x, int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(String columnLabel, @Nullable Object x) throws SimulatedSQLException {
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
    default Object getObject(int columnIndex, Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Ref getRef(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    @SuppressWarnings("nullness")
    default Blob getBlob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Clob getClob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Array getArray(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Object getObject(String columnLabel, Map<String, Class<?>> map)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Ref getRef(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Blob getBlob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Clob getClob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Array getArray(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(int columnIndex, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Date getDate(String columnLabel, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(int columnIndex, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Time getTime(String columnLabel, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(int columnIndex, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Timestamp getTimestamp(String columnLabel, @Nullable Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default URL getURL(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default URL getURL(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(int columnIndex, @Nullable Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(String columnLabel, @Nullable Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, @Nullable Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, @Nullable Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, @Nullable Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, @Nullable Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(int columnIndex, @Nullable Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(String columnLabel, @Nullable Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default RowId getRowId(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default RowId getRowId(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(int columnIndex, @Nullable RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(String columnLabel, @Nullable RowId x) throws SimulatedSQLException {
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
    default void updateNString(int columnIndex, @Nullable String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNString(String columnLabel, @Nullable String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, @Nullable NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, @Nullable NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default NClob getNClob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default NClob getNClob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default SQLXML getSQLXML(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default SQLXML getSQLXML(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(int columnIndex, @Nullable SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(String columnLabel, @Nullable SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getNString(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default String getNString(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getNCharacterStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default Reader getNCharacterStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(int columnIndex, @Nullable Reader x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(String columnLabel, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, @Nullable Reader x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, @Nullable InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, @Nullable InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, @Nullable InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, @Nullable Reader reader, long length) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, @Nullable Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(int columnIndex, @Nullable Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(String columnLabel, @Nullable Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, @Nullable Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, @Nullable InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, @Nullable Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, @Nullable InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, @Nullable InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, @Nullable Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default <T> T getObject(int columnIndex, Class<T> type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    @Nullable
    default <T> T getObject(String columnLabel, Class<T> type) throws SimulatedSQLException {
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
