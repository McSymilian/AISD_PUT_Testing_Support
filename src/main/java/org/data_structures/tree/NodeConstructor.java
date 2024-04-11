package org.data_structures.tree;

public interface NodeConstructor {
    <T> TreeNode<T> create(T val);
}
