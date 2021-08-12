package ru.job4j.concurrent.email;

public class MainEmail {
    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user = new User("e@mail", "name");
        emailNotification.emailTo(user);
        emailNotification.close();
    }
}
