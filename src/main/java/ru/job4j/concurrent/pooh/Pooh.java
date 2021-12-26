package ru.job4j.concurrent.pooh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Pooh {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            boolean done = true;
//            while (!server.isClosed()) {
                while (done) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     InputStream input = socket.getInputStream()) {
                    byte[] buff = new byte[1_000_000];
                    var total = input.read(buff);
                    var text = new String(Arrays.copyOfRange(buff, 0, total),
                            StandardCharsets.UTF_8);
                    System.out.println("=1====");
                    System.out.println(text);
                    System.out.println("=2=======");
                    String[] lines = text.split(System.lineSeparator());
                    String end = lines[lines.length - 1];
                    System.out.println(end);
                    System.out.println("=end======");
                    String first = lines[0];
                    System.out.println(first);
                    System.out.println("=first===");
                    var req = Req.of(text);
                    System.out.println(req);
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write(text.getBytes());
                }
            }
        }
    }
}
