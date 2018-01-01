package snakegame.client;

import snakegame.server.Message;
import snakegame.structs.Snake;
import snakegame.structs.World;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
//    public class Client extends JFrame {
    private int port;
    private String host;

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public Client() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }

    public Client(int port) { this(port, DEFAULT_HOST); };
    public Client(String host) { this(DEFAULT_PORT, host); };

    public void run() throws IOException {
        Socket socket = new Socket(this.host, this.port);
        // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();

        // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);

        String line = null;

        while (true) {
//            Message message = new Message(this.uuid, keyCode);
//            out.writeUTF(message.toString); // отсылаем введенную строку текста серверу.
//            out.flush(); // заставляем поток закончить передачу данных.
            line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            World world = World.parse(line);
//            world.draw();
        }

    }
}
