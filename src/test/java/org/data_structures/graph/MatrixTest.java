package org.data_structures.graph;

import org.data_structures.data.MatrixGenerator;
import org.data_structures.utility.Matrix;

import java.util.ArrayList;
import java.util.List;

import static org.data_structures.data.MatrixGenerator.*;

public class MatrixTest {
    public Matrix getTestNeighbourMatrix() {
        return generateMatrix(getTestEdges());
    }

    public Matrix getTestGraphMatrix() {
        return generateGraphMatrix(generateMatrix(getTestEdges()));
    }

    public List<List<Integer>> getTestEdges() {
        return new ArrayList<>(List.of(
            new ArrayList<>(List.of(5, 4)),
            new ArrayList<>(List.of(1, 2)),
            new ArrayList<>(List.of(2, 3)),
            new ArrayList<>(List.of(4, 3)),
            new ArrayList<>(List.of(4, 5))
        ));
    }


}
