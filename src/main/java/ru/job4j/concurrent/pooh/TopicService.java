package ru.job4j.concurrent.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer,
            ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String theme = req.getTheme();
        String method = req.getMethod();
        String message = req.getMessage();
        int id = req.getId();
        if (("POST").equals(method)) {
            if (queue.putIfAbsent(theme, new ConcurrentHashMap<>()) != null) {
                queue.get(theme).forEach((k, v) -> v.offer(message));
                return new Resp("Сообщение добавлено всем подписчикам", 200);
            } else {
                queue.remove(theme);
            }
            return new Resp("Эта тема подписчикам не интересна", 200);
        } else {
            ConcurrentHashMap<Integer, ConcurrentLinkedQueue<String>> nestedMap =
                    queue.putIfAbsent(theme, new ConcurrentHashMap<>());
            if (nestedMap != null) {
                if (nestedMap.putIfAbsent(id, new ConcurrentLinkedQueue<>()) != null) {
                    String text = nestedMap.get(id).poll();
                    if (text == null) {
                        return new Resp("Новые сообщения отсутствуют.", 200);
                    }
                    return new Resp(text, 200);
                }
                return new Resp("Вы подписаны.", 200);
            } else {
                queue.get(theme).putIfAbsent(id, new ConcurrentLinkedQueue<>());
                return new Resp("Топик создан и вы подписаны.", 200);
            }
        }
    }
}
