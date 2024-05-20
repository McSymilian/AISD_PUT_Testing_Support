package org.data_structures.graph;

import lombok.Getter;
import org.data_structures.data.MatrixGenerator;
import org.data_structures.utility.Matrix;
import org.data_structures.utility.Structure;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.annotation.Scale;
import org.data_structures.utility.exception.LoopException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Examined
public class GraphMatrix extends Structure {
    private final Matrix matrix;

    /**
     * 0 - white
     * 1 - gray
     * 2 - black
     */
    private final List<Integer> lstTarjanColors;

    @Getter
    private final List<Integer> dfsPath;

    @Getter
    private final List<Integer> delPath;

    private final int size;
    public GraphMatrix(Matrix matrix) {
        this.matrix = matrix.copy();

        size = matrix.getRowCount();
        lstTarjanColors = new ArrayList<>(Arrays.asList(new Integer[Math.toIntExact(getSize())]));
        Collections.fill(lstTarjanColors, 0);

        dfsPath =  new ArrayList<>(matrix.getRowCount());
        delPath = new ArrayList<>(matrix.getRowCount());
    }

    @Scale("size")
    public Long getSize() {
        return (long) size;
    }

    private Long dfsTime = 0L;

    @Duration("DFS_time")
    public Long getDFSTime() {
        return dfsTime;
    }


    @Exam
    public List<Integer> dfs() {
        dfsTime = System.nanoTime();

        int i = 0;
        while (i < matrix.getRowCount()) {
            if (lstTarjanColors.get(i) == 0) {
                dfs(i);
                i = -1;
            }
            i++;
        }

        dfsTime = System.nanoTime() - dfsTime;

        return dfsPath;
    }

    private void dfs(int node) {
        lstTarjanColors.set(node, 1);
        if (dfsPath.contains(node))
            throw new LoopException("Graph has loop", matrix);

        for (int i = 0; i < getSize(); i++) {
            Integer edge = matrix.get(node, i);
            if (edge != null && edge >= 1 && edge < getSize() + 1 && lstTarjanColors.get(i) == 0) {
                dfs(i);
            }
        }

        dfsPath.addFirst(node);
    }


    private Long delTime = 0L;

    @Duration("DEL_time")
    public Long getDelTime() {
        return delTime;
    }

    @Exam
    public List<Integer> del() {
        int begin = -1;

        for (int i = 0; i < getSize(); i++)
            if (inDegree(i) == 0) {
                begin = i;
                break;
            }

        if (begin == -1)
            throw new LoopException("Graph has no begin", matrix);

        delTime = System.nanoTime();

        while (delPath.size() < getSize()) {
            boolean hasZeroDegree = false;
            for (int i = begin; i < getSize(); i++) {
                Integer node = matrix.get(i, i);
                if (node != null && inDegree(i) == 0) {
                    del(i);
                    hasZeroDegree = true;
                }
            }

            if (!hasZeroDegree)
                throw new LoopException("Graph has loop", matrix);
        }

        delTime = System.nanoTime() - delTime;

        return delPath;
    }

    private void del(int node) {
        delPath.addLast(node);
        matrix.removeRowAndColumn(node);
    }

    private int inDegree(int node) {
        int inDegree = 0;
        for (int i = 0; i < getSize(); i++) {
            Integer edge = matrix.get(i, node);
            if (edge != null && edge > 0 && edge < getSize() + 1)
                inDegree++;
        }


        return inDegree;
    }

    public static Structure of(List<Integer> values) {
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < values.size(); i += 2)
            edges.add(new ArrayList<>(List.of(values.get(i), values.get(i + 1))));

        return new GraphMatrix(MatrixGenerator.generateGraphMatrix(MatrixGenerator.generateMatrix(edges)));
    }
}
