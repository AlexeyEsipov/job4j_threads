package ru.job4j.concurrent.waitnotify;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueueMain {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> sb = new SimpleBlockingQueue<>(2);
        List<Integer> result = new ArrayList<>();
        Thread pr1 = new Thread(
            () -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        sb.offer(i);
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
        );
        Thread pr2 = new Thread(
                () -> {
                    for (int i = 10; i < 15; i++) {
                        try {
                            sb.offer(i);
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
        );
        Thread con = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            result.add(sb.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        pr1.start();
        pr2.start();
        con.start();
        try {
            pr1.join();
            pr2.join();
            con.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(result.removeAll(List.of(0, 1, 2, 3, 4)));
        System.out.println(result.removeAll(List.of(10, 11, 12, 13, 14)));
        System.out.println(result);
    }
}
