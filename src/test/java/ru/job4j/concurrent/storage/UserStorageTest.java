package ru.job4j.concurrent.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {

    private static class ThreadMapAdd200 extends Thread {
        private final UserStorage us;

        public ThreadMapAdd200(UserStorage us) {
            this.us = us;
        }

        @Override
        public void run() {
            this.us.add(new User(200));
        }
    }

    private static class ThreadMapAdd400 extends Thread {
        private final UserStorage us;

        public ThreadMapAdd400(UserStorage us) {
            this.us = us;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.us.add(new User(400));
        }
    }

    @Test
    public void when200plus400Then600() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        Thread first = new ThreadMapAdd200(userStorage);
        Thread second = new ThreadMapAdd400(userStorage);
        first.start();
        second.start();
        first.join();
        second.join();
        userStorage.transfer(1, 2, 200);
        assertEquals(600, userStorage.get(2).getAmount());

    }
}