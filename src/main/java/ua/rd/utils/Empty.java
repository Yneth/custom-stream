package ua.rd.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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
