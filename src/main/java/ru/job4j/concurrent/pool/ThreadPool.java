package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.waitnotify.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(i, new TaskWorker());
            threads.get(i).start();
        }
    }

    public void work(Runnable job)  {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                tasks.offer(job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void shutdown() {
        for (Thread t: threads) {
            t.interrupt();
        }
    }

    private final class TaskWorker extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                Runnable nextTask;
                try {
                    nextTask = tasks.poll();
                    nextTask.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
