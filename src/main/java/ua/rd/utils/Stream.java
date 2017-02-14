package ua.rd.utils;

import java.util.ArrayList;
import java.util.Collections;
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

class Cons<A> implements Stream<A> {
    private final Lazy<A> value;
    private final Lazy<Stream<A>> tail;

    Cons(Lazy<A> value, Lazy<Stream<A>> tail) {
        this.value = value;
        this.tail = tail;
    }

    @Override
    public <R> R foldRight(R initial, BiFunction<A, Lazy<R>, R> function) {
        return function.apply(value.get(), () -> tail.get().foldRight(initial, function));
    }

    @Override
    public <R> R foldLeft(R initial, BiFunction<R, A, R> function) {
        return tail.get().foldLeft(function.apply(initial, value.get()), function);
    }

    @Override
    public <R> Stream<R> map(Function<A, R> mapper) {
        return foldRight(Stream.empty(), (a, s) -> Stream.cons(() -> mapper.apply(a), s));
    }

    @Override
    public Stream<A> append(Stream<A> other) {
        return foldRight(other, (a, lazyR) -> Stream.cons(() -> a, lazyR));
    }

    @Override
    public <R> Stream<R> flatMap(Function<A, Stream<R>> fr) {
        return foldRight(Stream.empty(), (a, lazyR) -> fr.apply(a).append(lazyR.get()));
    }

    @Override
    public Stream<A> filter(Function<A, Boolean> filter) {
        return foldRight(Stream.empty(), (a, s) -> {
            if (!filter.apply(a)) {
                return Stream.cons(() -> a, s);
            }
            return s.get();
        });
    }

    @Override
    public Stream<A> take(int n) {
        if (n > 0)
            return Stream.cons(value, () -> tail.get().take(n - 1));
        return Stream.empty();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<A> toList() {
        List<A> result = new ArrayList<>();
        return foldLeft(result, (a, s) -> {
            a.add(s);
            return a;
        });
    }
}

class Empty implements Stream<Object> {

    @Override
    public <R> R foldRight(R initial, BiFunction<Object, Lazy<R>, R> function) {
        return initial;
    }

    @Override
    public <R> R foldLeft(R initial, BiFunction<R, Object, R> function) {
        return initial;
    }

    @Override
    public <R> Stream<R> map(Function<Object, R> mapper) {
        return Stream.empty();
    }

    @Override
    public Stream<Object> append(Stream<Object> other) {
        return Stream.empty();
    }

    @Override
    public <R> Stream<R> flatMap(Function<Object, Stream<R>> fr) {
        return Stream.empty();
    }

    @Override
    public Stream<Object> filter(Function<Object, Boolean> mapper) {
        return Stream.empty();
    }

    @Override
    public Stream<Object> take(int n) {
        return Stream.empty();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<Object> toList() {
        return Collections.emptyList();
    }
}