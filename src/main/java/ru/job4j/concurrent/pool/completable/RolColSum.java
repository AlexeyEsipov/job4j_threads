package ru.job4j.concurrent.pool.completable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
    private int rowSum;
    private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < n; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            Sums sum = new Sums();
            sum.setRowSum(sumRow);
            sum.setColSum(sumCol);
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        Sums[] sums = new Sums[n];
        for (int k = 0; k < n; k++) {
            futures.put(k, getTask(matrix, k));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int sumRow = 0;
            int sumCol = 0;
            for (int i = 0; i < data.length; i++) {
                sumRow += data[index][i];
                sumCol += data[i][index];
            }
            Sums sums = new Sums();
            sums.setColSum(sumCol);
            sums.setRowSum(sumRow);
            return sums;
        });
    }
}
