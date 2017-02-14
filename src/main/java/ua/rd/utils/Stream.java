package ua.rd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface Stream<A> {
    Empty EMPTY = new Empty();

    <R> R foldRight(R initial, AccumulatorRight<A, R> function);

    <R> R foldLeft(R initial, AccumulatorLeft<A, R> function);

    <R> Stream<R> map(Function<A, R> mapper);

    Stream<A> filter(Function<A, Boolean> mapper);

    Stream<A> take(int n);

    boolean isEmpty();

    List<A> toList();

    static <B> Stream<B> cons(Lazy<B> value, Lazy<Stream<B>> tail) {
        return new Cons<>(value, tail);
    }

    static <B> Stream<B> none() {
        return (Stream<B>) EMPTY;
    }

    static <B> Stream<B> constant(B b) {
        return new Cons<>(() -> b, () -> constant(b));
    }

    static Stream<Integer> range(int a, int b) {
        if (a >= b) {
            return none();
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
    public <R> R foldRight(R initial, AccumulatorRight<A, R> function) {
        return function.accumulate(value.get(), () -> tail.get().foldRight(initial, function));
    }

    @Override
    public <R> R foldLeft(R initial, AccumulatorLeft<A, R> function) {
        return tail.get().foldLeft(function.accumulate(initial, value.get()), function);
    }

    @Override
    public <R> Stream<R> map(Function<A, R> mapper) {
        return foldRight(Stream.none(), (a, s) -> Stream.cons(() -> mapper.apply(a), s));
    }

    @Override
    public Stream<A> filter(Function<A, Boolean> filter) {
        return foldRight(Stream.none(), (a, s) -> {
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
        return Stream.none();
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
    public <R> R foldRight(R initial, AccumulatorRight<Object, R> function) {
        return initial;
    }

    @Override
    public <R> R foldLeft(R initial, AccumulatorLeft<Object, R> function) {
        return initial;
    }

    @Override
    public <R> Stream<R> map(Function<Object, R> mapper) {
        return Stream.none();
    }

    @Override
    public Stream<Object> filter(Function<Object, Boolean> mapper) {
        return Stream.none();
    }

    @Override
    public Stream<Object> take(int n) {
        return Stream.none();
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