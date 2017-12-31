package snakegame.structs;

import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;

import java.awt.event.KeyEvent;

public enum Direction {
    up,
    right,
    down,
    left;

    public Direction newDirection(KeyEvent event) {
        return newDirection(event.getKeyCode());
    }

    public Direction newDirection(int keyCode){
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                if (this != Direction.left) {
                    return Direction.right;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (this != Direction.right) {
                    return Direction.left;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this != Direction.up) {
                    return Direction.down;
                }
                break;
            case KeyEvent.VK_UP:
                if (this != Direction.down) {
                    return Direction.up;
                }
                break;
            default:
                return this;
        };
        return this;
    }
}
