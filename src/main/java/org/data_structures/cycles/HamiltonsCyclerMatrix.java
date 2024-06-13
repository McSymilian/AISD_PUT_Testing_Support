package org.data_structures.cycles;

import lombok.Getter;
import org.data_structures.data.MatrixGenerator;
import org.data_structures.utility.Matrix;
import org.data_structures.utility.Structure;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.annotation.Factor;
import org.data_structures.utility.annotation.Measurement;
import org.data_structures.utility.annotation.Scale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Examined
public class HamiltonsCyclerMatrix extends Structure {

    private final Matrix matrix;

    @Getter
    private List<Integer> path = new ArrayList<>();

    public HamiltonsCyclerMatrix(Matrix matrix) {
        this.matrix = matrix;

        int edgesCount = countEdges(matrix);
        if (edgesCount == 0) this.fillingFactor = 0L;
        else this.fillingFactor = (long) (((double) matrix.getRowCount() / edgesCount) * 100L);
    }

    private int countEdges(Matrix matrix) {
        int c = 0;
        for (int i = 0; i < matrix.getRowCount(); i++)
            for (int j = 0; j < matrix.getColumnCount(); j++)
                c  += Math.abs(matrix.get(i, j));

        return c;
    }

    private Long traceCycleTime = 0L;

    @Duration("trace_cycle_time")
    public Long getTraceCycleTime() {
        return traceCycleTime;
    }

    private final Long fillingFactor;

    @Factor("filling")
    public Long getFilling() {
        return fillingFactor;
    }


    @Scale("size")
    public long getSize() {
        return matrix.getRowCount();
    }

    int countedVisits;
    int start;
    boolean[] visited;

    @Exam
    public List<Integer> traceCycle() {
        path = new ArrayList<>();
        visited = new boolean[matrix.getRowCount()];
        for (int i = 0; i < matrix.getRowCount(); i++) visited[i] = false;
        traceCycleTime = System.nanoTime();
        start = 0;
        countedVisits = 0;
        path.add(0);
        if (hamiltonian(start)) {
            traceCycleTime = System.nanoTime() - traceCycleTime;
            return path.reversed();
        }

        return Collections.emptyList();
    }

    public boolean hamiltonian(int v) {
        visited[v] = true;
        countedVisits++;
        for (int i = 0; i < matrix.getRowCount(); i++) {
            if (matrix.get(v, i) == 0) continue;
            if (i == start && countedVisits == matrix.getRowCount() || !visited[i] && hamiltonian(i)) {
                path.add(v);
                return true;
            }
        }
        visited[v] = false;
        countedVisits--;
        return false;
    }

    public static Structure of(List<Integer> input) {
        return new HamiltonsCyclerMatrix(MatrixGenerator.generateMatrix(MatrixGenerator.map(input)));
    }
}
