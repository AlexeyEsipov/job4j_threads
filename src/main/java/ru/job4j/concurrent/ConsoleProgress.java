package ru.job4j.concurrent;

public class ConsoleProgress {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    try {
                        String[] process = new String[] {"/", "--", "\\", "|"};
                        while (!Thread.currentThread().isInterrupted()) {
                            for (int j = 0; j < 4; j++) {
                                System.out.print("\rLoading ... " + process[j]);
                                Thread.sleep(250);
                            }
                        }
                    } catch (InterruptedException e) {
                        System.out.println("\nЗагрузка прервана");
                    }
                }
        );
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
