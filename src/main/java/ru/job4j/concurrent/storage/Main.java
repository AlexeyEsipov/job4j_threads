package ru.job4j.concurrent.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserStorage us = new UserStorage();
        User user = new User(1, 200);
        System.out.println(us.add(user));
        System.out.println(us.add(user));
        us.add(new User(2, 400));
        System.out.println(us.get(1));
        System.out.println(us.get(2));
        System.out.println(us.transfer(1, 2, 100));
        System.out.println(us.get(1));
        System.out.println(us.get(2));
        System.out.println(us.transfer(1, 2, -100));
        System.out.println(us.get(1));
        System.out.println(us.get(2));
        List list = new ArrayList();
        list.add(new User(1, 200));
        list.add(new User(2, 300));
        list.add(new User(3, 400));
        List nList = new LinkedList(list);
        System.out.println(nList);
        list.remove(0);
        list.add(new User(4, 700));
        System.out.println(list);
        System.out.println(nList);
        us.update(new User(1, 1000));
        System.out.println(us.transfer(1, 2, 1100));
    }
}
