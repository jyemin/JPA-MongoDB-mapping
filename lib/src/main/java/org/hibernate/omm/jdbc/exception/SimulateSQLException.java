package org.hibernate.omm.jdbc.exception;

import java.sql.SQLException;

public class SimulateSQLException extends SQLException {

  public SimulateSQLException(String reason) {
    super(reason);
  }

  public SimulateSQLException(String reason, Throwable cause) {
    super(reason, cause);
  }
}
