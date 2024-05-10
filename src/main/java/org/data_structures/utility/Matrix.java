package org.data_structures.utility;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private final List<List<Integer>> matrix;

    public Matrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    public Matrix(int size) {
        this(size, size);
    }

    public Matrix(int wight, int height) {
        matrix = new ArrayList<>(wight);
        for (int i = 0; i < wight; i++) {
            matrix.add(new ArrayList<>(height));
            for (int j = 0; j < height; j++) {
                matrix.get(i).add(null);
            }
        }
    }

    public Integer get(int row, int column) {
        if (matrix.get(row) == null || matrix.get(row).get(column) == null)
            return null;

        return matrix.get(row).get(column);
    }

    public void set(int row, int column, Integer value) {
        matrix.get(row).set(column, value);
    }

    public int getRowCount() {
        return matrix.size();
    }

    public int getColumnCount() {
        return matrix.getFirst().size();
    }

    public Matrix copy() {
        List<List<Integer>> copy = new ArrayList<>(getRowCount());
        for (var row: matrix) {
            copy.add(new ArrayList<>(row));
        }
        return new Matrix(copy);
    }

    public void removeRow(int row) {
        matrix.set(row, null);
    }

    public void removeColumn(int column) {
        for (var row: matrix) {
            if (row != null)
                row.set(column, null);
        }
    }

    public void removeRowAndColumn(int index) {
        removeRow(index);
        removeColumn(index);
    }

    public boolean isEmpty() {
        if (matrix.isEmpty())
            return true;

        for (var row: matrix)
            if (row != null && !row.isEmpty())
                return false;

        return true;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var row: matrix) {
            if (row == null) {
                sb.append("null\n");
                continue;
            }

            for (var value: row) {
                sb.append(value).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<Integer> getVertices() {
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            vertices.add(i);
        }
        return vertices;
    }

    public Integer[][] toArray() {
        Integer[][] array = new Integer[getRowCount()][getColumnCount()];
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                array[i][j] = get(i, j);
            }
        }
        return array;
    }

}
