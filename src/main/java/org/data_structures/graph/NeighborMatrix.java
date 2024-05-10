package org.data_structures.graph;

import lombok.Getter;
import org.data_structures.utility.Matrix;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Scale;
import org.data_structures.utility.exception.LoopException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeighborMatrix {

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

    public NeighborMatrix(Matrix matrix) {
        this.matrix = matrix;

        size = matrix.getRowCount();
        lstTarjanColors = new ArrayList<>(Arrays.asList(new Integer[getSize()]));
        Collections.fill(lstTarjanColors, 0);

        dfsPath =  new ArrayList<>(matrix.getRowCount());
        delPath = new ArrayList<>(matrix.getRowCount());
    }

    @Scale
    public Integer getSize() {
        return size;
    }

    private Long dfsTime = 0L;

    @Duration("DFS_time")
    public Long getDFSTime() {
        return dfsTime;
    }

    @Exam
    public List<Integer> dfs() {
        dfsTime = System.nanoTime();

        for (int i = 0; i < matrix.getRowCount(); i++)
            if (lstTarjanColors.get(i) == 0) {
                dfs(i);
                i = 0;
            }

        dfsTime = System.nanoTime() - dfsTime;

        return dfsPath;
    }

    private void dfs(int node) {
        lstTarjanColors.set(node, 1);
        if (dfsPath.contains(node))
            throw new LoopException("Graph has loop", matrix);

        for (int i = 0; i < getSize(); i++) {
            if (matrix.get(node, i) > 0 && lstTarjanColors.get(i) == 0) {
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
            if (edge != null && edge > 0)
                inDegree++;
        }


        return inDegree;
    }
}
