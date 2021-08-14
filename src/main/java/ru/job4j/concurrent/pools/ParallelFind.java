package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class  ParallelFind<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T ask;

    public ParallelFind(T[] array, int from, int to, T ask) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.ask = ask;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearFind(from, to);
        }
        int mid = (from + to) / 2;
        ParallelFind<T> leftFind = new ParallelFind<>(array, from, mid, ask);
        ParallelFind<T> rightFind = new ParallelFind<>(array, mid + 1, to, ask);
        leftFind.fork();
        rightFind.fork();
        int left = leftFind.join();
        int right = rightFind.join();
        return Math.max(left, right);
    }

    public static <T> int find(T ask, T[] array) {
        return new ForkJoinPool().invoke(new ParallelFind<>(array, 0, array.length - 1, ask));
    }

    private int linearFind(int from, int to) {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(ask)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws InterruptedException {
        Integer[] mass = new Integer[]{1, 4, 3, 5, 4, 2};
        System.out.println("Serial search: find 2 in list {1, 4, 3, 5, 4, 2}");
        int ss = find(2, mass);
        System.out.println(" index: " + ss);

        Integer[] mass1 = new Integer[]{2, 4, 3, 5, 2, 2, 2, 7, 8, 19, 10, 17, 16};
        System.out.println("Parallel search: find 17 in list {2,4,3,5,2,2,2,7,8,19,10,17,16}");
        int parallelSearch = find(17, mass1);
        System.out.println(" index: " + parallelSearch);

        System.out.println("Parallel search (not found): "
                + "find 25 in list {2, 4, 3, 5, 2, 2, 2, 7, 8, 19, 10, 17, 16}");
        int parallelSearchNotElement = find(25, mass1);
        System.out.println(" index: " + parallelSearchNotElement);
    }
}
