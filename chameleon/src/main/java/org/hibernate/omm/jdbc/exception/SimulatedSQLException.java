package org.hibernate.omm.jdbc.exception;

import java.sql.SQLException;

public class SimulatedSQLException extends SQLException {

    public SimulatedSQLException() {
    }

    public SimulatedSQLException(String reason) {
        super(reason);
    }

    public SimulatedSQLException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
