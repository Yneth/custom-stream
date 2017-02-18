package ua.rd.utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class DelayedCons<A> implements Stream<A> {
    private final Lazy<Stream<A>> lazyStream;

    DelayedCons(Lazy<Stream<A>> lazyStream) {
        this.lazyStream = lazyStream;
    }

    @Override
    public <R> R foldRight(R initial, BiFunction<A, Lazy<R>, R> function) {
        return lazyStream.get().foldRight(initial, function);
    }

    @Override
    public <R> R foldLeft(R initial, BiFunction<R, A, R> function) {
        return lazyStream.get().foldLeft(initial, function);
    }

    @Override
    public <R> Stream<R> map(Function<A, R> mapper) {
        return lazyStream.get().map(mapper);
    }

    @Override
    public Stream<A> append(Stream<A> other) {
        return lazyStream.get().append(other);
    }

    @Override
    public <R> Stream<R> flatMap(Function<A, Stream<R>> fr) {
        return lazyStream.get().flatMap(fr);
    }

    @Override
    public Stream<A> filter(Predicate<A> predicate) {
        return lazyStream.get().filter(predicate);
    }

    @Override
    public Stream<A> take(int n) {
        return lazyStream.get().take(n);
    }

    @Override
    public boolean isEmpty() {
        return lazyStream.get().isEmpty();
    }

    @Override
    public List<A> toList() {
        return lazyStream.get().toList();
    }
}
