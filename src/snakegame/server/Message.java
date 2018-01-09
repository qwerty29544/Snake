package snakegame.server;

import com.sun.nio.sctp.IllegalReceiveException;

import java.util.UUID;

public class Message {
    public int getKeyCode() {
        return keyCode;
    }//(геттер) поле с кодом ключа, введенного пользователем

    public UUID getUuid() {
        return uuid;
    }//(геттер) поле с уникальным идентификатором пользователя

    private int keyCode;//поле с кодом клавиши клиента
    private UUID uuid;//поле с уникальным идентификатором пользователя

    //конструктор класса сообщения данных
    public Message(int keyCode, UUID uuid) {
        this.keyCode = keyCode;//установка значения
        this.uuid = uuid;//установка значения
    }

    //интерпретация сообщения в текстовом мире в вид класса сообщения
    public static Message parse(String string) throws IllegalArgumentException {
        String[] tokens = string.split(" ");//установка разделителей
        if (tokens.length == 2)
            return new Message(Integer.parseInt(tokens[1]), UUID.fromString(tokens[0]));//получение из строки новой переменной класса сообщения клиента о номере нажатой клавиши
        else
            throw new IllegalReceiveException("IllegalReceiveException Message parse");//ошибка введенного значения
    }

    @Override
    public String toString() {
        return uuid.toString() + " " + keyCode;
    }//интерпертация сообщения в текстовый вид
}
