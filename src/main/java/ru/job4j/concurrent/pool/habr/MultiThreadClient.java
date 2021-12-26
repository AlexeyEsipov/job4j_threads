package ru.job4j.concurrent.pool.habr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadClient {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ThreadPoolHabr threadPool = new ThreadPoolHabr(5);
        Counter counter = new Counter();

        long start = System.nanoTime();

        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> counter.count(j),
                            threadPool
                    ));
        }

        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }

        System.out.printf("Executed by %d s, value : %f%n",
                (System.nanoTime() - start) / (1000_000_000),
                value);

        threadPool.shutdown();
    }
}
