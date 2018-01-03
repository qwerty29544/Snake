package snakegame.server;

import snakegame.structs.Snake;
import snakegame.structs.World;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private World world;
    private int port;
    private String host;

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";
    private Map<UUID, Message> events;
    private Set<ClientUpdater> updaters;

//    Map<UUID, Snake> clients;

    public Server(int port, String host) {
        this.port = port;
        this.host = host;
        this.world = new World();
        for(int i = 0; i < 5; i++) {
            world.generateApple();
        }
        world.generateApple();
        this.events = new ConcurrentHashMap<UUID, Message>();
        this.updaters = new HashSet<ClientUpdater>();
//        this.clients = new ConcurrentHashMap<UUID, Snake>();
    }

    public Server() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }

    public Server(int port) {
        this(port, DEFAULT_HOST);
    }

    ;

    public Server(String host) {
        this(DEFAULT_PORT, host);
    }

    ;

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);
            new Thread(new WorldUpdater()).start();
            while(true) {
                Socket socket = ss.accept();
                new Thread(new ClientHandler(socket)).start();
                synchronized (updaters){
                    updaters.add(new ClientUpdater(socket));
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
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
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String line = null;
            synchronized (world) {
                UUID uuid = world.generateSnake();
                try {
                    out.writeUTF(uuid.toString());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                while (true) {
//                    TODO catch eof exception and remove snakes
                    line = in.readUTF();
                    Message message = Message.parse(line);
                    synchronized (events) {
                        events.put(message.getUuid(), message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientUpdater {
        private DataOutputStream out;

        public ClientUpdater(Socket socket) {
            try {
                this.out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void publish() throws IOException {
            try {
                out.writeUTF(world.toString());
                out.flush();
            } catch (SocketException e) {
                updaters.remove(this);
            }
        }
    }

    public class WorldUpdater implements Runnable {
        @Override
        public void run() {
//            TODO: magic number
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (world) {
                    processMessages();
                    world.step();
                    for(ClientUpdater updater : updaters) {
                        try {
                            updater.publish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void processMessages() {
            synchronized (events) {
                for (Message message: events.values())
                    world.processMessage(message);
                events.clear();
            }
        }
    };
}