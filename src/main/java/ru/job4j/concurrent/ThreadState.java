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

        //приостанавливаем нить main до тех пор, пока нити первая и вторая не завершатся
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //нить main возобновляет работу и выполняет последний оператор
        System.out.println("Работа завершена");
    }
}
