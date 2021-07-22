package ru.job4j.concurrent.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
            while (queue.size() == limit) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            notifyAll();
    }

    public synchronized T poll() {
        T result;
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        result = queue.poll();
        notifyAll();
        return result;
    }
}
