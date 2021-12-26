package ru.job4j.concurrent.pool.habr;

public class SingleThreadClient {
    public static void main(String[] args) {
        Counter counter = new Counter();

        long start = System.nanoTime();

        double value = 0;
        for (int i = 0; i < 400; i++) {
            value += counter.count(i);
        }

        System.out.printf("Executed by %d s, value : %f%n",
                (System.nanoTime() - start) / (1000_000_000),
                value);
    }
}
