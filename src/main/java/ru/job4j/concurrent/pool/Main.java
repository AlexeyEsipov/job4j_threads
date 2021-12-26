package ru.job4j.concurrent.pool;

public class Main {
    public static void main(String[] args)  {
        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 15; i++) {
            Task task = new Task(i);
            pool.work(task);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}


