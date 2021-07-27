package ru.job4j.concurrent.cas;

import org.junit.Test;
import static org.junit.Assert.*;

public class CASCountTest {

    /**
     * Внутренний класс описывает нить increment.
     */
    private static class ThreadIncrement extends Thread {
        private final int maxStep;
        private final CASCount count;

        public ThreadIncrement(int maxStep, CASCount count) {
            this.maxStep = maxStep;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < maxStep; i++) {
                count.increment();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Test
    public void whenIncrement5and10ThenCount15() {
        CASCount casCount = new CASCount();
        /*
        создаем две нити и запускаем их, потом сравниваем результат с ожидаемым
         */
        ThreadIncrement increment1 = new ThreadIncrement(5, casCount);
        ThreadIncrement increment2 = new ThreadIncrement(10, casCount);
        increment1.start();
        increment2.start();
        try {
            increment1.join();
            increment2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals(15, casCount.get());
    }
}