package org.data_structures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AVLTest extends DataStructuresTest {

    @Test
    void construct() {
        var data = generateDataSet();
        var avl = new AVL<>(data);
        Assertions.assertEquals(data.stream().sorted().toList(), avl.traceInOrder());
    }

    @Test
    void add() {
        var data = generateDataSet();

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
        avl.removeNode(50);
        var list = new ArrayList<>(getDataSet());
        list.removeFirst();
        Assertions.assertEquals(list.stream().sorted().toList(), avl.traceInOrder());
    }

    @Test
    void traceInOrder() {
        var data = new ArrayList<>(generateDataSet(0x800000));

        var tree = new AVL<>(new ArrayList<>(data));

        assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void traceMin() {
        var data = new ArrayList<>(generateDataSet());

        var tree = new AVL<>(new ArrayList<>(data));

        assertEquals(
                data.stream().sorted().toList().getFirst(),
                tree.traceMin().getLast()
        );

    }
}