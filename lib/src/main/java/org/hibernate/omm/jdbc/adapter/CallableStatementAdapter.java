package org.hibernate.omm.jdbc.adapter;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

public class CallableStatementAdapter implements CallableStatement {

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean wasNull() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public String getString(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean getBoolean(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public byte getByte(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public short getShort(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getInt(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public long getLong(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public float getFloat(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public double getDouble(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public byte[] getBytes(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Date getDate(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Time getTime(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Object getObject(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Ref getRef(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Blob getBlob(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Clob getClob(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Array getArray(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, String typeName)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType, int scale)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType, String typeName)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public URL getURL(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setURL(String parameterName, URL val) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBoolean(String parameterName, boolean x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setByte(String parameterName, byte x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setShort(String parameterName, short x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setInt(String parameterName, int x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setLong(String parameterName, long x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setFloat(String parameterName, float x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDouble(String parameterName, double x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setString(String parameterName, String x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBytes(String parameterName, byte[] x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDate(String parameterName, Date x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTime(String parameterName, Time x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType, int scale)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(String parameterName, Object x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDate(String parameterName, Date x, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTime(String parameterName, Time x, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public String getString(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean getBoolean(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public byte getByte(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public short getShort(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getInt(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public long getLong(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public float getFloat(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public double getDouble(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public byte[] getBytes(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Date getDate(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Time getTime(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Timestamp getTimestamp(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Object getObject(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Ref getRef(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Blob getBlob(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Clob getClob(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Array getArray(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Date getDate(String parameterName, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Time getTime(String parameterName, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public URL getURL(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public RowId getRowId(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public RowId getRowId(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setRowId(String parameterName, RowId x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNString(String parameterName, String value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(String parameterName, NClob value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(String parameterName, Reader reader, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(String parameterName, Reader reader, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public NClob getNClob(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public NClob getNClob(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public SQLXML getSQLXML(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public String getNString(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public String getNString(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Reader getNCharacterStream(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Reader getCharacterStream(int parameterIndex) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Reader getCharacterStream(String parameterName) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(String parameterName, Blob x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(String parameterName, Clob x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(String parameterName, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(String parameterName, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public <T> T getObject(int parameterIndex, Class<T> type) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public <T> T getObject(String parameterName, Class<T> type) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ResultSet executeQuery() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int executeUpdate() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setString(int parameterIndex, String x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void clearParameters() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean execute() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void addBatch() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length)
			throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int executeUpdate(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void close() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getMaxFieldSize() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setMaxFieldSize(int max) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getMaxRows() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setMaxRows(int max) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getQueryTimeout() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setQueryTimeout(int seconds) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void cancel() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public SQLWarning getWarnings() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void clearWarnings() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setCursorName(String name) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean execute(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ResultSet getResultSet() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getUpdateCount() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean getMoreResults() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setFetchDirection(int direction) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getFetchDirection() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setFetchSize(int rows) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getFetchSize() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getResultSetConcurrency() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getResultSetType() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void addBatch(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void clearBatch() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int[] executeBatch() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public Connection getConnection() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean getMoreResults(int current) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public int getResultSetHoldability() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean isClosed() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void setPoolable(boolean poolable) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean isPoolable() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public void closeOnCompletion() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}
}
