package ru.job4j.concurrent.pool.codelessons;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolCoderLessons {
    private final PoolWorker[] threads;
    private final LinkedBlockingQueue<Task> queue;
    private volatile boolean isRunning = true;

    public ThreadPoolCoderLessons(int nThreads) {
        queue = new LinkedBlockingQueue<>();
        threads = new PoolWorker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Task task) {
        synchronized (queue) {
            queue.offer(task);
            queue.notify();
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;

            while (isRunning) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: "
                                    + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }

                /* If we don't catch RuntimeException,*/
                /* the pool could leak threads*/
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: "
                            + e.getMessage());
                }
            }
        }
    }
}
