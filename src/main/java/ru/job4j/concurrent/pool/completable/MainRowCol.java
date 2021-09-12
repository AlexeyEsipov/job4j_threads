package ru.job4j.concurrent.pool.completable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainRowCol {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        System.out.println("Col 0 (1,4,7): " + RolColSum.asyncSum(m)[0].getColSum());
        System.out.println("Col 0 (1,4,7): " + RolColSum.sum(m)[0].getColSum());
        System.out.println("Col 1 (2,5,8): " + RolColSum.asyncSum(m)[1].getColSum());
        System.out.println("Col 1 (2,5,8): " + RolColSum.sum(m)[1].getColSum());
        System.out.println("Col 2 (3,6,9): " + RolColSum.asyncSum(m)[2].getColSum());
        System.out.println("Col 2 (3,6,9): " + RolColSum.sum(m)[2].getColSum());
        System.out.println("Row 0 (1,2,3): " + RolColSum.asyncSum(m)[0].getRowSum());
        System.out.println("Row 0 (1,2,3): " + RolColSum.sum(m)[0].getRowSum());
        System.out.println("Row 1 (4,5,6): " + RolColSum.asyncSum(m)[1].getRowSum());
        System.out.println("Row 1 (4,5,6): " + RolColSum.sum(m)[1].getRowSum());
        System.out.println("Row 2 (7,8,9): " + RolColSum.asyncSum(m)[2].getRowSum());
        System.out.println("Row 2 (7,8,9): " + RolColSum.sum(m)[2].getRowSum());
    }
}
