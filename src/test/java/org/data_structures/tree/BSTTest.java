package org.data_structures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BSTTest extends DataStructuresTest {
    @Test
    void add() {
        var data = generateDataSet();

        var tree = new BST<Integer>(new ArrayList<>());
        for (var val: data) {
            tree.add(val);
        }

        Assertions.assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void traceMin() {
        var data = getDataSet();

        var tree = new BST<>(data);

        Assertions.assertEquals(
                List.of(50, 30, 20),
                tree.traceMin()
        );

    }

    @Test
    void traceMax() {
        var data = getDataSet();

        var tree = new BST<>(data);

        Assertions.assertEquals(
                List.of(50, 70, 80),
                tree.traceMax()
        );
    }

    @Test
    void traceInOrder() {
        var data = getDataSet();

        var tree = new BST<>(data);

        Assertions.assertEquals(
                List.of(20, 30, 40, 50, 60, 70, 80),
                tree.traceInOrder()
        );
    }

    @Test
    void tracePreOrder() {
        var data = getDataSet();

        var tree = new BST<>(data);

        Assertions.assertEquals(
                List.of(50, 30, 20, 40, 70, 60, 80),
                tree.tracePreOrder()
        );
    }

    @Test
    void clear() {
        var data = getDataSet();

        var tree = new BST<>(data);
        tree.clear();

        tree.traceInOrder();
    }

    @Test
    void printSubTree() {
        var data = getDataSet();

        var tree = new BST<>(data);

        tree.printSubTree(30);
    }

    @Test
    void deleteNode() {
        var data = getDataSet();

        var tree = new BST<>(data);

        tree.deleteNode(50);
        tree.tracePreOrder();
    }

    @Test
    void find() {
        var data = getDataSet();

        var tree = new BST<>(data);


        Assertions.assertEquals(data.get(2), tree.find(data.get(2)).getVal());
    }

    @Test
    void balance() {
        var data = getDataSet().stream().sorted().toList();

        var treeBalanced = new BST<>(data);

        treeBalanced.balance();
        Assertions.assertEquals(20, treeBalanced.traceMin().getLast());
        Assertions.assertEquals(7, treeBalanced.traceInOrder().size());
        Assertions.assertEquals(3, treeBalanced.traceMax().size());
        Assertions.assertEquals(treeBalanced.traceMin().size(), treeBalanced.traceMax().size());
    }
}