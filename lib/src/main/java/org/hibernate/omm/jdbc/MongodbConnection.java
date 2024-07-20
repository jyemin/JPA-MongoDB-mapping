package org.hibernate.omm.jdbc;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.hibernate.omm.jdbc.adapter.ArrayAdapter;
import org.hibernate.omm.jdbc.adapter.ConnectionAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import com.mongodb.client.MongoDatabase;

public class MongodbConnection extends ConnectionAdapter {

	private final MongoDatabase mongoDatabase;

	private boolean autoCommit;
	private boolean closed;

	private SQLWarning sqlWarning;

	public MongodbConnection(MongoDatabase mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	@Override
	public Statement createStatement() {
		return new MongodbStatement( mongoDatabase, this );
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SimulatedSQLException {
		return new MongodbPreparedStatement( mongoDatabase, this, sql );
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public String nativeSQL(String sql) throws SimulatedSQLException {
		throw new NotSupportedSQLException();
	}

	@Override
	public boolean getAutoCommit() throws SimulatedSQLException {
		return this.autoCommit;
	}

	@Override
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	@Override
	public void commit() {
	}

	@Override
	public void rollback() {
	}

	@Override
	public void close() {
		this.closed = true;
	}

	@Override
	public boolean isClosed() {
		return this.closed;
	}

	@Override
	public void clearWarnings() {
		sqlWarning = null;
	}

	@Override
	public SQLWarning getWarnings() {
		return sqlWarning;
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SimulatedSQLException {
		return new ArrayAdapter() {
			@Override
			public int getBaseType() throws SQLException {
				return 0;
			}

			@Override
			public Object getArray() throws SQLException {
				return elements;
			}
		};
	}
}
