package dev.strand.netsgiro.exception;

/**
 * An exception caused by some data not adhering to the standard.
 */
public class ValidationException extends Exception {

    private static final long serialVersionUID = -6369676260668079628L;

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }

}
