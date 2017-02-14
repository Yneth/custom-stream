package ua.rd.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StreamTest {

    @Test
    public void noneStreamShouldReturnTrueOnIsEmpty() {
        assertTrue(Stream.none().isEmpty());
    }
}