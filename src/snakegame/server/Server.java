package snakegame.server;

import snakegame.structs.Snake;
import snakegame.structs.World;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetSocketAddress;
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
    private String host;

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";
    private Queue<Message> events;

//    Map<UUID, Snake> clients;

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
        this.world = new World();
        this.events = new LinkedBlockingQueue<Message>();
//        this.clients = new ConcurrentHashMap<UUID, Snake>();
    }

    public Server() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }

    public Server(int port) { this(port, DEFAULT_HOST); };
    public Server(String host) { this(DEFAULT_PORT, host); };

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);

            Socket socket = ss.accept();

            new Thread(new ClientHandler(socket)).run();
        } catch(Exception x) { System.out.println("¯\\_(ツ)_/¯"); }
    }

    public static void main(String[] args) {
        new Server().run();
    }

    public class ClientHandler implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            try {
                this.in = new DataInputStream(socket.getInputStream());
                this.out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("¯\\_(ツ)_/¯");
            }
        }

        @Override
        public void run() {
            String line = null;
            synchronized (world){
                UUID uuid = world.generateSnake();
                try {
                    out.writeUTF(uuid.toString());
                    out.flush();
                } catch (IOException e) {
                    System.out.println("¯\\_(ツ)_/¯");;
                }
            }
            try {
                while (true) {
                    line = in.readUTF();
                    Message message = Message.parse(line);
                    events.add(message);
                    synchronized (world) {
                        out.writeUTF(world.toString());
                        out.flush();
                    }
                }
            }
            catch(IOException e) {
                System.out.println("¯\\_(ツ)_/¯");
            }
        }
    }
}
