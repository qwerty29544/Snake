package snakegame.server;

import snakegame.structs.World;

import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private World world;

    private Queue<KeyEvent> events = new LinkedBlockingQueue<>();

    public void run() {

    }
}
