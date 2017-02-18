package ua.rd.utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<A> {
    Empty EMPTY_STREAM = new Empty();

    <R> R foldRight(R initial, BiFunction<A, Lazy<R>, R> function);

    <R> R foldLeft(R initial, BiFunction<R, A, R> function);

    <R> Stream<R> map(Function<A, R> mapper);

    Stream<A> append(Stream<A> other);

    <R> Stream<R> flatMap(Function<A, Stream<R>> fr);

    Stream<A> filter(Predicate<A> mapper);

    Stream<A> take(int n);

    boolean isEmpty();

    List<A> toList();
}

