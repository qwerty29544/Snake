package snakegame.client;

import snakegame.server.Message;
import snakegame.structs.Snake;
import snakegame.structs.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static snakegame.Constants.BORDER_LENGHT;
import static snakegame.Constants.BORDER_WIDTH;

//Класс клиента, который настраивает соединение с сервером с помощью Socket
//В классе реализуется отрисовка мира, считывание клавиш, отправка сигнала на сервер и принятие ответного сигнала
public class Client extends JFrame {
    private int port;//Переменная, отведённая для порта
    private String host;//Переменная, отведённая для хоста
    private UUID uuid;//Перменная, отведённая для уникального номера пользователя
    private World world = new World();//Объявление переменной класса мир

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";//указать новый интересующий порт сервера, на котором запущена серверная часть игры
    static String TITLE = "SSSsssssnek";

    //конструктор клиента
    public Client(int port, String host) {
        super(TITLE);//Заголовок
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Возможность выхода по кнопке выхода в верней части окна
        setSize(BORDER_WIDTH,BORDER_LENGHT);//Установка размеров окна
        setResizable(false);//Установка невозможности изменять размеры окна
        this.port = port;//Присваивание переменной класса установленного порта
        this.host = host;//Присваивание переменной класса установленного хоста
    }

    //метод реализующий считывание данных с клавиатуры
    public static class ClientKeyListener implements KeyListener {
        private UUID uuid;//Переменная уникального номера пользователя
        private DataOutputStream dataOutputStream;//Поток вывода данных

        //Конструктор класса считывания клавиш с клавиатуры, привязка к выводимым клиентом данных уникального номера
        public ClientKeyListener(UUID uuid, DataOutputStream dataOutputStream) {
            this.uuid = uuid;//уникальный идентификатор пользователя
            this.dataOutputStream = dataOutputStream;//выводимые пользователем данные
        }

        @Override
        public void keyTyped(KeyEvent e) { };

        @Override
        public void keyPressed(KeyEvent e) {
            Message message = new Message(e.getKeyCode(), uuid);//Создание сообщения клиента для отправки его на сервер
            try {
                dataOutputStream.writeUTF(message.toString());//Отправка из класса данных, введённых с клавиатуры
            } catch (IOException e1) {
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { };
    }

    public Client() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }//конструктор по умолчанию

    public Client(int port) { this(port, DEFAULT_HOST); }//конструктор по умолчанию

    public Client(String host) { this(DEFAULT_PORT, host); }//конструктор по умолчанию

    //запуск клиентской части программы (окно со змейкой)
    public void run() throws IOException {
        this.setVisible(true);//видимое окно игры
        Socket socket = new Socket(this.host, this.port);//создание сокета клиента, отправка сообщения на хост о создании соединения
        InputStream sin = socket.getInputStream();//переменная потока ввода данных
        OutputStream sout = socket.getOutputStream();//переменная потока выводимых из клиента данных
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);
        uuid = UUID.fromString(in.readUTF());//установка, считывание идентификатора при открытии сокета
        this.addKeyListener(new ClientKeyListener(uuid, out));//запуск функции считывания клавиш с клавиатуры
        String line = null;//начально положение строки данных
        while (true) {
            line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            world = World.parse(line);//однозначное декодирование данных о состоянии мира из строки line и присваивание этих данных локальной переменной World, отвечающей за отрисовку поля
            repaint();
        }
    }

    //запуск клиента
    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //функция отрисовки клиентского окна
    public void paint(Graphics g)//
    {
        Graphics2D g2 = (Graphics2D) g.create();//создание экземлара класса работы с графикой
        g2.setColor(Color.white);//выбор белого цвета рисования
        g2.fillRect(0, 0, getWidth(), getHeight());//заполнение всего поля белым цветом
        world.draw(g2);//рисовка мира
    }
}
