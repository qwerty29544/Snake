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

//public class Client {
public class Client extends JFrame {
    private int port;
    private String host;
    private UUID uuid;
    private World world = new World();

    static int DEFAULT_PORT = 1337;
    static String DEFAULT_HOST = "127.0.0.1";
    static String TITLE = "SSSsssssnek";

    public Client(int port, String host) {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Возможность выхода по кнопке выхода в верней части окна
        setSize(BORDER_WIDTH,BORDER_LENGHT);//Установка размеров окна
//        setUndecorated(true);
        setResizable(false);
        this.port = port;
        this.host = host;
    }

    public static class ClientKeyListener implements KeyListener {
        private UUID uuid;
        private DataOutputStream dataOutputStream;

        public ClientKeyListener(UUID uuid, DataOutputStream dataOutputStream) {
            this.uuid = uuid;
            this.dataOutputStream = dataOutputStream;
        }

        @Override
        public void keyTyped(KeyEvent e) { };//обработчик события нажатия символа юникод на клавиатуре (выход из программы с кодом 0)

        @Override
        public void keyPressed(KeyEvent e) {
            Message message = new Message(e.getKeyCode(), uuid);
            try {
                dataOutputStream.writeUTF(message.toString());
            } catch (IOException e1) {
            }
        }//обработчки нажатия любой клавиши на клавиатуре (отправляемся на обработчик события нажатия клавиши)

        @Override
        public void keyReleased(KeyEvent e) { };
    }

    public Client() {
        this(DEFAULT_PORT, DEFAULT_HOST);
    }

    public Client(int port) { this(port, DEFAULT_HOST); };
    public Client(String host) { this(DEFAULT_PORT, host); };

    public void run() throws IOException {
        this.setVisible(true);
        Socket socket = new Socket(this.host, this.port);
        // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);


        uuid = UUID.fromString(in.readUTF()); // ждем пока сервер отошлет строку текста.
        this.addKeyListener(new ClientKeyListener(uuid, out));


        String line = null;

        while (true) {
//            Message message = new Message(this.uuid, keyCode);
//            out.writeUTF(message.toString); // отсылаем введенную строку текста серверу.
//            out.flush(); // заставляем поток закончить передачу данных.
            line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            world = World.parse(line);
            repaint();
        }

    }

    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g)//
    {
        Graphics2D g2 = (Graphics2D) g.create();//создание экземлара класса работы с графикой
        //g.setsetRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);'
        g2.setColor(Color.white);//выбор белого цвета рисования
        g2.fillRect(0, 0, getWidth(), getHeight());//заполнение всего поля белым цветом
        world.draw(g2);
    }
}
