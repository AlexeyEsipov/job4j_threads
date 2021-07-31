package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        Base newModel = new Base(model.getId(), model.getVersion());
        newModel.setName(model.getName());
        return  memory.putIfAbsent(model.getId(), newModel) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        int id = model.getId();
        int version = model.getVersion();
        String nameNew = model.getName();
        Base oldModel = memory.get(id);
        if (memory.get(id).getVersion() != version) {
            throw new OptimisticException("Versions are not equal");
        }
        memory.computeIfPresent(id, (k, v) -> {
                Base result = new Base(id, version + 1);
                result.setName(nameNew);
                return result;
            }
        );
        return !memory.containsValue(oldModel);
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
