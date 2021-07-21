package ru.job4j.concurrent;

public class NewThread294 extends Thread {

    public NewThread294() {
        super("Демонстрационный поток");
        System.out.println("Дочерний поток: " + this);
        start();
    }

    public void run() {
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println("Дочерний поток: " + n);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Дочерний поток прерван.");
        }
        System.out.println("Дочерний поток завершен.");
    }
}

class ExtendThread {
    public static void main(String[] args) {
        new NewThread294();
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println("Главный  поток: " + n);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Главный поток исполнения прерван.");
        }
        System.out.println("Главный поток завершен.");
    }
}
