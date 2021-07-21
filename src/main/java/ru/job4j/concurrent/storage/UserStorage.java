package ru.job4j.concurrent.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized User get(int id) {
        return users.get(id);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (fromId == toId) {
            return false;
        }
        User sender = users.get(fromId);
        User recipient = users.get(toId);
        if (sender == null || recipient == null || sender.getAmount() < amount) {
            return false;
        }
        return update(new User(fromId, sender.getAmount() - amount))
                && update(new User(toId, recipient.getAmount() + amount));
    }
}
