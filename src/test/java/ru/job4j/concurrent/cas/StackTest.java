package ru.job4j.concurrent.cas;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StackTest {
    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(stack.poll(), (Integer) 3);
        assertEquals(stack.poll(), (Integer) 2);
        assertEquals(stack.poll(), (Integer) 1);
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals(stack.poll(), (Integer) 1);
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(stack.poll(), (Integer) 2);
        assertEquals(stack.poll(), (Integer) 1);
    }
}