package org.hibernate.omm.jdbc.exception;

public class NotSupportedSQLException extends SimulatedSQLException {
    public NotSupportedSQLException() {
    }

    public NotSupportedSQLException(String reason) {
        super(reason);
    }

    public NotSupportedSQLException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
