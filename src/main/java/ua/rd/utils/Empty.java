package ua.rd.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class Empty implements Stream<Object> {

    Empty() {
    }

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
        return StreamOps.empty();
    }

    @Override
    public Stream<Object> append(Stream<Object> other) {
        return StreamOps.empty();
    }

    @Override
    public <R> Stream<R> flatMap(Function<Object, Stream<R>> fr) {
        return StreamOps.empty();
    }

    @Override
    public Stream<Object> filter(Predicate<Object> predicate) {
        return StreamOps.empty();
    }

    @Override
    public Stream<Object> take(int n) {
        return StreamOps.empty();
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
