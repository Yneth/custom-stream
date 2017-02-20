package ua.rd.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmptyTest {

    @Test
    public void noneStreamShouldReturnTrueOnIsEmpty() {
        assertTrue(StreamOps.empty().isEmpty());
    }

    @Test
    public void takeShouldBeEmptyIfCalledOnNone() {
        assertTrue(StreamOps.empty().take(10).isEmpty());
    }

    @Test
    public void isEmptyShouldBeTrueOnEmptyStream() {
        assertTrue(StreamOps.empty().isEmpty());
    }

    @Test
    public void foldRightShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().foldRight(StreamOps.empty(), (a, r) -> r.get()).isEmpty());
    }

    @Test
    public void foldLeftShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().foldLeft(StreamOps.empty(), (r, a) -> r).isEmpty());
    }

    @Test
    public void mapShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().map(a -> a).isEmpty());
    }

    @Test
    public void flatMapShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().flatMap(StreamOps::of).isEmpty());
    }

    @Test
    public void filterShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().filter(a -> false).isEmpty());
    }

    @Test
    public void appendShouldBeEmptyIfCalledOnEmpty() {
        assertTrue(StreamOps.empty().append(StreamOps.empty()).isEmpty());
    }

    @Test
    public void appendShouldReturnPassedStream() {
        assertTrue(!StreamOps.empty().append(StreamOps.of(1)).isEmpty());
    }

    @Test
    public void toListShouldReturnEmptyList() {
        assertTrue(StreamOps.empty().toList().isEmpty());
    }
}