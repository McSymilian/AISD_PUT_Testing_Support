package org.data_structures.tree;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.data_structures.utility.Duration;
import org.data_structures.utility.Examined;
import org.data_structures.utility.Measurement;
import org.data_structures.utility.Pair;
import org.data_structures.utility.Scale;

import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;


@Examined
public class BST<T extends Comparable<T>> {
    public static<T extends Comparable<T>> BST<T> of(List<T> values) {
        return new BST<>(new ArrayList<>(values));
    }

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private TreeNode<T> head;



    @Scale("size")
    public long getSize() {
        return traceInOrder().size();
    }

    @Duration("creation_time")
    public long getCreationTime() {
        return creationTime;
    }

    @Duration("find_min_time")
    public long getTraceMinTime() {
        return traceMinTime;
    }

    @Duration("trace_inorder_time")
    public long getTraceInOrderTime() {
        return traceInOrderTime;
    }

    @Duration("balance_time")
    public long getBalanceTime() {
        return balanceTime;
    }

    @Setter
    protected long creationTime = 0;
    public BST(List<T> values) {
        this(values, TreeNode::of);
    }

    public BST(List<T> values, NodeConstructor nodeConstructor) {
        setNodeConstructor(nodeConstructor);
        final var start = System.nanoTime();
        for (var val: values)
            add(val);
        creationTime = System.nanoTime() - start;
    }

    public void add(T value) {
        if (getHead() == null) {
            var tmp = nodeConstructor.create(value);
            setHead(tmp);
        }
        else {
            add(getHead(), value);
        }
    }

    @Setter(AccessLevel.PROTECTED)
    protected NodeConstructor nodeConstructor;
    private TreeNode<T> add(TreeNode<T> node, T value) {
        if (node.getVal().compareTo(value) > 0) {
            if (node.hasLeftChild())
                return add(node.getChildLeft(), value);
            else {
                TreeNode<T> treeNode = nodeConstructor.create(value);
                node.setChildLeft(treeNode);
                return treeNode;
            }
        }
        else
            if (node.hasRightChild())
                return add(node.getChildRight(), value);
            else {
                TreeNode<T> treeNode = nodeConstructor.create(value);
                node.setChildRight(treeNode);
                return treeNode;
            }
    }

    @Setter
    protected long traceMinTime = 0;

    /**
     * Values are added to the list in chronological order <br> Head -> Minimal value
     * @return values at trace to minimum value in tree
     */
    public List<T> traceMin() {
        List<T> ans = new ArrayList<>();
        var pointer = getHead();
        if (pointer == null)
            return ans;

        final var start = System.nanoTime();
        while (pointer.hasLeftChild()) {
            ans.add(pointer.getVal());
            pointer = pointer.getChildLeft();
        }

        ans.add(pointer.getVal());
        traceMinTime = System.nanoTime() - start;
        return ans;
    }

    /**
     * Values are added to the list in chronological order <br> Head -> Maximum value
     * @return values at trace to maximum value in tree
     */
    public List<T> traceMax() {
        List<T> ans = new ArrayList<>();
        var pointer = getHead();
        if (pointer == null)
            return ans;

        while (pointer.hasRightChild()) {
            ans.add(pointer.getVal());
            pointer = pointer.getChildRight();
        }

        ans.add(pointer.getVal());

        return ans;
    }

    protected TreeNode<T> find(TreeNode<T> node, T value) {
        if (node != null) {
            return switch ((int) Math.signum(node.getVal().compareTo(value))) {
                case 1 -> find(node.getChildLeft(), value);
                case -1 -> find(node.getChildRight(), value);
                default -> node;
            };
        }
        return null;
    }

    public TreeNode<T> find(T value) {
        return find(getHead(), value);
    }

    private void traceInOrder(TreeNode<T> node, List<T> list) {
        if (node != null) {
            traceInOrder(node.getChildLeft(), list);

            list.add(node.getVal());

            traceInOrder(node.getChildRight(), list);
        }
    }

    @Setter
    protected long traceInOrderTime = 0;
    public List<T> traceInOrder() {
        List<T> treeAsList = new ArrayList<>();

        final var start = System.nanoTime();
        traceInOrder(getHead(), treeAsList);
        traceInOrderTime = System.nanoTime() - start;

        return treeAsList;
    }

    private void tracePreOrder(TreeNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getVal());

            tracePreOrder(node.getChildLeft(), list);
            tracePreOrder(node.getChildRight(), list);
        }
    }

    public List<T> tracePreOrder() {
        List<T> treeAsList = new ArrayList<>();
        tracePreOrder(getHead(), treeAsList);

        return treeAsList;
    }

    private void clear(TreeNode<T> node) {
        if (node != null) {
            clear(node.getChildLeft());
            clear(node.getChildRight());

            node.setChildRight(null);
            node.setChildRight(null);

            if (node.equals(getHead()))
                setHead(null);
        }
    }

    public void clear() {
        clear(getHead());
    }

    public void printSubTree(T node) {
        List<T> treeAsList = new ArrayList<>();
        tracePreOrder(find(node), treeAsList);

        System.out.println(treeAsList);
    }

    private TreeNode<T> removeNode(TreeNode<T> node) {
        if (getHead() == node) {
            setHead(null);
            return getHead();
        }

        var parent = node.getParent();

        if (parent.getChildLeft() == node)
            parent.setChildLeft(null);
        else
            parent.setChildRight(null);
        return parent;
    }

    private int toVine(TreeNode<T> grand){
        int count = 0;
        var tmp = grand.getChildRight();

        while (tmp != null) {
            if (tmp.hasLeftChild()) {
                var rotationResult = rightRotation(tmp, grand);
                tmp = rotationResult.getFirst();
                grand = rotationResult.getSecond();
            }
            else {
                count++;
                grand = tmp;
                tmp = tmp.getChildRight();
            }
        }

        return count;
    }

    /**
     *
     * @param node pivot of rotation
     * @param rotator node to rotated
     * @return pair of node and rotator
     */
    protected Pair<TreeNode<T>, TreeNode<T>> rightRotation(TreeNode<T> node, TreeNode<T> rotator) {
        var oldTmp = node;
        node = node.getChildLeft();
        oldTmp.setChildLeft(node.getChildRight());
        node.setChildRight(oldTmp);
        rotator.setChildRight(node);

        return Pair.of(node, rotator);
    }

    private void compress(TreeNode<T> grand, int m) {
        var tmp = grand.getChildRight();

        for (int i = 0; i < m; i++) {
            var rotationResult = leftRotation(tmp, grand);
            tmp = rotationResult.getFirst();
            grand = rotationResult.getSecond();
        }
    }

    /**
     *
     * @param node pivot of rotation
     * @param rotator node to rotated
     * @return pair of node and rotator
     */
    protected Pair<TreeNode<T>, TreeNode<T>> leftRotation(TreeNode<T> node, TreeNode<T> rotator) {
        var oldTmp = node;
        node = node.getChildRight();
        rotator.setChildRight(node);
        oldTmp.setChildRight(node.getChildLeft());
        node.setChildLeft(oldTmp);
        rotator = node;
        node = node.getChildRight();

        return Pair.of(node, rotator);
    }

    private TreeNode<T> balanceBST(TreeNode<T> root)
    {
        var grand = new TreeNode<T>(null);

        grand.setChildRight(root);

        int count = toVine(grand);
        int h = (int)(Math.log(count + 1D) / Math.log(2));
        int m = (int)Math.pow(2, h) - 1;

        compress(grand, count - m);

        for (m = m / 2; m > 0; m /= 2)
            compress(grand, m);

        return grand.getChildRight();
    }

    @Setter
    protected long balanceTime = 0;
    public void balance() {
        final var start = System.nanoTime();
        setHead(balanceBST(getHead()));
        balanceTime = System.nanoTime() - start;
    }

    public void deleteNode(T key) {
        var node = find(key);
        if (!node.hasRightChild() && !node.hasLeftChild())
            removeNode(node);
        else if (!node.hasLeftChild()){
            if(node.getParent() == null) {
                setHead(node.getChildRight());
                getHead().setParent(null);
                return;
            }
            else if (node.getParent().getVal().compareTo(node.getVal()) > 0)
                node.getParent().setChildLeft(node.getChildRight());
            else
                node.getParent().setChildRight(node.getChildRight());
            removeNode(node);
        } else if (!node.hasRightChild()) {
            if(node.getParent() == null) {
                setHead(node.getChildLeft());
                getHead().setParent(null);
                return;
            }
            else if (node.getParent().getVal().compareTo(node.getVal()) > 0)
                node.getParent().setChildLeft(node.getChildLeft());
            else
                node.getParent().setChildRight(node.getChildLeft());
            removeNode(node);
        }
        else {
            var succ = node.getChildRight();

            while (succ.hasLeftChild())
                succ = succ.getChildLeft();


            var succParent = succ.getParent();
            if (succParent != node)
                succParent.setChildLeft(succ.getChildRight());
            else
                succParent.setChildRight(succ.getChildRight());

            node.setVal(succ.getVal());
        }
    }
}
