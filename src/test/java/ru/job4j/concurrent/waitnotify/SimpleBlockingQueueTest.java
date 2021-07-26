package ru.job4j.concurrent.waitnotify;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueueTest {
    private final List<Integer> result = new ArrayList<>();

    /**
     * Класс описывает нить producer.
     */
    private static class ThreadProducer extends Thread {
        private final SimpleBlockingQueue<Integer> ps;
        private final List<Integer> list;

        private ThreadProducer(SimpleBlockingQueue<Integer> ps, List<Integer> list) {
            this.ps = ps;
            this.list = list;
        }

        @Override
        public void run() {
            for (int i: list) {
                try {
                    this.ps.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Класс описывает нить consumer.
     */
    private class ThreadConsumer extends Thread {
        private final SimpleBlockingQueue<Integer> ps;

        private ThreadConsumer(SimpleBlockingQueue<Integer> ps) {
            this.ps = ps;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    result.add(ps.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void whenExecute2ThreadThen2() {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(4);
        List<Integer> list1 = List.of(0, 1, 2, 3, 4);
        List<Integer> list2 = List.of(10, 11, 12, 13, 14);

        /*
          Создаем две нити producer и одну нить consumer, и запускаем их.
         */
        ThreadProducer producer1 = new ThreadProducer(sbq, list1);
        ThreadProducer producer2 = new ThreadProducer(sbq, list2);
        ThreadConsumer consumer = new ThreadConsumer(sbq);
        producer1.start();
        producer2.start();
        consumer.start();
        try {
            producer1.join();
            producer2.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        /*
        проверяем, что коллекции list1 и list2 приняты полностью.
         */
        assertTrue(result.removeAll(list1));
        assertTrue(result.removeAll(list2));
        assertTrue(result.isEmpty());
    }
}