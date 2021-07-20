package ru.job4j.concurrent.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    @GuardedBy("this")
    private final AtomicInteger id = new AtomicInteger();

    public synchronized boolean add(User user) {
        users.put(id.incrementAndGet(), new User(id.get(), user.getAmount()));
        return users.containsKey(id.get());
    }

    public synchronized User get(int id) {
        return users.get(id);
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        User tmp = users.get(user.getId());
        if (tmp.equals(user)) {
            users.remove(user.getId());
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        if (users.get(user.getId()) == null) {
            return false;
        }
        users.replace(user.getId(), new User(user.getId(), user.getAmount()));
        return true;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (fromId == toId) {
            return false;
        }
        User sender = users.get(fromId);
        User recipient = users.get(toId);
        if (sender == null || recipient == null) {
            return false;
        }
        if (sender.getAmount() < amount) {
            return false;
        }
        return update(new User(sender.getId(), sender.getAmount() - amount))
                && update(new User(recipient.getId(), recipient.getAmount() + amount));
    }
}
