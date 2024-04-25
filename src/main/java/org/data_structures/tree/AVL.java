package org.data_structures.tree;

import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.annotation.Scale;
import org.data_structures.utility.Structure;

import java.util.ArrayList;
import java.util.List;

@Examined
public class AVL<T extends Comparable<T>> extends Structure {

    public static<T extends Comparable<T>> AVL<T> of(List<T> values) {
        return new AVL<>(new ArrayList<>(values));
    }

    /**
     * @param values list of values to insert
     */
    public AVL(List<T> values) {
        this.bst = new BST<>(values);
        var sortedList = bst.traceInOrder();
        bst.clear();

        var time = System.nanoTime();
        buildAVL(sortedList);
        bst.setCreationTime(System.nanoTime() - time);

    }

    private void buildAVL(List<T> values) {
        buildAVL(values, 0, values.size());
    }

    private void buildAVL(List<T> values, int start, int end) {
        if (values.isEmpty()) {
            return;
        }
        int mid = (start + end) / 2;
        bst.add(values.get(mid));

        if (start != mid)
            buildAVL(values, start, mid);
        if (mid + 1 != end)
            buildAVL(values, mid + 1, end);
    }

    @Scale("size")
    public long getSize() {
        return bst.getSize();
    }

    @Duration("creation_time")
    public long getCreationTime() {
        return bst.getCreationTime();
    }

    @Duration("trace_min_time")
    public long getTraceMinTime() {
        return bst.getTraceMinTime();
    }

    @Duration("trace_in_order")
    public long getTraceInOrderTime() {
        return bst.getTraceInOrderTime();
    }

    private final BST<T> bst;

    @Exam
    public List<T> traceMin() {
        return bst.traceMin();
    }

    public List<T> traceMax() {
        return bst.traceMin();
    }

    public void removeNode(T key) {
        bst.removeNode(key);
        bst.balance();
    }

    @Exam
    public List<T> traceInOrder() {
        return bst.traceInOrder();
    }

    public List<T> tracePreOrder() {
        return bst.tracePreOrder();
    }

    public void clear() {
        bst.clear();
    }

    public void printSubTree(T key) {
        bst.printSubTree(key);
    }

    public void add(T val) {
        bst.add(val);
    }

}
