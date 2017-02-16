package ua.rd.utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Stream<A> {
    Empty EMPTY_STREAM = new Empty();

    <R> R foldRight(R initial, BiFunction<A, Lazy<R>, R> function);

    <R> R foldLeft(R initial, BiFunction<R, A, R> function);

    <R> Stream<R> map(Function<A, R> mapper);

    Stream<A> append(Stream<A> other);

    <R> Stream<R> flatMap(Function<A, Stream<R>> fr);

    Stream<A> filter(Function<A, Boolean> mapper);

    Stream<A> take(int n);

    boolean isEmpty();

    List<A> toList();

    static <B> Stream<B> cons(Lazy<B> value, Lazy<Stream<B>> tail) {
        return new Cons<>(value, tail);
    }

    static <B> Stream<B> empty() {
        return (Stream<B>) EMPTY_STREAM;
    }

    static <B> Stream<B> constant(B b) {
        return new Cons<>(() -> b, () -> constant(b));
    }

    static Stream<Integer> range(int a, int b) {
        if (a >= b) {
            return empty();
        }
        return new Cons<>(() -> a, () -> range(a + 1, b));
    }
}

