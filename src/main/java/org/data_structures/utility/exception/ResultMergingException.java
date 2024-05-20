package org.data_structures.utility.exception;

public class ResultMergingException extends RuntimeException {
    public ResultMergingException() {
    }

    public ResultMergingException(String message) {
        super(message);
    }

    public ResultMergingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultMergingException(Throwable cause) {
        super(cause);
    }
}
