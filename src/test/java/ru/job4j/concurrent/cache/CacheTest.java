package ru.job4j.concurrent.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenVersionEqualsThenUpdateOk() {
        Cache cache = new Cache();
        Base base = new Base(0, 0, "name");
        cache.add(base);
        base.setName("nameUpdate");
        assertTrue(cache.update(base));
        assertEquals(1, cache.get(0).getVersion());
    }

    @Test(expected = OptimisticException.class)
    public void whenVersionNotEqualsThenException() {
        Cache cache = new Cache();
        Base base = new Base(0, 0);
        cache.add(base);
        cache.update(new Base(0, 1));
    }
}