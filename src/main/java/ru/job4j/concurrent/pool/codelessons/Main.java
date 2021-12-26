package ru.job4j.concurrent.pool.codelessons;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolCoderLessons pool = new ThreadPoolCoderLessons(7);

        for (int i = 0; i < 15; i++) {
            Task task = new Task(i);
            pool.execute(task);
        }
    }
}

