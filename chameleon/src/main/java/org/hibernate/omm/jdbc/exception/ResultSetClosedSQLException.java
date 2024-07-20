package org.hibernate.omm.jdbc.exception;

public class ResultSetClosedSQLException extends SimulatedSQLException {
	public ResultSetClosedSQLException() {
	}

	public ResultSetClosedSQLException(String reason) {
		super( reason );
	}

	public ResultSetClosedSQLException(String reason, Throwable cause) {
		super( reason, cause );
	}
}
