package org.data_structures.cycles;

import org.data_structures.data.MatrixGenerator;
import org.data_structures.graph.MatrixTestDataSupplier;
import org.data_structures.utility.Matrix;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HamiltonsCyclerMatrixTest extends MatrixTestDataSupplier {

    @Test
    void traceCycle() {
        Matrix testData = MatrixGenerator.parseMatrix(
                """
                6 8
                1 2
                2 3
                2 5
                3 4
                4 6
                5 3
                5 4
                6 1""");
        HamiltonsCyclerMatrix ham = new HamiltonsCyclerMatrix(testData);
        ham.traceCycle();
        assertEquals(List.of(0, 5, 3, 4, 2, 1, 0), ham.getPath());
    }

}