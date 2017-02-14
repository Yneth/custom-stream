package ua.rd.utils;

interface AccumulatorRight<A, R> {
    R accumulate(A a, Lazy<R> r);
}