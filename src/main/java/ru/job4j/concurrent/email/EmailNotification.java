package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(new Thread(
            () -> {
                String subject = String.format("Notification %s to email %s",
                        user.getUsername(), user.getEmail());
                String body = String.format("Add a new event to %s", user.getUsername());
                send(subject, body, user.getEmail());
            }
        ));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {
        // метод должен быть пока пустым, вывод на консоль только для отладки
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }
}
