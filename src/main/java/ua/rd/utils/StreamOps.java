package ua.rd.utils;

import static ua.rd.utils.Stream.EMPTY_STREAM;

public final class StreamOps {

    private StreamOps() {
    }

    public static <A> Stream<A> of(A... as) {
        return of(0, as);
    }

    private static <A> Stream<A> of(int index, A... as) {
        if (as == null || as.length == 0 || index >= as.length || index < 0) {
            return empty();
        }
        return cons(() -> as[index], () -> of(index + 1, as));
    }

    public static <A> Stream<A> cons(Lazy<A> value, Lazy<Stream<A>> tail) {
        if (value == null) {
            return empty();
        }
        return new Cons<>(value, tail == null ? StreamOps::empty : tail);
    }

    public static <A> Stream<A> empty() {
        return (Stream<A>) EMPTY_STREAM;
    }

    public static <A> Stream<A> constant(A a) {
        return new Cons<>(() -> a, () -> constant(a));
    }

    public static Stream<Integer> range(int a, int b) {
        if (a >= b) {
            return empty();
        }
        return new Cons<>(() -> a, () -> range(a + 1, b));
    }
}
