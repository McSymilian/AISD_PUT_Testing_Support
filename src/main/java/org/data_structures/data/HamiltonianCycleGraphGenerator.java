package org.data_structures.data;

import org.data_structures.utility.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HamiltonianCycleGraphGenerator extends DataGenerator{
    @Override
    public List<Integer> generate(int size, int minValue, int maxValue) {
        var edges = new ArrayList<Pair<Integer, Integer>>();
        for (int x = size - 1; x > 0; x--)
            edges.add(Pair.of(x, x - 1));

        edges.add(Pair.of(0, size - 1));
        for (int e = maxValue - size, x = 2; e <= maxValue; x++) {
            for (int i = 0; i < size; i++) {
                edges.add(Pair.of(i, (i + x) % size));
                e++;
            }
        }
        edges.add(Pair.of(size, edges.size()));

        return edges.stream()
                .map(edge -> List.of(edge.first(), edge.second()))
                .flatMap(Collection::stream)
                .toList();
    }
}
