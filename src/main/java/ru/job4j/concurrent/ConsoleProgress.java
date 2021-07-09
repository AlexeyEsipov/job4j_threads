package ru.job4j.concurrent;

public class ConsoleProgress {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    try {
                        String[] process = new String[] {"/", "--", "\\", "|"};
                        int i = 0;
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.print("\rLoading ... " + process[i]);
                            i++;
                            if (i == 4) {
                                i = 0;
                            }
                            Thread.sleep(250);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("\nЗагрузка прервана");
                    }
                }
        );
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
