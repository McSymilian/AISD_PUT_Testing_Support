package org.data_structures.utility.exception;

public class ResultFileCreationException extends RuntimeException {
    private ResultFileCreationException() {
        super();
    }

    public ResultFileCreationException(String message) {
        super(message);
    }

    public ResultFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultFileCreationException(Throwable cause) {
        super(cause);
    }
}
