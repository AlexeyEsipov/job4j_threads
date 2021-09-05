package ru.job4j.concurrent.pooh;

import java.util.Objects;

public class Req {
    private String method;
    private String mode;
    private String theme;
    private String message;
    private int id;

    private Req(String method, String mode, String theme, String message, int id) {
        this.method = method;
        this.mode = mode;
        this.theme = theme;
        this.message = message;
        this.id = id;
    }

    public static Req of(String content) {
        // POST /queue/weather / HTTP/1.1
        // метод/режим/тема/

        // объявляем переменные
        int id = -1;
        String message = "";
        // разбиваем текст на строки
        String[] lines = content.split(System.lineSeparator());
//        System.out.println("длина = " + lines.length);
        // разбиваем первую строку на слова
        String[] words = lines[0].split("/");
        // присваиваем переменным значения
        String method = words[0].trim();
        String mode = words[1];
        String theme = words[2].split(" ")[0];

        // последняя строка с параметрами, если она должна быть
        if (Objects.equals(method, "POST")) {
            message = lines[lines.length - 1];
        }
        // ID пользователя, если он должен быть
        if (Objects.equals(method, "GET") && Objects.equals(mode, "topic")) {
            id = Integer.parseInt(words[3].split(" ")[0]);
        }
        return new Req(method, mode, theme, message, id);
    }

    public String getMethod() {
        return method;
    }

    public String getMode() {
        return mode;
    }

    public String getTheme() {
        return theme;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Req{"
                + "method=" + method
                + ", mode=" + mode
                + ", theme=" + theme
                + ", message=" + message
                + ", id=" + id + '}';
    }
}
