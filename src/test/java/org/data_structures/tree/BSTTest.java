package org.data_structures.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BSTTest extends DataStructuresTest {
    @Test
    void add() {
        var data = getDataSet();

        var tree = new BST<Integer>(new ArrayList<>());
        for (var val: data) {
            tree.add(val);
        }

        assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void traceMin() {
        var data = getDataSet();

        var tree = new BST<>(data);

        assertEquals(
                List.of(6, 4, 2, 1),
                tree.traceMin()
        );

    }

    @Test
    void traceMax() {
        var data = getDataSet();

        var tree = new BST<>(data);

        assertEquals(
                List.of(6, 8, 9, 10, 12),
                tree.traceMax()
        );
    }

    @Test
    void traceInOrder() {
        var data = new ArrayList<>(getDataSet());

        var tree = new BST<>(new ArrayList<>(data));

        assertEquals(
                data.stream().sorted().toList(),
                tree.traceInOrder()
        );
    }

    @Test
    void tracePreOrder() {
        var data = getDataSet();

        var tree = new BST<>(data);

        assertEquals(
                List.of(6, 4, 2, 1, 5, 8, 7, 9, 10, 12),
                tree.tracePreOrder()
        );
    }

    @Test
    void clear() {
        //given
        var data = new ArrayList<>(getDataSet());

        var tree = new BST<>(data);
        tree.clear();

        //then
        data.clear();
        assertEquals(data, tree.traceInOrder());
    }

    @Test
    void printSubTree() {
        var data = getDataSet();

        var tree = new BST<>(data);

        tree.printSubTree(8);
    }

    @Test
    void deleteNode() {
        //given
        var data = new ArrayList<>(getDataSet());
        var toDel = new ArrayList<>(data.subList(5, 7));
        var tree = new BST<>(new ArrayList<>(data));

        toDel.forEach(tree::removeNode);
        toDel.forEach(data::remove);

        //then
        assertEquals(data.stream().sorted().toList(), tree.traceInOrder());
    }


    @Test
    void find() {
        var data = getDataSet();

        var tree = new BST<>(data);


        assertEquals(data.get(2), tree.find(data.get(2)).getVal());
    }

    @Test
    void balance() {
        var data = getDataSet().stream().sorted().toList();

        var treeBalanced = new BST<>(data);

        treeBalanced.balance();
        assertEquals(1, treeBalanced.traceMin().getLast());
        assertEquals(10, treeBalanced.traceInOrder().size());
        assertEquals(3, treeBalanced.traceMax().size());
        assertTrue(abs(treeBalanced.traceMax().size() - treeBalanced.traceMin().size()) <= 1);
    }


}