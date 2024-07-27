package org.hibernate.omm.exception;

/**
 * Denotes that the feature in question is supposed to be supported by OMM but not yet implemented in current version
 *
 * @author Nathan Xu
 * @see NotSupportedRuntimeException
 * @since 1.0.0
 */
public class NotYetImplementedException extends RuntimeException {
    public NotYetImplementedException() {
    }

    public NotYetImplementedException(final String message) {
        super(message);
    }

    public NotYetImplementedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotYetImplementedException(final Throwable cause) {
        super(cause);
    }
}
