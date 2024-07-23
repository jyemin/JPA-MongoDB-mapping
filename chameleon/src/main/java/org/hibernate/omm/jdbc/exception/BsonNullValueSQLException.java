package org.hibernate.omm.jdbc.exception;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class BsonNullValueSQLException extends SimulatedSQLException {

    public BsonNullValueSQLException() {
    }

    public BsonNullValueSQLException(String reason) {
        super(reason);
    }

    public BsonNullValueSQLException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
