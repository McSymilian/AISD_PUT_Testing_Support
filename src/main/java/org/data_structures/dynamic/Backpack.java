package org.data_structures.dynamic;

import lombok.Getter;
import org.data_structures.utility.Structure;
import org.data_structures.utility.annotation.Exam;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Backpack extends Structure {
    private final int capacity;
    private final int numOfItems;
    private final List<Integer> weights;
    private final List<Integer> values;

    public Backpack(List<Integer> input) {
        this.capacity = input.getFirst();
        this.numOfItems = input.get(1);
        this.weights = input.subList(2, numOfItems + 2);
        this.values = input.subList(numOfItems + 2, 2 * numOfItems + 2);
    }

    private int bruteForce(int capacity, int numOfItems, List<Integer> weights, List<Integer> values) {
        int fmax = 0;
        int xMax = 0;
        for (int x = 1; x < Math.pow(2, numOfItems) - 1; x++)
            if (W(x, numOfItems, weights) <= capacity)
                if (f(x, numOfItems, values) > fmax) {
                    fmax = f(x, numOfItems, values);
                    xMax = x;
                }

        return xMax;
    }

    private int W(int x, int numOfItems, List<Integer> weights) {
        int sum = 0;
        for (int i = 0; i < numOfItems; i++)
            if ((x & (1 << i)) != 0)
                sum += weights.get(i);

        return sum;
    }

    private int f(int x, int numOfItems, List<Integer> values) {
        int sum = 0;
        for (int i = 0; i < numOfItems; i++)
            if ((x & (1 << i)) != 0)
                sum += values.get(i);

        return sum;
    }

    private int bruteForceResult = 0;

    public List<Integer> getBruteForceResultNormalized() {
        var indexes = new ArrayList<Integer>();
        for (int i = 0; i < numOfItems; i++)
            if ((bruteForceResult & (1 << i)) != 0)
                indexes.add(i);

        return indexes;
    }

    @Exam
    public int bruteForce() {
        bruteForceResult = bruteForce(capacity, numOfItems, weights, values);
        return bruteForceResult;
    }

    private int greedyResult = 0;

    public int greedy(int numOfItems, List<Integer> weights, List<Integer> values) {
        var indexes = new ArrayList<Integer>();
        for (int i = 0; i < numOfItems; i++)
            indexes.add(i);

        indexes.sort((a, b) -> {
            double ratioA = (double) values.get(a) / weights.get(a);
            double ratioB = (double) values.get(b) / weights.get(b);
            return Double.compare(ratioB, ratioA);
        });

        int i = 0;
        int tmpCapacity = this.capacity;
        int result = 0;
        do {
            if (weights.get(indexes.get(i)) <= tmpCapacity) {
                tmpCapacity -= weights.get(indexes.get(i));
                result |= 1 << indexes.get(i);
            }
            i++;
        } while (tmpCapacity > 0 && i < numOfItems);

        return result;
    }

    @Exam
    public int greedy() {
        greedyResult = greedy(numOfItems, weights, values);
        return greedyResult;
    }

    public static int knapsack(List<Integer> wt, List<Integer> val, int W, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 0; i < n + 1; i++)
            dp[i][0] = 0;

        for (int w = 0; w < W + 1; w++)
            dp[0][w] = 0;


        for (int i = 1; i < n + 1; i++)
            for (int w = 1; w < W + 1; w++)
                if (wt.get(i - 1) <= w)
                    dp[i][w] = Math.max(val.get(i - 1) + dp[i - 1][w - wt.get(i - 1)], dp[i - 1][w]);
                else
                    dp[i][w] = dp[i - 1][w];

        int res = 0;
        while (n > 0 && W > 0) {
            if (dp[n][W] != dp[n - 1][W]) {
                res += 1<<n-1;
                W -= wt.get(n - 1);
            }
            n--;
        }

        return res;
    }

    private int dynamicProgrammingResult = 0;

    public List<Integer> getDynamicProgrammingResultNormalized() {
        var indexes = new ArrayList<Integer>();
        System.out.println(Integer.toBinaryString(dynamicProgrammingResult));
        for (int i = 0; i <= numOfItems; i++)
            if ((dynamicProgrammingResult & (1 << i)) != 0)
                indexes.add(i);

        return indexes;
    }

    public List<Integer> getGreedyResultNormalized() {
        var indexes = new ArrayList<Integer>();
        for (int i = 0; i <= numOfItems; i++)
            if ((greedyResult & (1 << i)) != 0)
                indexes.add(i);

        return indexes;
    }

    @Exam
    public int dynamicProgramming() {
        dynamicProgrammingResult = knapsack(weights, values, capacity, numOfItems);
        return dynamicProgrammingResult;
    }
}
