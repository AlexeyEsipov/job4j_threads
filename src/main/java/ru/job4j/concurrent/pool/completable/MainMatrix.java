package ru.job4j.concurrent.pool.completable;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainMatrix {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 109}};
        int[] result = Matrix.asyncSum(m);
        System.out.println(Arrays.toString(result));
    }
}
