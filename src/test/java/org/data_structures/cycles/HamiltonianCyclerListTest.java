package org.data_structures.cycles;

import org.data_structures.data.MatrixGenerator;
import org.data_structures.graph.MatrixTestDataSupplier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HamiltonianCyclerListTest extends MatrixTestDataSupplier {

    @Test
    void traceCycle() {
        HamiltonianCyclerList hcl = new HamiltonianCyclerList(MatrixGenerator.readEdgesFromFile("inputGraph.txt"));
        assertEquals(9, hcl.traceCycle().size());
        assertEquals(List.of(0, 1, 2, 4, 6, 3, 5, 7, 0), hcl.getPath());
    }
}