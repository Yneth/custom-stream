package ua.rd.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StreamTest {

    @Test
    public void noneStreamShouldReturnTrueOnIsEmpty() {
        assertTrue(Stream.empty().isEmpty());
    }

    @Test
    public void takeShouldBeEmptyIfCalledOnNone() {
        assertTrue(Stream.empty().take(10).isEmpty());
    }

    @Test
    public void isEmptyShouldBeTrueOnEmptyStream() {
        assertTrue(Stream.empty().isEmpty());
    }

    @Test
    public void foldRightShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(Stream.empty().foldRight(Stream.empty(), (a, r) -> r.get()).isEmpty());
    }

    @Test
    public void foldLeftShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(Stream.empty().foldLeft(Stream.empty(), (r, a) -> r).isEmpty());
    }

    @Test
    public void mapShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(Stream.empty().map(a -> a).isEmpty());
    }
}