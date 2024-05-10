package org.data_structures.graph;

import org.data_structures.data.MatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeighborMatrixTest extends MatrixTest {

    @Test
    void dfs() {
        NeighborMatrix neighborMatrix = new NeighborMatrix(MatrixGenerator.generateMatrix(MatrixGenerator.readEdgesFromFile("inputGraph.txt")));
        List<Integer> ans =neighborMatrix.dfs();
        assertEquals(List.of(3, 4, 0, 1, 2), ans);
    }

    @Test
    void del() {
        NeighborMatrix neighborMatrix = new NeighborMatrix(getTestNeighbourMatrix());
        List<Integer> ans = neighborMatrix.del();
        assertEquals(List.of(0, 1, 3, 4, 2), ans);
    }
}