package dev.strand.netsgiro.exception;

/**
 * An exception which occured during parsing.
 * Often a wrapper around a {@link ValidationException} cause.
 */
public class ParseException extends Exception {

    private static final long serialVersionUID = -7552746500487958685L;

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String message) {
        super(message);
    }

}
