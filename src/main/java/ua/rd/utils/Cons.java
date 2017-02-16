package ua.rd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        return foldRight(Stream.empty(), (a, lazyAcc) -> Stream.cons(() -> mapper.apply(a), lazyAcc));
    }

    @Override
    public Stream<A> append(Stream<A> other) {
        return foldRight(other, (a, lazyAcc) -> Stream.cons(() -> a, lazyAcc));
    }

    @Override
    public <R> Stream<R> flatMap(Function<A, Stream<R>> fr) {
        return foldRight(Stream.empty(), (a, lazyR) -> fr.apply(a).append(lazyR.get()));
    }

    @Override
    public Stream<A> filter(Function<A, Boolean> filter) {
        return foldRight(Stream.empty(), (a, lazyAcc) -> {
            if (!filter.apply(a)) {
                return Stream.cons(() -> a, lazyAcc);
            }
            return lazyAcc.get();
        });
    }

    @Override
    public Stream<A> take(int n) {
        if (n > 0) {
            return Stream.cons(value, () -> tail.get().take(n - 1));
        }
        return Stream.empty();
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
