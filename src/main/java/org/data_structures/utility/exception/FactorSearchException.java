package org.data_structures.utility.exception;

public class FactorSearchException extends RuntimeException {
    public FactorSearchException() {
    }

    public FactorSearchException(String message) {
        super(message);
    }

    public FactorSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactorSearchException(Throwable cause) {
        super(cause);
    }
}
