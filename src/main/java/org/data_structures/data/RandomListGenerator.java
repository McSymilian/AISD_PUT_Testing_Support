package org.data_structures.data;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomListGenerator extends DataGenerator {
    private final Random random;

    public RandomListGenerator() {
        this.random = new Random();
    }
    private static final RandomListGenerator generator = new RandomListGenerator();

    public static List<Integer> generateList(int size, int minValue, int maxValue) {
        return generator.generate(size, minValue, maxValue);
    }

    @Override
    public List<Integer> generate(int size, int minValue, int maxValue) {
        Set<Integer> randomSet = LinkedHashSet.newLinkedHashSet(size);
        while (randomSet.size() < size) {
            int randomValue = random.nextInt(minValue, maxValue);
            randomSet.add(randomValue);
        }
        return randomSet.stream().toList();
    }

    public List<Double> generate(int size, double minValue, double maxValue) {
        List<Double> randomList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double randomValue = random.nextDouble(minValue, maxValue);
            randomList.add(randomValue);
        }
        return randomList;
    }
}

