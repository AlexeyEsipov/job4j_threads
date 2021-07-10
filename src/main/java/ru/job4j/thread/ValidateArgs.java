package ru.job4j.thread;

public class ValidateArgs {
    static void parseArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Некорректные аргументы. "
                    + "Программа должна собираться в jar и запускаться через "
                    + "java -jar Wget.jar https://source.com/file.txt 5 dest.txt "
                    + "где https://source.com/file.txt - путь к источнику закачки"
                    + "    5 - желаемая скорость скачивания"
                    + "    dest.txt - файл назначения");
        }
        String speed = args[1];
        boolean isNumeric = speed.chars().allMatch(Character::isDigit);
        if (!isNumeric) {
            throw new IllegalArgumentException("Некорректные аргументы. "
                    + speed
                    + " - желаемая скорость скачивания"
                    + " должна быть записана цифрами");
        }
    }
}
