package org.data_structures.data;

import org.data_structures.utility.Matrix;
import org.data_structures.utility.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MatrixGenerator extends DataGenerator {

    public static Matrix parseMatrix(String matrix) {
        String[] rows = matrix.split("\n");

        int columnCount = rows[0].split(" ").length;
        List<List<Integer>> matrixArray = new ArrayList<>();

        for (String s : rows) {
            List<Integer> row = new ArrayList<>();
            String[] columns = s.split(" ");
            for (int j = 0; j < columnCount; j++) {
                row.add(Integer.parseInt(columns[j]));
            }
            matrixArray.add(row);
        }

        return new Matrix(matrixArray);
    }

    public static Matrix generateMatrix(List<List<Integer>> edges) {
        Matrix matrix = new Matrix(edges.getFirst().getFirst());

        for (int i = 1; i < edges.size(); i++) {
            List<Integer> edge = edges.get(i);
            matrix.set(edge.get(0) - 1, edge.get(1) - 1, 1);
            matrix.set(edge.get(1) - 1, edge.get(0) - 1, -1);
        }

        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                if (matrix.get(i, j) == null) {
                    matrix.set(i, j, 0);
                }
            }
        }

        return matrix;
    }

    public static Matrix generateGraphMatrix(Matrix matrix) {
        List<List<Integer>> successors = new ArrayList<>();
        List<List<Integer>> predecessors = new ArrayList<>();
        List<List<Integer>> noIncidences = new ArrayList<>();

        initRelationsLists(matrix, successors, predecessors, noIncidences);

        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                switch (matrix.get(i, j)) {
                    case 1 -> {
                        successors.get(i).add(j);
                        predecessors.get(j).add(i);
                    }
                    case -1 -> {
                        successors.get(j).add(i);
                        predecessors.get(i).add(j);
                    }
                    case 0 -> {
                        noIncidences.get(i).add(j);
                        noIncidences.get(j).add(i);
                    }
                    default -> throw new IllegalArgumentException("Invalid value in matrix");
                }
            }
        }

        Matrix graphMatrix = new Matrix(matrix.getRowCount(), matrix.getColumnCount() + 3);

        for (int i = 0; i < matrix.getRowCount(); i++) {
            setSuccessors(matrix, successors, i, graphMatrix);

            setPredecessors(matrix, predecessors, i, graphMatrix);

            setNoIncidence(matrix, noIncidences, i, graphMatrix);
        }

        return graphMatrix;
    }

    private static void setNoIncidence(Matrix matrix, List<List<Integer>> noIncidences, int i, Matrix graphMatrix) {
        var noInc = noIncidences.get(i);
        int noIncIndex = 1;
        graphMatrix.set(
                i,
                matrix.getColumnCount() + 2,
                noInc.size() > 1 ? noInc.get(noIncIndex) + 1 : 0
        );

        for (int j = noIncIndex; j <= noInc.size(); j++) {
            graphMatrix.set(
                    i,
                    noInc.get(j - 1),
                    -(noInc.get(j < noInc.size() ? j: j - 1) + 1)
            );
        }
    }

    private static void setPredecessors(Matrix matrix, List<List<Integer>> predecessors, int i, Matrix graphMatrix) {
        var preds = predecessors.get(i);
        int predsIndex = 1;
        graphMatrix.set(
                i,
                matrix.getColumnCount() + 1,
                preds.size() > 1 ? preds.get(predsIndex) + 1 : 0
        );

        for (int j = predsIndex; j <= preds.size(); j++) {
            graphMatrix.set(
                    i,
                    preds.get(j - 1),
                    preds.get(j < preds.size() ? j: j - 1) + 1 + matrix.getRowCount()
            );
        }
    }

    private static void setSuccessors(Matrix matrix, List<List<Integer>> successors, int i, Matrix graphMatrix) {
        var succs = successors.get(i);
        int succsIndex = 1;
        graphMatrix.set(
                i,
                matrix.getColumnCount(),
                succs.size() > 1 ? succs.get(succsIndex) + 1 : 0
        );

        for (int j = succsIndex; j <= succs.size(); j++) {
            graphMatrix.set(
                    i,
                    succs.get(j - 1),
                    succs.get(j < succs.size() ? j: j - 1) + 1
            );
        }
    }

    private static void initRelationsLists(Matrix matrix, List<List<Integer>> succesors, List<List<Integer>> predecessors, List<List<Integer>> noIncidences) {
        for (int vertix: matrix.getVertices()) {
            List<Integer> vertixList = List.of(vertix);

            succesors.add(new ArrayList<>(vertixList));
            predecessors.add(new ArrayList<>(vertixList));
            noIncidences.add(new ArrayList<>(vertixList));
        }
    }

    public static Matrix generateGraphMatrix(List<List<Integer>> edges) {
        return generateGraphMatrix(generateMatrix(edges));
    }

    public static List<List<Integer>> readEdgesFromFile(String path) {
        List<List<Integer>> edges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] edge = line.split(" ");
                edges.add(List.of(Integer.parseInt(edge[0]), Integer.parseInt(edge[1])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return edges;
    }

    private static final Random random = new Random();
    public static List<List<Integer>> generateRandomEdges(int vertices) {
        Set<Pair<Integer, Integer>> randomEdges = new TreeSet<>();
        int maxEdges = vertices * (vertices - 1) / 2;
        int edges = maxEdges / 2;

        while (randomEdges.size() < edges) {
            int beg = random.nextInt(1, vertices - 2);
            int dest = random.nextInt(beg  + 1, vertices + 1);
            randomEdges.add(Pair.of(beg, dest));
        }

        var res = new ArrayList<>(randomEdges
                .stream()
                .map(pair -> List.of(pair.first(), pair.second()))
                .toList());

        res.addFirst(List.of(vertices, edges));
        return res;
    }

    @Override
    public List<Integer> generate(int size, int minValue, int maxValue) {
        return generateRandomEdges(size)
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public static List<List<Integer>> map(List<Integer> input) {
        final AtomicInteger counter = new AtomicInteger(0);
        return input.stream()
                .collect(
                        Collectors.groupingBy(item -> {
                            final int i = counter.getAndIncrement();
                            return (i % 2 == 0) ? i : i - 1;
                        })
                )
                .values()
                .stream()
                .toList();
    }
}
