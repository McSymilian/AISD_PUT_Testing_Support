package org.data_structures.utility;

import lombok.Data;

@Data
public class Pair<T, K> {
    private final T first;

    private final K second;

    public static <T, K> Pair<T, K> of(T first, K second) {
        return new Pair<>(first, second);
    }
}
