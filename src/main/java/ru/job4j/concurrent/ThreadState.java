package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {

        // создаем первую нить
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        //создаем вторую нить
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        //запускаем первую и вторую нить
        first.start();
        second.start();

        //ждем, пока нити первая и вторая не завершатся

        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("Работа завершена");
    }
}
