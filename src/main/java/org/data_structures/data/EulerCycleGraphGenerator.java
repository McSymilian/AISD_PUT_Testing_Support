package org.data_structures.data;

import org.data_structures.utility.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EulerCycleGraphGenerator extends MatrixGenerator {

    @Override
    public List<Integer> generate(int size, int minValue, int maxValue) {
        var edges = new ArrayList<Pair<Integer, Integer>>();
        for (int x = size - 1; x > 0; x -- )
            edges.add(Pair.of(x, x - 1));

        edges.add(Pair.of(0, size - 1));

        for (int x = size - 1; x >= 0; x -- )
                for (int y = size - 1; y >= 0; y--) {
                    if (y == x || y == x - 1) continue;

                    for (int z = size - 1; z >= 0; z--) {
                        if (z == y || z == y - 1 || z == x || z - 1 == x) continue;
                        edges.add(Pair.of(x, y));
                        edges.add(Pair.of(y, z));
                        edges.add(Pair.of(z, x));

                    }
                }

        return edges.stream()
                .map(edge -> List.of(edge.first(), edge.second()))
                .flatMap(Collection::stream)
                .toList();
    }
}
