package org.hibernate.omm.jdbc;

import com.mongodb.client.MongoDatabase;

import org.hibernate.omm.jdbc.adapter.ConnectionAdapter;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLWarning;
import java.sql.Statement;

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

}
