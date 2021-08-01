package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return  memory.putIfAbsent(model.getId(),
                new Base(model.getId(), model.getVersion(), model.getName())) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return Objects.equals(memory.computeIfPresent(model.getId(), (k, v) -> {
            if (memory.get(model.getId()).getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.getId(), model.getVersion() + 1, model.getName());
            }
        ), new Base(model.getId(), model.getVersion() + 1, model.getName()));
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
