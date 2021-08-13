package ru.job4j.concurrent.pools;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParallelFindTest {

    @Test
    public void when2Then5() {
        Integer[] arr = new Integer[]{1, 4, 3, 5, 4, 2, 7, 8, 19, 10, 19, 16};
        int ask = 2;
        assertEquals(5, ParallelFind.find(ask, arr));
    }

    @Test
    public void when0ThenNotFound() {
        Integer[] arr = new Integer[]{1, 4, 3, 5, 2, 2, 7, 8, 19, 10, 19, 16};
        int ask = 0;
        assertEquals(-1, ParallelFind.find(ask, arr));
    }

    @Test
    public void whenSeveral2ThenFindAny() {
        Integer[] arr = new Integer[]{2, 4, 3, 5, 2, 2, 2, 7, 8, 19, 10, 19, 16};
        int ask = 2;
        assertTrue(List.of(0, 4, 5, 6).contains(ParallelFind.find(ask, arr)));
    }

    @Test
    public void whenSerialSearch0ThenNotFound() {
        Integer[] arr = new Integer[]{1, 4, 3, 5, 2};
        int ask = 0;
        assertEquals(-1, ParallelFind.find(ask, arr));
    }

    @Test
    public void whenSerialSearch3Then2() {
        Integer[] arr = new Integer[]{1, 4, 3, 5, 2};
        int ask = 3;
        assertEquals(2, ParallelFind.find(ask, arr));
    }

    @Test
    public void whenSerialSearch7ThenNotFound() {
        Integer[] arr = new Integer[]{1, 4, 3, 5, 2};
        int ask = 7;
        assertEquals(-1, ParallelFind.find(ask, arr));
    }
}