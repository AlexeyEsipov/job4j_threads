package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        Base newBase = new Base(model.getId(), model.getVersion());
        newBase.setName(model.getName());
        return  memory.putIfAbsent(model.getId(), newBase) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        Base newModel = new Base(model.getId(), model.getVersion() + 1);
        newModel.setName(model.getName());
        return Objects.equals(memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base result = new Base(k, v.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }
        ), newModel);
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
