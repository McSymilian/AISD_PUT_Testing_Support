package org.data_structures.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphMatrixTest extends MatrixTest {

    @Test
    void dfs() {
        GraphMatrix graphMatrix = new GraphMatrix(getTestGraphMatrix());
        assertEquals(List.of(3, 4, 0, 1, 2), graphMatrix.dfs());
    }

    @Test
    void del() {
        GraphMatrix graphMatrix = new GraphMatrix(getTestGraphMatrix());
        assertEquals(List.of(0, 1, 3, 4, 2), graphMatrix.del());
    }
}