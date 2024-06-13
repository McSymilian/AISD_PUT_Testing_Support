package org.data_structures.data;

import org.data_structures.graph.MatrixTestDataSupplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGeneratorTest extends MatrixTestDataSupplier {

    @Test
    void generateMatrix() {
        assertArrayEquals(getTestNeighbourMatrix().toArray(), MatrixGenerator.generateMatrix(getTestEdges()).toArray());

    }

    @Test
    void generateGraphMatrix() {
        assertArrayEquals(getTestGraphMatrix().toArray(), MatrixGenerator.generateGraphMatrix(getTestEdges()).toArray());
    }


    @Test
    void readEdgesFromFile() {
        var edges = MatrixGenerator.readEdgesFromFile("inputGraph.txt");
        assertArrayEquals(getTestEdges().toArray(), edges.toArray());
        assertArrayEquals(getTestNeighbourMatrix().toArray(), MatrixGenerator.generateMatrix(getTestEdges()).toArray());
        assertArrayEquals(getTestGraphMatrix().toArray(), MatrixGenerator.generateGraphMatrix(getTestEdges()).toArray());
    }

    @Test
    void generateRandomEdges() {
        int size = 100;
        var edges = MatrixGenerator.generateRandomEdges(size);
        assertEquals((size * (size - 1) >> 2) + 1, edges.size());
        for (var edge : edges) {
            assertEquals(2, edge.size());
        }
    }
}