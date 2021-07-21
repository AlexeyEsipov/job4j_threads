package ru.job4j.concurrent;

public class NewThread implements Runnable {
    private Thread t;

    NewThread() {
        t = new Thread(this, "Демонстрационный поток");
        System.out.println("Дочерний поток создан: " + t);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println("Дочерний поток: " + n);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Дочерний поток исполнения прерван.");
        }
        System.out.println("Дочерний поток завершен.");
    }
}

class ThreadDemo {
    public static void main(String[] args) {
        new NewThread();
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println("Главный поток: " + n);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Главный поток исполнения прерван.");
        }
        System.out.println("Главный поток завершен.");
    }
}
