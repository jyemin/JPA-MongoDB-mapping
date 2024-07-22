package org.hibernate.omm.jdbc.exception;

public class ColumnInfoUnknownSQLException extends SimulatedSQLException {
    public ColumnInfoUnknownSQLException() {
    }

    public ColumnInfoUnknownSQLException(final String reason) {
        super(reason);
    }

    public ColumnInfoUnknownSQLException(final String reason, final Throwable cause) {
        super(reason, cause);
    }
}
