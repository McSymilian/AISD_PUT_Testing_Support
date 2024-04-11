package org.data_structures.tree;

import org.data_structures.utility.Duration;
import org.data_structures.utility.Examined;
import org.data_structures.utility.Scale;

import java.util.ArrayList;
import java.util.List;

@Examined
public class AVL<T extends Comparable<T>> extends BST<T> {


    public static<T extends Comparable<T>> AVL<T> of(List<T> values) {
        return new AVL<>(new ArrayList<>(values));
    }

    @Scale("size")
    @Override
    public long getSize() {
        return super.getSize();
    }

    @Duration("creation_time")
    @Override
    public long getCreationTime() {
        return super.getCreationTime();
    }

    @Duration("trace_min_time")
    @Override
    public long getTraceMinTime() {
        return super.getTraceMinTime();
    }

    @Duration("trace_in_order")
    @Override
    public long getTraceInOrderTime() {
        return super.getTraceInOrderTime();
    }

    @Duration("balance_time")
    @Override
    public long getBalanceTime() {
        return super.getBalanceTime();
    }

    public AVL(List<T> values) {
        super(values, AVLTreeNode::of);

        var list = traceInOrder();
        clear();

        final var start = System.nanoTime();
        constructAVLBySplit(list);

        setCreationTime(System.nanoTime() - start);
    }

    private void constructAVLBySplit(List<T> values) {
        if (values.isEmpty())
            return;

        int middleIndex = values.size() / 2;
        this.add(values.get(middleIndex));
        if (middleIndex > 0)
            constructAVLBySplit(values.subList(0, middleIndex));

        if (values.size() > 2)
            constructAVLBySplit(values.subList(middleIndex + 1, values.size()));

    }

    @Override
    public void add(T value) {
        setHead(add((AVLTreeNode<T>) getHead(), value));
    }

    private AVLTreeNode<T> add(AVLTreeNode<T> node, T value) {
        if (node == null) {
            return AVLTreeNode.of(value);
        }

        if (value.compareTo(node.getVal()) < 0) {
            node.setChildLeft(add(node.getChildLeft(), value));
        } else if (value.compareTo(node.getVal()) > 0) {
            node.setChildRight(add(node.getChildRight(), value));
        } else {
            return node;
        }
        updateBalanceFactor(node);

        return balance(node);
    }

    @Override
    public void balance() {
        final var start = System.nanoTime();
        setHead(balanceRecursive((AVLTreeNode<T>) getHead()));
        setBalanceTime(System.nanoTime() - start);
    }

    private AVLTreeNode<T> balanceRecursive(AVLTreeNode<T> node) {
        if (node == null) {
            return null;
        }

        node.setChildLeft(balanceRecursive(node.getChildLeft()));

        node.setChildRight(balanceRecursive(node.getChildRight()));

        updateBalanceFactor(node);

        return balance(node);
    }

    private void updateBalanceFactor(AVLTreeNode<T> node) {
        int leftHeight = node.getChildLeft() == null ? -1 : node.getChildLeft().getBalanceFactor();
        int rightHeight = node.getChildRight() == null ? -1 : node.getChildRight().getBalanceFactor();
        node.setBalanceFactor(rightHeight - leftHeight);
    }

    private AVLTreeNode<T> balance(AVLTreeNode<T> node) {
        int leftBalanceFactor = node.getChildLeft() == null ? -1 : node.getChildLeft().getBalanceFactor();
        int rightBalanceFactor = node.getChildRight() == null ? -1 : node.getChildRight().getBalanceFactor();

        if (leftBalanceFactor < -1) {
            if (rightBalanceFactor > 0) {
                node.setChildLeft(leftRotate(node.getChildLeft()));
            }
            return rightRotate(node);
        } else if (leftBalanceFactor > 1) {
            if (node.getChildRight() != null && rightBalanceFactor < 0) {
                node.setChildRight(rightRotate(node.getChildRight()));
            }
            return leftRotate(node);
        }
        return node;
    }

    private AVLTreeNode<T> leftRotate(AVLTreeNode<T> node) {
        AVLTreeNode<T> pivot = node.getChildRight();
        if (pivot == null) {
            return node;
        }
        node.setChildRight(pivot.getChildLeft());
        pivot.setChildLeft(node);
        updateBalanceFactor(node);
        updateBalanceFactor(pivot);
        return pivot;
    }

    private AVLTreeNode<T> rightRotate(AVLTreeNode<T> node) {
        AVLTreeNode<T> pivot = node.getChildLeft();
        if (pivot == null) {
            return node;
        }
        node.setChildLeft(pivot.getChildRight());
        pivot.setChildRight(node);
        updateBalanceFactor(node);
        updateBalanceFactor(pivot);
        return pivot;
    }

    public void delete(T value) {
        setHead(deleteRecursive((AVLTreeNode<T>) getHead(), value));
    }

    private AVLTreeNode<T> deleteRecursive(AVLTreeNode<T> node, T value) {
        if (node == null) return null;

        if (value.compareTo(node.getVal()) < 0) {
            node.setChildLeft(deleteRecursive(node.getChildLeft(), value));
        } else if (value.compareTo(node.getVal()) > 0) {
            node.setChildRight(deleteRecursive(node.getChildRight(), value));
        } else {
            if (node.getChildLeft() == null || node.getChildRight() == null) {
                node = (node.getChildLeft() != null) ? node.getChildLeft() : node.getChildRight();
            } else {
                AVLTreeNode<T> successor = findMin(node.getChildRight());
                node.setVal(successor.getVal());
                node.setChildRight(deleteRecursive(node.getChildRight(), successor.getVal()));
            }
        }

        if (node == null) return null;

        updateBalanceFactor(node);

        return balance(node);
    }
    private AVLTreeNode<T> findMin(AVLTreeNode<T> node) {
        while (node.getChildLeft() != null) {
            node = node.getChildLeft();
        }
        return node;
    }

}
