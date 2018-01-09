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
    private World world;//локальная переменная типа World для манипулирования данными игрового поля
    private int port;//переменная порт сервера
    private String host;//переменная хост

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";
    static int UPDATE_INTERVAL = 200;//интервал задержки основного(и единственного) потока игры
    static int NUM_APPLES = 5;//количество яблок
    private Map<UUID, Message> events;//Карта событий от идентификатора и сообщений(данных) клиента
    private Set<ClientUpdater> updaters;//
    private Set<Socket> sockets;

    //конструктор класса Server
    public Server(int port, String host) {
        this.port = port;//утсановка значения
        this.host = host;//установка значения
        this.world = new World();//создание пустого нового мира
        for(int i = 0; i < NUM_APPLES; i++) {//счетчика яблок
            world.generateApple();//генерация нового яблока
        }
        world.generateApple();//генерация нового яблока
        this.events = new ConcurrentHashMap<UUID, Message>();//события есть ссписок ссообщений клиентов
        this.updaters = new HashSet<ClientUpdater>();//обновления это хэшсписок

    }

    //конструктор по умолчанию
    public Server() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }

    //конструктор только по порту
    public Server(int port) {
        this(port, DEFAULT_HOST);
    }


    //конструктор только по хосту
    public Server(String host) {
        this(DEFAULT_PORT, host);
    }

    //запуск мира и основного потока
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);//открытие нового сервер-сокета по порту
            new Thread(new WorldUpdater()).start();//новый поток обновления состояния мира
            while(true) {
                Socket socket = ss.accept();//принятие всех сокетов клиентов
                new Thread(new ClientHandler(socket)).start();//начать поток передачи данных клиентам и принятия данных от них
                synchronized (updaters){//сихронизированные апдейтеры
                    updaters.add(new ClientUpdater(socket));//добавление нового потребителя апдейтов по сокету
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    //запуск сервера из мейна
    public static void main(String[] args) {
        new Server().run();
    }

    //держатель обновлений клиентов и сервера
    public class ClientHandler implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;

        //конструктор класса по сокету
        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;//сокет
                this.in = new DataInputStream(socket.getInputStream());//поток ввода из сокета
                this.out = new DataOutputStream(socket.getOutputStream());//поток ввода в сокет
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //запуск потока обновлений
        @Override
        public void run() {
            String line = null;//начально состояние сообщений
            synchronized (world) {//синхронизированный мир
                UUID uuid = world.generateSnake();//выделение нового UUID для новой змейки
                try {
                    out.writeUTF(uuid.toString());//запись по UUID данных в клиент
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                while (true) {
//                    TODO catch eof exception and remove snakes
                    line = in.readUTF();//запись из клиента
                    Message message = Message.parse(line);//принятие сообщения о нажании клавиши
                    synchronized (events) {//синхронные события
                        events.put(message.getUuid(), message);//положить в список событий интерпретированные данные из клиента
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {//закрытие всех поток передачи данных
                    in.close();
                    out.close();
                    socket.close();
                }
                catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
    }

    //класс обработчика выводимых в клиент данных
    public class ClientUpdater {
        private DataOutputStream out;//переменная выводимого потока данных

        //конструктор класса
        public ClientUpdater(Socket socket) {
            try {
                this.out = new DataOutputStream(socket.getOutputStream());//ассоциирование переменной и конкретного сокета клиента
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //метод записи данных в клиент
        public void publish() throws IOException {
            try {
                out.writeUTF(world.toString());//запись данных о мире в текстовом виде
                out.flush();//
            } catch (SocketException e) {
                updaters.remove(this);
            }
        }
    }

    //класс содержащий данные об основном потоке обновления данных на клиентах
    public class WorldUpdater implements Runnable {
        @Override
        public void run() {
//            TODO: magic number
            while (true) {
                try {
                    Thread.sleep(UPDATE_INTERVAL);//время обновления потока приложения
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (world) {//синхронизация игрового поля и данных в нём, как единого целого
                    processMessages();//метод обработки сообщений клиентов
                    world.step();//шаг на поле всех змеек
                    for(ClientUpdater updater : updaters) {//для всех клиентов, подключенных по клиент-сокету, обновить
                        try {
                            updater.publish();//метод публикации данных
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void processMessages() {
            synchronized (events) {//сихронизированные сообщения пользователей (клиентов)
                for (Message message: events.values())//цикл по всем сообщениям (данным) клиентов
                    world.processMessage(message);//обработка данных клиента и расстановка на поле принятых данных
                events.clear();//очистка списка
            }
        }
    };
}