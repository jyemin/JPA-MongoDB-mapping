package org.hibernate.omm.jdbc.exception;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class ResultSetClosedSQLException extends SimulatedSQLException {
    public ResultSetClosedSQLException() {
    }

    public ResultSetClosedSQLException(String reason) {
        super(reason);
    }

    public ResultSetClosedSQLException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
