package ru.job4j.concurrent.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    // queue - это хэш-карта очередей по темам
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
            queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        // здесь только те запросы, у которых mode = "queue"
        String method = req.getMethod();
        String theme = req.getTheme();
        String message = req.getMessage();
        // если запрос GET
        if (method.equals("GET")) {
            // пробуем извлечь очередь с темой запроса
            var innerQueue = queue.get(theme);
            // если очередь существует и
            if (innerQueue != null) {
                // если очередь не пустая, извлекаем и возвращаем ответ, иначе сообщаем что пустая
                return !innerQueue.isEmpty() ? new Resp(innerQueue.poll(), 200)
                                             : new Resp("theme: " + theme + " is empty", 200);
            // если очередь не существует
            } else {
                // сообщаем что очередь не найдена
                return  new Resp("theme " + theme + " not found", 404);
            }
        // если запрос POST
        } else {
            // если очереди с такой темой нет, то создаем ее
            queue.putIfAbsent(theme, new ConcurrentLinkedQueue<>());
            // достать очередь с этой темой и поместить в нее сообщение
            queue.get(theme).offer(message);
            return new Resp("message in queue", 200);
        }
    }
}
