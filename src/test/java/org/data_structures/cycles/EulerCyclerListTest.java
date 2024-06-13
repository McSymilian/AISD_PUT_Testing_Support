package org.data_structures.cycles;

import org.data_structures.data.MatrixGenerator;
import org.data_structures.graph.MatrixTestDataSupplier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EulerCyclerListTest extends MatrixTestDataSupplier {

    @Test
    void traceCycle() {
        EulerCyclerList hcl = new EulerCyclerList(MatrixGenerator.readEdgesFromFile("inputGraph.txt"));
        assertEquals(19, hcl.traceCycle().size());
        assertEquals(List.of(0, 1, 2, 4, 6, 3, 5, 3, 6, 4, 5, 7, 5, 4, 2, 1, 0, 7, 0), hcl.getPath());
    }
}