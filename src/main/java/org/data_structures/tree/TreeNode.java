package org.data_structures.tree;

import lombok.Data;

import java.util.Objects;

@Data
public class TreeNode<T> {
    private T val;
    private TreeNode<T> parent;
    private TreeNode<T> childLeft;
    private TreeNode<T> childRight;

    public TreeNode(T val) {
        this.val = val;
    }

    public static <T> TreeNode<T> of(T val) {
        return new TreeNode<>(val);
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public boolean isChildLeft() {
        if (!hasParent()) return false;

        return getParent().getChildLeft() == this;
    }

    public boolean isChildRight() {
        if (!hasParent()) return false;

        return getParent().getChildRight() == this;
    }

    public boolean hasLeftChild() {
        return getChildLeft() != null;
    }

    public boolean hasRightChild() {
        return getChildRight() != null;
    }

    public void setChildLeft(TreeNode<T> childLeft) {
        this.childLeft = childLeft;

        if (childLeft != null)
            childLeft.setParent(this);
    }

    public void setChildRight(TreeNode<T> childRight) {
        this.childRight = childRight;

        if (childRight != null)
            childRight.setParent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode<?> treeNode)) return false;
        return Objects.equals(val, treeNode.val) && Objects.equals(parent, treeNode.parent) && Objects.equals(childLeft, treeNode.childLeft) && Objects.equals(childRight, treeNode.childRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, parent, childLeft, childRight);
    }

}
