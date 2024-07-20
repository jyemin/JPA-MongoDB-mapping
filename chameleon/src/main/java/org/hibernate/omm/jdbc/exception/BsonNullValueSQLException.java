package org.hibernate.omm.jdbc.exception;

public class BsonNullValueSQLException extends SimulatedSQLException {

	public BsonNullValueSQLException() {
	}

	public BsonNullValueSQLException(String reason) {
		super( reason );
	}

	public BsonNullValueSQLException(String reason, Throwable cause) {
		super( reason, cause );
	}
}
