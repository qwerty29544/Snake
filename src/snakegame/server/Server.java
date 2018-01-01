package snakegame.server;

import snakegame.structs.Snake;
import snakegame.structs.World;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private World world;
    private int port;

    static int DEFAULT_PORT = 1337;
    private Queue<Message> events;

    Map<UUID, Snake> clients;

    public Server(int port) {
        this.port = port;
        this.world = new World();
        this.events = new LinkedBlockingQueue<Message>();
        this.clients = new ConcurrentHashMap<UUID, Snake>();
    }

    public Server() {
        this(DEFAULT_PORT);
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);

            Socket socket = ss.accept();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;

            while(true) {
                line = in.readUTF();
                Message message = Message.parse(line);
                events.add(message);
                synchronized (this.world) {
                    out.writeUTF(this.world.toString());
                    out.flush();
                }
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}
