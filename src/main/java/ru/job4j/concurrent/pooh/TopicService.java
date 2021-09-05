package ru.job4j.concurrent.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    // Здесь queue - внешняя хэш-карта, где ключом является Тема,
    // а значением другая хеш-карта, назовем ее внутренняя,
    // в которой ключом является ID пользователя, а значением - очередь сообщений по Теме,
    //
    // то есть, можно сказать, что queue - это внешняя хэш-карта с ключом по темам,
    // для вложенных хэш карт с
    // ключом по ID. А вложенные хэш-карты - это хэш-карты конкретного
    // клиента с очередями, на которые он подписан.
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer,
            ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

// здесь только TOPIC
    @Override
    public Resp process(Req req) {
        //System.out.println("in Topic process " + req.getMethod());
        //
        String theme = req.getTheme(); // тема
        String method = req.getMethod(); // метод
        String message = req.getMessage(); // сообщение
        int id = req.getId(); // ID подписчика
        // если метод POST - отправитель рассылает сообщение всем подписчикам по теме
        if (method.equals("POST")) {
            // Попытка внести в хранилище новую (а значит, пустую) хэш-карту с ключом по теме,
            //если не получится поместить (результат != null), то значит она там уже есть.
            // А если получилось внести (результат = null) то значит, что ее там нет, то есть
            // нет подписчиков по этой теме, а то, что мы сейчас внесли, надо удалить.
            // Итак: пытаемся поместить пустую тему и оцениваем результат
            var innerMap = queue.putIfAbsent(theme, new ConcurrentHashMap<>());
            if (innerMap != null) {
                // если не получилось, то достаем ту, что там уже есть по теме
                // и каждому подписчику (k- ключ по ID) в его очередь (v - очередь сообщений)
                // добавляем сообщение
                queue.get(theme).forEach((k, v) -> v.offer(message));
                return new Resp("сообщение добавлено всем подписчикам", 200);
            } else {
                // если получилось внести пустую (новую), то это значит, что ее там раньше не было
                // и то что мы внесли, надо удалить
                queue.remove(theme);
            }
            return new Resp("эта тема подписчикам не интересна", 200);
        // если метод GET - получатель забирает сообщение из темы по своему ID
        } else {
            // Пытаемся достать из хранилища карту по теме и оцениваем результат
            // Попытку организуем, как и в предыдущем методе
            // - пытаемся поместить пустую карту с ключом по теме.
            var nestedMap = queue.putIfAbsent(theme, new ConcurrentHashMap<>());
            // оцениваем результат - = null значит получилось, != - не получилось
            if (nestedMap != null) {
                // Если не получилось поместить новую, то из старой (а она запишется в переменную q)
                // достаем очередь сообщений для подписчика с нужным ID.
                // Проверим, есть ли у нас такой подписчик. Проверку делаем тем же методом -
                // помещаем во вложенную карту пустую новую
                // очередь по ключу ID и оцениваем результат
                var mapId = nestedMap.putIfAbsent(id, new ConcurrentLinkedQueue<>());
                // оцениваем результат - = null значит получилось (подписчика с таким ID у нас нет),
                // != - не получилось, значит подписчик с таким ID есть.
                if (mapId != null) {
                // такой подписчик есть, извлекаем из его очереди сообщение
                    String text = nestedMap.get(id).poll();

                    if (text == null) {
                        // если сообщение отсутствует, сообщаем об этом
                        return new Resp("new messages are absent", 200);
                    }
                    // если сообщение присутствует, помещаем его в ответ
                    return new Resp(text, 200);
                }
                // если такого подписчика нет, подписываем его
                return new Resp("you are subscribed", 200);
            } else {
                // Если получилось поместить новую тему, это значит,
                // что подписчик ID создал новую тему и
                // подписался на нее.
                // Достаем эту тему и помещаем в нее очередь сообщений для подписчика ID
                queue.get(theme).putIfAbsent(id, new ConcurrentLinkedQueue<>());
                // Сообщаем об этом подписчику.
                return new Resp("topic is created and you are subscribed", 200);
            }
        }
    }
}
