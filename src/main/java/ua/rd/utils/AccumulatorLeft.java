package ua.rd.utils;

interface AccumulatorLeft<A, R> {
    R accumulate(R r, A a);
}