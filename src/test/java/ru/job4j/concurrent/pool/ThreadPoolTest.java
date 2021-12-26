package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThreadPoolTest {
    private List<Integer> result = new ArrayList<>();

    /**
     * Класс описывает задачу.
     */
    private class TaskTest implements Runnable {
        private int num;

        public TaskTest(int n) {
            num = n;
        }

        @Override
        public void run() {
            result.add(num);
        }
    }

    @Test
    public void whenTaskFrom0To4ThenListResultFrom0To4() {
        ThreadPool pool = new ThreadPool();
        List<Integer> l1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TaskTest taskTest = new TaskTest(i);
            pool.work(taskTest);
            l1.add(i);
        }
/**        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        } */
        pool.shutdown();
            System.out.println(result);
        System.out.println(l1);
    }

}