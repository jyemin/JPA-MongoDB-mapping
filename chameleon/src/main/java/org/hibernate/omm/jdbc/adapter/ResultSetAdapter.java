package org.hibernate.omm.jdbc.adapter;

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
    default BigDecimal getBigDecimal(int columnIndex, int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte[] getBytes(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getAsciiStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getUnicodeStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getBinaryStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
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
    default BigDecimal getBigDecimal(String columnLabel, int scale) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default byte[] getBytes(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getAsciiStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getUnicodeStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default InputStream getBinaryStream(String columnLabel) throws SimulatedSQLException {
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
    default String getCursorName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSetMetaData getMetaData() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int findColumn(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getCharacterStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getCharacterStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default BigDecimal getBigDecimal(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
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
    default void updateBigDecimal(int columnIndex, BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(int columnIndex, String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(int columnIndex, byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(int columnIndex, Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(int columnIndex, Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(int columnIndex, Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, Reader x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(int columnIndex, Object x, int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(int columnIndex, Object x) throws SimulatedSQLException {
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
    default void updateBigDecimal(String columnLabel, BigDecimal x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateString(String columnLabel, String x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBytes(String columnLabel, byte[] x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateDate(String columnLabel, Date x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTime(String columnLabel, Time x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateTimestamp(String columnLabel, Timestamp x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, InputStream x, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, Reader reader, int length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(String columnLabel, Object x, int scaleOrLength)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateObject(String columnLabel, Object x) throws SimulatedSQLException {
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
    default Statement getStatement() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(int columnIndex, Map<String, Class<?>> map) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Ref getRef(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Blob getBlob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Clob getClob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array getArray(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Object getObject(String columnLabel, Map<String, Class<?>> map)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Ref getRef(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Blob getBlob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Clob getClob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Array getArray(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(int columnIndex, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Date getDate(String columnLabel, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(int columnIndex, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Time getTime(String columnLabel, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(int columnIndex, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Timestamp getTimestamp(String columnLabel, Calendar cal) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default URL getURL(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default URL getURL(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(int columnIndex, Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRef(String columnLabel, Ref x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, Blob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, Clob x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(int columnIndex, Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateArray(String columnLabel, Array x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default RowId getRowId(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default RowId getRowId(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(int columnIndex, RowId x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateRowId(String columnLabel, RowId x) throws SimulatedSQLException {
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
    default void updateNString(int columnIndex, String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNString(String columnLabel, String nString) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, NClob nClob) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default NClob getNClob(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default NClob getNClob(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLXML getSQLXML(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default SQLXML getSQLXML(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getNString(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getNString(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getNCharacterStream(int columnIndex) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Reader getNCharacterStream(String columnLabel) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(int columnIndex, Reader x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(String columnLabel, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, Reader x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, InputStream x, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, InputStream inputStream, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, Reader reader, long length) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, Reader reader, long length)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(int columnIndex, Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNCharacterStream(String columnLabel, Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(int columnIndex, InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(int columnIndex, InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(int columnIndex, Reader x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateAsciiStream(String columnLabel, InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBinaryStream(String columnLabel, InputStream x) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateCharacterStream(String columnLabel, Reader reader)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(int columnIndex, InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateBlob(String columnLabel, InputStream inputStream) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(int columnIndex, Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateClob(String columnLabel, Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(int columnIndex, Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default void updateNClob(String columnLabel, Reader reader) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T getObject(int columnIndex, Class<T> type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
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
