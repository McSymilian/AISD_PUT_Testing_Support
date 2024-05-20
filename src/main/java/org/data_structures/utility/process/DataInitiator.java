package org.data_structures.utility.process;

import lombok.extern.java.Log;
import org.data_structures.data.DataGenerator;
import org.data_structures.data.MatrixGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public class DataInitiator {
    private DataInitiator() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, List<List<Integer>>> initDataSets() {
        log.info("Data sets are being generated...");
        var generators = dataGeneratorsList();
        return dataSets(generators);
    }

    private static List<DataGenerator> dataGeneratorsList() {
        return List.of(new MatrixGenerator());
    }

    private static Map<String, List<List<Integer>>> dataSets(List<DataGenerator> dataGeneratorList) {
        var datasetMap = new HashMap<String, List<List<Integer>>>();

        for (var generator: dataGeneratorList) {
            datasetMap.put(generator.getClass().getSimpleName(), new ArrayList<>());

            datasetMap.get(generator.getClass().getSimpleName()).addAll(generateDatasets(generator));
        }
        return datasetMap;
    }

    private static List<List<Integer>> generateDatasets(DataGenerator datasetGenerator) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 10; i <= 1000; i *= 10)
            for (int j = 0; j < 50; j++) {
                res.add(datasetGenerator.generate(i, Integer.MIN_VALUE, Integer.MAX_VALUE));
            }

        return res;
    }
}
