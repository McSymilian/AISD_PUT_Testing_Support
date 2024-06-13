package org.data_structures.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {
    private final List<List<Integer>> content;

    public Matrix(List<List<Integer>> content) {
        this.content = content;
    }

    public Matrix(int size) {
        this(size, size);
    }

    public Matrix(int wight, int height) {
        content = new ArrayList<>(wight);
        for (int i = 0; i < wight; i++) {
            content.add(new ArrayList<>(height));
            for (int j = 0; j < height; j++) {
                content.get(i).add(null);
            }
        }
    }

    public Integer get(int row, int column) {
        if (content.get(row) == null || content.get(row).get(column) == null)
            return null;

        return content.get(row).get(column);
    }

    public List<Integer> getRow(int row) {
        return content.get(row).stream().toList();
    }

    public List<Integer> getColumn(int column) {
        List<Integer> columnList = new ArrayList<>();
        for (var row: content) {
            columnList.add(row.get(column));
        }
        return columnList;
    }

    public void set(int row, int column, Integer value) {
        content.get(row).set(column, value);
    }

    public int getRowCount() {
        return content.size();
    }

    public int getColumnCount() {
        return content.getFirst().size();
    }

    public Matrix clone() {

        return new Matrix(content.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList())
        );
    }

    public void removeRow(int row) {
        content.set(row, null);
    }

    public void removeColumn(int column) {
        for (var row: content) {
            if (row != null)
                row.set(column, null);
        }
    }

    public void removeRowAndColumn(int index) {
        removeRow(index);
        removeColumn(index);
    }

    public boolean isEmpty() {
        if (content.isEmpty())
            return true;

        for (var row: content)
            if (row != null && !row.isEmpty())
                return false;

        return true;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var row: content) {
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
