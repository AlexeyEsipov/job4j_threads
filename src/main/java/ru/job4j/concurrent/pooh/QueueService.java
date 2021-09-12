package ru.job4j.concurrent.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
            queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String method = req.getMethod();
        String theme = req.getTheme();
        String message = req.getMessage();
        if (("GET").equals(method)) {
            ConcurrentLinkedQueue<String> innerQueue = queue.get(theme);
            if (innerQueue != null) {
                return !innerQueue.isEmpty() ? new Resp(innerQueue.poll(), 200)
                                             : new Resp("Тема: " + theme + " нет сообщений", 200);
            } else {
                return  new Resp("Тема " + theme + " не найдена", 404);
            }
        } else {
            queue.putIfAbsent(theme, new ConcurrentLinkedQueue<>());
            queue.get(theme).offer(message);
            return new Resp("Сообщение в очереди", 200);
        }
    }
}
