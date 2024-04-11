package org.data_structures.tree;

import java.util.List;

public interface BSTConstructor {
    <T extends Comparable<T>> BST<T> construct(List<T> values);
}
