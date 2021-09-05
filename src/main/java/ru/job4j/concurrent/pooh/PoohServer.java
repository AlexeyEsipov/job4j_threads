package ru.job4j.concurrent.pooh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoohServer {
    private final HashMap<String, Service> modes = new HashMap<>();

    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                pool.execute(() -> {
                    try (OutputStream out = socket.getOutputStream();
                         InputStream input = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        var total = input.read(buff);
                        var text = new String(Arrays.copyOfRange(buff, 0, total),
                                StandardCharsets.UTF_8);
                        Req req = Req.of(text);
                        Resp resp = modes.get(req.getMode()).process(req);
                        out.write(("HTTP/1.1 " + resp.status() + " OK\r\n\r\n").getBytes());
                        out.write(resp.text().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 // пример POST запроса из командной строки
// C:\Tools\curl-7.74.0_1-win64-mingw\bin> ->
//  -> curl -X POST -d "temperature=178" http://localhost:9000/queue/weather
//    или curl -X POST -d "temperature=178" http://localhost:9000/topic/weather
// пример GET запроса из командной строки
// C:\Tools\curl-7.74.0_1-win64-mingw\bin> curl -i http://localhost:9000/queue/weather
//    или curl -i http://localhost:9000/topic/weather/1
// где: queue или topic - режим работы очередь или топик
//    weather - имя темы, если ее нет, то создать новую
//    1 - ID пользователя

    public static void main(String[] args) {
        new PoohServer().start();
    }
}
