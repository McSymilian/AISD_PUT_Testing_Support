package org.data_structures.utility.exception;

import org.data_structures.utility.Matrix;

public class LoopException extends RuntimeException {

    private Matrix matrix;
    public LoopException(String message, Matrix matrix) {
        super(message);
        this.matrix = matrix;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + matrix.toString();
    }
}
