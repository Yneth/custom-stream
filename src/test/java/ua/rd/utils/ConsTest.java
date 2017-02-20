package ua.rd.utils;

public class ConsTest extends AbstractConsTest {

    @Override
    Stream<Integer> createIntStream() {
        return StreamOps.of(INTS);
    }
}
