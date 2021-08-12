package ru.job4j.concurrent.pools;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class  ParallelFind<T> extends RecursiveTask<Integer> {
    private final List<T> array;
    private final int from;
    private final int to;
    private final T ask;

    public ParallelFind(List<T> array, int from, int to, T ask) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.ask = ask;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return array.get(from) == ask ? from : -1;
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

    public int find() {
        if (array.size() < 10) {
            return linearFind();
        }
        return new ForkJoinPool().invoke(new ParallelFind<>(array, 0, array.size() - 1, ask));
    }

    private int linearFind() {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(ask)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> arr = List.of(1, 4, 3, 5, 4, 2);
        System.out.println("Serial search: find 2 in list {1, 4, 3, 5, 4, 2}");
        int serialSearch = new ParallelFind<>(arr, 0, arr.size(), 2).find();
        System.out.println(" index: " + serialSearch);
        arr = List.of(2, 4, 3, 5, 2, 2, 2, 7, 8, 19, 10, 17, 16);
        System.out.println("Parallel search: find 17 in list {2,4,3,5,2,2,2,7,8,19,10,17,16}");
        int parallelSearch = new ParallelFind<>(arr, 0, arr.size(), 17).find();
        System.out.println(" index: " + parallelSearch);
        System.out.println("Parallel search: find 25 in list {2,4,3,5,2,2,2,7,8,19,10,17,16}");
        int parallelSearchNotElement = new ParallelFind<>(arr, 0, arr.size(), 25).find();
        System.out.println(" index: " + parallelSearchNotElement);
    }
}
