package ru.job4j.concurrent.pool.completable;

public class Loop {
    public static void main(String[] args) {
        int n = 5;
        int[] arr = {0, 1, 2, 3, 4};
        int i = 0;
        for (int k = 0; k <= n - 1 - k; k++) {
            System.out.println(k + " " + i);
            System.out.print(arr[k]);
            System.out.println(arr[n - 1 - k]);
            i++;
        }
    }

}
