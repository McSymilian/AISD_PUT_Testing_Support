package org.data_structures.tree;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.Pair;
import org.data_structures.utility.annotation.Scale;
import org.data_structures.utility.Structure;

import java.util.ArrayList;
import java.util.List;


@Examined
public class BST<T extends Comparable<T>> extends Structure {
    public static<T extends Comparable<T>> BST<T> of(List<T> values) {
        return new BST<>(new ArrayList<>(values));
    }

    @Getter(AccessLevel.PROTECTED)
    private TreeNode<T> head;

    protected void setHead(TreeNode<T> newHead) {
        if (newHead != null)
            newHead.setParent(null);

        head = newHead;
    }

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
    @Exam
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


    private void traceInOrder(TreeNode<T> node, List<T> treeAsList) {
        if (node != null) {
            traceInOrder(node.getChildLeft(), treeAsList);

            treeAsList.add(node.getVal());

            traceInOrder(node.getChildRight(), treeAsList);
        }

    }

    @Setter
    protected long traceInOrderTime = 0;

    @Exam
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

    private TreeNode<T> getClosestChild(TreeNode<T> node) {
        TreeNode<T> left;
        TreeNode<T> right;
        TreeNode<T> tmp = node.getChildRight();

        do {
            right = tmp;
            if (tmp != null)
                tmp = tmp.getChildLeft();
        } while (tmp != null);

        tmp = node.getChildLeft();
        do {
            left = tmp;
            if (tmp != null)
                tmp = tmp.getChildRight();
        } while (tmp != null);


        if (left == null) return right;
        else return left;

    }

    private TreeNode<T> removeNode(TreeNode<T> node) {
        boolean hasLeft = node.hasLeftChild();
        boolean hasRight = node.hasRightChild();
        boolean hasParent = node.hasParent();

        if (hasLeft && hasRight) {
            var closestChild = getClosestChild(node);
            removeNode(closestChild);

            closestChild.setChildRight(node.getChildRight());
            closestChild.setChildLeft(node.getChildLeft());

            if (hasParent) {
                if (node.isChildLeft())
                    node.getParent().setChildLeft(closestChild);
                else
                    node.getParent().setChildRight(closestChild);

                return closestChild.getParent();
            }
            else {
                setHead(closestChild);
                return getHead();
            }
        }
        else if (hasLeft) {
            if (hasParent) {
                var parent = node.getParent();

                if (node.isChildLeft())
                    parent.setChildLeft(node.getChildLeft());
                else
                    parent.setChildRight(node.getChildLeft());

                return parent;
            }
            else {
                setHead(node.getChildLeft());
                return getHead();
            }

        }
        else if (hasRight) {
            if (hasParent) {
                var parent = node.getParent();

                if (node.isChildLeft())
                    parent.setChildLeft(node.getChildRight());
                else
                    parent.setChildRight(node.getChildRight());

                return parent;
            }
            else {
                setHead(node.getChildRight());
                return getHead();
            }
        }
        else {
            var parent = node.getParent();
            if (hasParent) {
                if (node.isChildLeft())
                    parent.setChildLeft(null);
                else
                    parent.setChildRight(null);
            }
            else setHead(null);

            return parent;

        }
    }

    /**
     *
     * @param node pivot of rotation
     * @return pair of node and rotator
     */
    Pair<TreeNode<T>, TreeNode<T>> RRRotation(TreeNode<T> node) {
        if (node == null) return null;
        if (node == getHead()) return Pair.of(node, null);

        var rotator = node.getParent();
        var rotatorParent = rotator.getParent();

        if (rotatorParent != null) {
            if (rotator.isChildLeft())
                rotatorParent.setChildLeft(node);
            else
                rotatorParent.setChildRight(node);
        }
        else {
            setHead(node);
        }

        rotator.setChildLeft(node.getChildRight());
        node.setChildRight(rotator);

        return Pair.of(node, rotator);
    }

    /**
     *
     * @param node pivot of rotation
     * @return pair of node and rotator
     */
    Pair<TreeNode<T>, TreeNode<T>> LLRotation(TreeNode<T> node) {
        if (node == null) return null;
        if (node == getHead()) return Pair.of(node, null);

        var rotator = node.getParent();
        var rotatorParent = rotator.getParent();

        if (rotatorParent != null) {
            if (rotator.isChildLeft())
                rotatorParent.setChildLeft(node);
            else
                rotatorParent.setChildRight(node);
        }
        else {
            setHead(node);
        }

        rotator.setChildRight(node.getChildLeft());
        node.setChildLeft(rotator);


        return Pair.of(node, rotator);
    }

    TreeNode<T> LRRotation(TreeNode<T> node) {
        LLRotation(node);
        RRRotation(node);

        return node;
    }

    TreeNode<T> RLRotation(TreeNode<T> node) {
        RRRotation(node);
        LLRotation(node);

        return node;
    }


    private int toVine(){
        var node = getHead();
        while (node != null) {
            while (node != null && !node.hasLeftChild())
                node = node.getChildRight();


            if (node  != null) {
                RRRotation(node.getChildLeft());
                node = getHead();
            }
        }

        return traceInOrder().size();
    }


    private void balanceBST()
    {
        int count = toVine();
        int h = (int)(Math.log(count + 1D) / Math.log(2));
        int m = (int)Math.pow(2, h) - 1;

        var point = getHead();
        for (int i = 0; i < count - m; i++) {
            point = point.getChildRight();

            LLRotation(point);
            point = point.getChildRight();
        }
        point=getHead();
        m /= 2;
        while (m > 0) {
            for (int i = 0; i < m; i++) {
                point = point.getChildRight();

                LLRotation(point);
                point = point.getChildRight();
            }
            point = getHead();
            m /= 2;
        }
    }

    @Setter
    protected long balanceTime = 0;

    @Exam
    public void balance() {
        final var start = System.nanoTime();
        balanceBST();
        balanceTime = System.nanoTime() - start;
    }

    public void removeNode(T key) {
        var node = find(key);
        removeNode(node);
    }

}
