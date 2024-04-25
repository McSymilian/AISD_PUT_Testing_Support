package org.data_structures.tree;

import org.data_structures.data.DataGenerator;
import org.data_structures.data.RandomListGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class DataStructuresTest {
    private Random random = new Random(System.nanoTime());
    public List<Integer> generateDataSet(int size) {
        return RandomListGenerator.generateList(size, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public List<Integer> generateDataSet() {
        return generateDataSet(100000);
    }

    public List<Integer> getDataSet() {
        return List.of(50, 30, 70, 80, 60, 20, 40);
    }
}
