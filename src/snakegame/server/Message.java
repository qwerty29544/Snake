package snakegame.server;

import com.sun.nio.sctp.IllegalReceiveException;

import java.util.UUID;

public class Message {
    public int getKeyCode() {
        return keyCode;
    }

    public UUID getUuid() {
        return uuid;
    }

    private int keyCode;
    private UUID uuid;

    public Message(int keyCode, UUID uuid) {
        this.keyCode = keyCode;
        this.uuid = uuid;
    }

    public static Message parse(String string) throws IllegalArgumentException {
        String[] tokens = string.split(" ");
        if (tokens.length == 2)
            return new Message(Integer.parseInt(tokens[1]), UUID.fromString(tokens[0]));
        else
            throw new IllegalReceiveException("poshel nahuy so svoim futbolom");
    }

    @Override
    public String toString() {
        return uuid.toString() + " " + keyCode;
    }
}
