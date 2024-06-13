package org.data_structures.utility.process;

import lombok.extern.java.Log;
import org.data_structures.cycles.EulerCyclerList;
import org.data_structures.cycles.EulerCyclerMatrix;
import org.data_structures.cycles.HamiltonianCyclerList;
import org.data_structures.cycles.HamiltonsCyclerMatrix;
import org.data_structures.graph.GraphMatrix;
import org.data_structures.graph.NeighborMatrix;
import org.data_structures.utility.annotation.Constructor;
import org.data_structures.utility.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log
public class ExaminedGenerator {
    private ExaminedGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Pair<String, Object>> generateExamined(Map<String, List<List<Integer>>> dataSets) {
        log.info("Examinables are being generated...");
        List<Pair<String, Object>> measurements = new ArrayList<>();
        for (var entry: dataSets.entrySet()) {
            for (var dataSet: entry.getValue()) {
                for (var it : getStructures()) {
                    measurements.add(Pair.of(
                            entry.getKey(),
                            it.create(new ArrayList<>(dataSet))
                    ));
                }
            }
        }

        return measurements;
    }

    private static List<Constructor> getStructures() {
        return List.of(EulerCyclerList::of, EulerCyclerMatrix::of);
    }
}
