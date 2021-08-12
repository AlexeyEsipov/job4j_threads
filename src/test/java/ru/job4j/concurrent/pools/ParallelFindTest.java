package ru.job4j.concurrent.pools;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParallelFindTest {
    @Test
    public void when2Then5() {
        List<Integer> arr = List.of(1, 4, 3, 5, 4, 2, 7, 8, 19, 10, 19, 16);
        int ask = 7;
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertEquals(6, pf.find());
    }

    @Test
    public void when0ThenNotFound() {
        List<Integer> arr = List.of(1, 4, 3, 5, 2, 2, 7, 8, 19, 10, 19, 16);
        int ask = 0;
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertEquals(-1, pf.find());
    }

    @Test
    public void whenSeveral2ThenFindAny() {
        List<Integer> arr = List.of(2, 4, 3, 5, 2, 2, 2, 7, 8, 19, 10, 19, 16);
        int ask = 2;
        List<Integer> arrayList = List.of(0, 4, 5, 6);
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertTrue(arrayList.contains(pf.find()));
    }

    @Test
    public void whenSerialSearch0ThenNotFound() {
        List<Integer> arr = List.of(1, 4, 3, 5, 2);
        int ask = 0;
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertEquals(-1, pf.find());
    }

    @Test
    public void whenSerialSearch3Then2() {
        List<Integer> arr = List.of(1, 4, 3, 5, 2);
        int ask = 3;
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertEquals(2, pf.find());
    }

    @Test
    public void whenSerialSearch7ThenNotFound() {
        List<Integer> arr = List.of(1, 4, 3, 5, 2);
        int ask = 7;
        ParallelFind<Integer> pf = new ParallelFind<>(arr, 0, arr.size(), ask);
        assertEquals(-1, pf.find());
    }
}