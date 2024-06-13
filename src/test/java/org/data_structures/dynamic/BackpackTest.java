package org.data_structures.dynamic;

import org.data_structures.graph.MatrixTestDataSupplier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackpackTest extends MatrixTestDataSupplier {

    @Test
    void bruteForce() {
        Backpack backpack = new Backpack(List.of(10, 4, 2, 3, 3, 4, 4, 5, 5, 6));
        backpack.bruteForce();
        assertEquals(List.of(1,2,3), backpack.getBruteForceResultNormalized());
    }

    @Test
    void greedy() {
        Backpack backpack = new Backpack(List.of(10, 4, 2, 3, 3, 4, 4, 5, 5, 6));
        backpack.greedy();
        assertEquals(List.of(0, 1, 2), backpack.getGreedyResultNormalized());
    }

    @Test
    void dynamicProgramming() {
        Backpack backpack = new Backpack(List.of(10, 4, 2, 3, 3, 4, 4, 5, 5, 6));
        backpack.dynamicProgramming();
        assertEquals(14, backpack.getDynamicProgrammingResult());
        assertEquals(List.of(1, 2, 3), backpack.getDynamicProgrammingResultNormalized());
    }
}