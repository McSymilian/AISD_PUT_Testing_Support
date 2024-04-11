package org.data_structures.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Setter
@Getter
public class AVLTreeNode<T> extends TreeNode<T> {

    public static <T> AVLTreeNode<T> of(T val) {
        return new AVLTreeNode<>(val);
    }
    public AVLTreeNode(T val) {
        super(val);
    }

//    public void setChildLeft(AVLTreeNode<T> childLeft) {
//        super.setChildLeft(childLeft);
//    }

//    public void setChildRight(AVLTreeNode<T> childRight) {
//        super.setChildRight(childRight);
//    }

    private int balanceFactor = 0;


    @Override
    public AVLTreeNode<T> getParent() {
        return (AVLTreeNode<T>) super.getParent();
    }

    @Override
    public AVLTreeNode<T> getChildLeft() {
        return (AVLTreeNode<T>) super.getChildLeft();
    }

    @Override
    public AVLTreeNode<T> getChildRight() {
        return (AVLTreeNode<T>) super.getChildRight();
    }

//    public void setParent(AVLTreeNode<T> parent) {
//        super.setParent(parent);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AVLTreeNode<?> that)) return false;
        if (!super.equals(o)) return false;
        return balanceFactor == that.balanceFactor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), balanceFactor);
    }
}
