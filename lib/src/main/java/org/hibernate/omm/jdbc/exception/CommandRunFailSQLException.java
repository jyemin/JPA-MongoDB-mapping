package org.hibernate.omm.jdbc.exception;

public class CommandRunFailSQLException extends SimulatedSQLException {

    public CommandRunFailSQLException() {
    }

    public CommandRunFailSQLException(String reason) {
        super(reason);
    }

    public CommandRunFailSQLException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
