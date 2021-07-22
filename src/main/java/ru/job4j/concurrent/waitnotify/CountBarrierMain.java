package ru.job4j.concurrent.waitnotify;

public class CountBarrierMain {
    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread await = new Thread(barrier::await);
        await.start();
        for (int i = 0; i < 100000; i++) {
            barrier.count();
        }
    }
}
