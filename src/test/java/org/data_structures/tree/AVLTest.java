package org.data_structures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AVLTest extends DataStructuresTest {

    @Test
    void construct() {
        var data = getDataSet();
        var avl = new AVL<>(data);
        Assertions.assertEquals(data.stream().sorted().toList(), avl.traceInOrder());
    }

    @Test
    void add() {
        var data = getDataSet();

        var tree = new AVL<Integer>(new ArrayList<>());
        for (var val: data) {
            tree.add(val);
        }

        Assertions.assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void delete() {
        var avl = new AVL<>(getDataSet());
        avl.removeNode(4);
        var list = new ArrayList<>(getDataSet());
        list.removeFirst();
        Assertions.assertEquals(List.of(1, 2, 5, 6, 7, 8, 9, 10, 12), avl.traceInOrder());
    }

    @Test
    void traceInOrder() {
        var data = new ArrayList<>(getDataSet());

        var tree = new AVL<>(new ArrayList<>(data));

        assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void traceMin() {
        var data = new ArrayList<>(getDataSet());

        var tree = new AVL<>(new ArrayList<>(data));

        assertEquals(
                data.stream().sorted().toList().getFirst(),
                tree.traceMin().getLast()
        );

    }
}