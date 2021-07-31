package ru.job4j.concurrent.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void update() {
        Cache cache = new Cache();
        Base base = new Base(0, 0);
        base.setName("name");
        cache.add(base);
        base.setName("nameUpdate");
        cache.update(base);
        assertEquals(1, cache.get(0).getVersion());
    }

    @Test(expected = OptimisticException.class)
    public void expect() {
        Cache cache = new Cache();
        Base base = new Base(0, 0);
        cache.add(base);
        Base baseNew = new Base(0, 1);
        cache.update(baseNew);
    }
}