package ua.rd.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class StreamOpsTest {
    private static final Integer[] ints = {1, 2, 3, 4, 5};

    @Test
    public void ofArrayShouldHaveCorrectLength() throws Exception {
        assertEquals(ints.length, StreamOps.of(ints).toList().size());
    }

    @Test
    public void ofArrayShouldHaveCorrectValues() throws Exception {
        assertEquals(Arrays.asList(ints), StreamOps.of(ints).toList());
    }

    @Test
    public void consShouldReturnNonEmptyStreamIfCalledWithNonNullArgs() throws Exception {
        assertFalse(StreamOps.cons(() -> 1, () -> StreamOps.range(2, 5)).isEmpty());
    }

    @Test
    public void consShouldReturnEmptyStreamIfCalledWithNonNullArgs() throws Exception {
        assertFalse(StreamOps.cons(() -> 1, () -> StreamOps.range(2, 5)).isEmpty());
    }

    @Test
    public void emptyShouldBeEmpty() throws Exception {
        assertTrue(StreamOps.empty().isEmpty());
    }

    @Test(expected = StackOverflowError.class)
    public void constantShouldThrowStackOverflow() throws Exception {
        StreamOps.constant(1).toList();
    }

    @Test
    public void constantShouldThrow() throws Exception {
        int n = 20;
        int value = 1;
        assertEquals(Collections.nCopies(n, value), StreamOps.constant(value).take(n).toList());
    }

    @Test
    public void rangeShouldReturnCorrect() throws Exception {
        assertEquals(Arrays.asList(ints), StreamOps.range(1, ints.length + 1).toList());
    }
}