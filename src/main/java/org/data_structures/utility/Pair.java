package org.data_structures.utility;

import java.util.Objects;

public record Pair<T, K>(T first, K second) implements Comparable<Pair<T, K>> {
    public static <T, K> Pair<T, K> of(T first, K second) {
        return new Pair<>(first, second);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pair<?, ?> pair)) return false;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public int compareTo(Pair<T, K> o) {
        return Integer.compare(hashCode(), o.hashCode());
    }
}
