package org.data_structures.cycles;

import org.data_structures.data.MatrixGenerator;
import org.data_structures.graph.MatrixTestDataSupplier;
import org.data_structures.utility.Matrix;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EulerCyclerMatrixTest extends MatrixTestDataSupplier {

    @Test
    void traceCycle() {
        Matrix testData = MatrixGenerator.parseMatrix(
                """
                4 4
                1 2
                2 3
                3 4
                4 1""");
        EulerCyclerMatrix ham = new EulerCyclerMatrix(testData);
        ham.traceCycle();
        assertEquals(List.of(0, 3, 2, 1), ham.getPath());
    }
}