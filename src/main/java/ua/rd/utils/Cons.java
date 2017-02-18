package ua.rd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

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
        return foldRight(StreamOps.empty(), (a, lazyAcc) -> {
            R result = mapper.apply(a);
            return StreamOps.cons(() -> result, lazyAcc);
        });
    }

    @Override
    public Stream<A> append(Stream<A> other) {
        return foldRight(other, (a, lazyAcc) -> StreamOps.cons(() -> a, lazyAcc));
    }

    @Override
    public <R> Stream<R> flatMap(Function<A, Stream<R>> fr) {
        return foldRight(StreamOps.empty(), (a, lazyAcc) -> fr.apply(a).append(new DelayedCons<>(lazyAcc)));
    }

    @Override
    public Stream<A> filter(Predicate<A> predicate) {
        return foldRight(StreamOps.empty(), (a, lazyAcc) -> {
            if (!predicate.test(a)) {
                return StreamOps.cons(() -> a, lazyAcc);
            }
            return new DelayedCons<>(lazyAcc);
        });
    }

    @Override
    public Stream<A> take(int n) {
        if (n > 0) {
            return StreamOps.cons(value, () -> tail.get().take(n - 1));
        }
        return StreamOps.empty();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<A> toList() {
        List<A> result = new ArrayList<>();
        return foldLeft(result, (a, lazyAcc) -> {
            a.add(lazyAcc);
            return a;
        });
    }
}
