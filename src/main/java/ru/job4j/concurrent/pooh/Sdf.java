package ru.job4j.concurrent.pooh;

public class Sdf {
    public static void main(String[] args) {
        String str = "POST/queue/weather / HTTP/1.1";
        String[] arr = str.split("/");
        System.out.println(arr[1]);
    }
}
