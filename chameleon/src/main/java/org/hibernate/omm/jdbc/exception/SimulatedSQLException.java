package org.hibernate.omm.jdbc.exception;

import com.mongodb.lang.Nullable;

import java.sql.SQLException;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public class SimulatedSQLException extends SQLException {

    public SimulatedSQLException() {
    }

    public SimulatedSQLException(@Nullable String reason) {
        super(reason == null ? "" : reason);
    }

    public SimulatedSQLException(@Nullable String reason, Throwable cause) {
        super(reason == null ? "" : reason, cause);
    }
}
