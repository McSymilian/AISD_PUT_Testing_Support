package org.data_structures.utility.exception;

public class AlgorithmExaminationException extends RuntimeException {
    public AlgorithmExaminationException() {
    }

    public AlgorithmExaminationException(String message) {
        super(message);
    }

    public AlgorithmExaminationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgorithmExaminationException(Throwable cause) {
        super(cause);
    }
}
