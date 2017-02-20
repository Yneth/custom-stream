package ua.rd.utils;

public class DelayedConsTest extends AbstractConsTest {

    @Override
    Stream<Integer> createIntStream() {
        return new DelayedCons<>(() -> StreamOps.of(INTS));
    }
}