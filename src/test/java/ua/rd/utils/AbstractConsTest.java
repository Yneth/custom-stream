package ua.rd.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public abstract class AbstractConsTest {
    protected static final Integer[] INTS = new Integer[]{1, 2, 3, 4, 5};
    protected Stream<Integer> intStream;

    @Before
    public final void setUp() {
        intStream = createIntStream();
    }

    abstract Stream<Integer> createIntStream();

    @Test
    public void foldRightShouldBeCalledOnlyForTheHead() throws Exception {
        BiFunction<Integer, Lazy<Stream<Integer>>, Stream<Integer>> fn =
                spy(new RightIntegerStreamAccumulator());
        intStream.foldRight(StreamOps.empty(), fn);

        verify(fn, only()).apply(any(Integer.class), any(Lazy.class));
    }

    @Test
    public void foldLeftShouldBeCalledForWholeStream() throws Exception {
        BiFunction<Stream<Integer>, Integer, Stream<Integer>> fn =
                spy(new LeftIntegerStreamAccumulator());
        intStream.foldLeft(StreamOps.empty(), fn);

        verify(fn, times(INTS.length)).apply(any(Stream.class), any(Integer.class));
    }

    @Test
    public void mapShouldBeCalledOnlyForTheHead() throws Exception {
        IntToStringMapper fn = spy(new IntToStringMapper());
        intStream.map(fn);

        verify(fn, only()).apply(any(Integer.class));
    }

    @Test
    public void appendShouldReturnStreamContainingElementsFromBothStreams() throws Exception {
        List<Integer> actual = intStream.append(intStream).toList();

        List<Integer> expected = intStream.toList();
        expected.addAll(expected);

        assertEquals(actual, expected);
    }

    @Test
    public void flatMapShouldOnlyBeCalledForTheHead() throws Exception {
        IntToStringStreamMapper fn = spy(new IntToStringStreamMapper());

        intStream.flatMap(fn);

        verify(fn, only()).apply(any(Integer.class));
    }

    @Test
    public void filterShouldOnlyBeCalledForTheHead() throws Exception {
        EvenIntFilter fn = spy(new EvenIntFilter());

        intStream.filter(fn);

        verify(fn, only()).test(any(Integer.class));
    }

    @Test
    public void takeShouldReturnCorrectList() throws Exception {
        List<Integer> integers = intStream.take(2).toList();
        assertEquals(2, integers.size());
        assertEquals(Arrays.asList(INTS).subList(0, 2), integers);
    }

    @Test
    public void isEmptyShouldBeTrueForNonEmpty() throws Exception {
        assertFalse(intStream.map(i -> i * 2).isEmpty());
    }

    @Test
    public void toList() throws Exception {
        assertEquals(Arrays.asList(INTS), intStream.toList());
    }

    private static class EvenIntFilter implements Predicate<Integer> {

        @Override
        public boolean test(Integer integer) {
            return integer % 2 == 0;
        }
    }

    private static class IntToStringStreamMapper implements Function<Integer, Stream<Integer>> {

        @Override
        public Stream<Integer> apply(Integer integer) {
            return StreamOps.of(integer);
        }
    }

    private static class IntToStringMapper implements Function<Integer, String> {

        @Override
        public String apply(Integer integer) {
            return integer.toString();
        }
    }

    private static class LeftIntegerStreamAccumulator implements BiFunction<Stream<Integer>, Integer, Stream<Integer>> {
        @Override
        public Stream<Integer> apply(Stream<Integer> acc, Integer i) {
            return StreamOps.cons(() -> i, () -> acc);
        }
    }

    private static class RightIntegerStreamAccumulator implements BiFunction<Integer, Lazy<Stream<Integer>>, Stream<Integer>> {

        @Override
        public Stream<Integer> apply(Integer i, Lazy<Stream<Integer>> acc) {
            return StreamOps.cons(() -> i, acc);
        }
    }
}