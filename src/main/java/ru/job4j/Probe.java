package ru.job4j;

public class Probe {
    public static void main(String[] args) {
        System.out.println("hello world");
        int[][][] a = new int[2][][];
        a[0] = new int[2][];
        a[0][0] = new int[5];
        a[0][0][0] = 2;
    }
}

