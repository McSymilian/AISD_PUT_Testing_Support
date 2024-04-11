package org.data_structures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class AVLTest extends DataStructuresTest {

    @Test
    void construct() {
        var avl = new AVL<>(generateDataSet());
        Assertions.assertEquals(10, avl.traceInOrder().size());
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
        avl.delete(50);
        var list = new ArrayList<>(getDataSet());
        list.removeFirst();
        Assertions.assertEquals(list.stream().sorted().toList(), avl.traceInOrder());
    }
}