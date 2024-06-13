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
import java.util.Stack;

@Examined
public class EulerCyclerMatrix extends Structure {
    private final Matrix matrix;
    private final int size;

    @Getter
    private final List<Integer> path = new ArrayList<>();

    @Scale("size")
    public long getSize() {
        return size;
    }

    private final Long fillingFactor;

    @Factor("filling")
    public Long getFilling() {
        return fillingFactor;
    }


    public EulerCyclerMatrix(Matrix matrix) {
        this.matrix = matrix;
        this.size = matrix.getRowCount();

        int edgesCount = countEdges(matrix);
        if (edgesCount == 0) this.fillingFactor = 0L;
        else this.fillingFactor = (long) (((double) size / edgesCount) * 100L);
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


    private int getDeg(int n) {
        int c = 0;
        for (int i = 0; i < matrix.getRowCount(); i++)
            if (matrix.get(n, i) != 0)
                c++;


        return c;
    }

    @Exam
    public List<Integer> traceCycle() {
        var matCopy = matrix.clone();
        for (int i = 0; i < matCopy.getRowCount(); i++)
            if (getDeg(i) % 2 != 0)
                return Collections.emptyList();


        traceCycleTime = System.nanoTime();
        int curr = -1;
        Stack<Integer> bufforPath = new Stack<>();
        for (int i = 0; i < matCopy.getRowCount(); i++) {
            if (getDeg(i) != 0) {
                curr = i;
                break;
            }
        }

        if (curr == -1) {
            traceCycleTime = 0L;
            return  Collections.emptyList();
        }


        while (!bufforPath.isEmpty() || matCopy.getRow(curr).stream().mapToInt(Math::abs).sum() != 0) {
            if (matCopy.getRow(curr).stream().mapToInt(Math::abs).sum() == 0) {
                path.add(curr);
                curr = bufforPath.pop();

            } else for (int i = 0; i < matCopy.getRow(curr).size(); i++)
                    if (matCopy.get(curr, i) != 0) {
                        bufforPath.push(curr);
                        matCopy.set(curr, i, 0);
                        matCopy.set(i, curr, 0);
                        curr = i;
                        break;
                    }
        }


        for (int i = 0; i < matCopy.getRowCount(); i++)
            if (matCopy.getRow(curr).stream().mapToInt(Math::abs).sum() > 0) {
                traceCycleTime = 0L;
                return Collections.emptyList();
            }

        traceCycleTime = System.nanoTime() - traceCycleTime;
        return path;
    }

    public static Structure of(List<Integer> input) {
        return new EulerCyclerMatrix(MatrixGenerator.generateMatrix(MatrixGenerator.map(input)));
    }
}
