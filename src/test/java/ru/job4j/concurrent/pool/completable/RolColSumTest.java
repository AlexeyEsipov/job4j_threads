package ru.job4j.concurrent.pool.completable;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {
    private final int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    @Test
    public void whenSerialSum() {
        assertEquals(12, RolColSum.sum(m)[0].getColSum());
        assertEquals(15, RolColSum.sum(m)[1].getColSum());
        assertEquals(18, RolColSum.sum(m)[2].getColSum());
        assertEquals(6, RolColSum.sum(m)[0].getRowSum());
        assertEquals(15, RolColSum.sum(m)[1].getRowSum());
        assertEquals(24, RolColSum.sum(m)[2].getRowSum());
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        assertEquals(12, RolColSum.asyncSum(m)[0].getColSum());
        assertEquals(15, RolColSum.asyncSum(m)[1].getColSum());
        assertEquals(18, RolColSum.asyncSum(m)[2].getColSum());
        assertEquals(6, RolColSum.asyncSum(m)[0].getRowSum());
        assertEquals(15, RolColSum.asyncSum(m)[1].getRowSum());
        assertEquals(24, RolColSum.asyncSum(m)[2].getRowSum());
    }

    @Test
    public void whenSerialSumEqualsAsyncSum() throws ExecutionException, InterruptedException {
        assertEquals(RolColSum.sum(m)[0].getColSum(), RolColSum.asyncSum(m)[0].getColSum());
        assertEquals(RolColSum.sum(m)[1].getColSum(), RolColSum.asyncSum(m)[1].getColSum());
        assertEquals(RolColSum.sum(m)[2].getColSum(), RolColSum.asyncSum(m)[2].getColSum());
        assertEquals(RolColSum.sum(m)[0].getRowSum(), RolColSum.asyncSum(m)[0].getRowSum());
        assertEquals(RolColSum.sum(m)[1].getRowSum(), RolColSum.asyncSum(m)[1].getRowSum());
        assertEquals(RolColSum.sum(m)[2].getRowSum(), RolColSum.asyncSum(m)[2].getRowSum());
    }
}