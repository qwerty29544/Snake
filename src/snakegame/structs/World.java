package snakegame.structs;

import snakegame.server.Message;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class World {
    SnakeFactory snakeFactory = new SnakeFactory();
    AppleFactory appleFactory = new AppleFactory();
    List<Apple> apples;
    //TO DO HashMap
    List<Snake> snakes;


    //snakes.add(snakeFactory.generateSnake());

    public World(List<Apple> apples, List<Snake> snakes) {
        this.apples = apples;
        this.snakes = snakes;
    }

    public void processMessage(Message message) {
        for(Snake snake: snakes) {
            if(snake.uuid.equals(message.getUuid())) {
                snake.setDirection(snake.getDirection().newDirection(message.getKeyCode()));
            }
        }
    }

    public World() {
        this.apples = new ArrayList<Apple>();
        this.snakes = new ArrayList<Snake>();
    }

    public void draw(Graphics2D g) {
        for(Snake snake : snakes) {
            snake.draw(g);
        }

        for(Apple apple: apples) {
            apple.draw(g);
        }
    }

//    public static World parse(String message) {
//
//    }

    public void generateApple() {
        apples.add(appleFactory.generateApple());
    }

    public UUID generateSnake() {
        Snake snake = snakeFactory.generateSnake();
        snakes.add(snake);
        return snake.getUuid();
    }

    public void step() {
        ArrayList<Snake> predictions = new ArrayList<Snake>();
        for (Snake snake : snakes) {
            predictions.add(snake.predictMove());
        }
        for (Snake prediction: predictions){
            for(Snake otherPrediction:predictions){
                if (prediction.uuid != otherPrediction.uuid && otherPrediction.contains(prediction.getHead())){
                    snakes.removeIf(snake -> snake.uuid.equals(otherPrediction.uuid));
                }
            }
            if (prediction.ateSelf()) {
                snakes.removeIf(snake -> snake.uuid.equals(prediction.uuid));
            }
        }
        predictions.removeIf(prediction -> {
            for(Snake snake: snakes) {
                if (snake.uuid.equals(prediction.uuid))
                    return false;
            }
            return true;
        });
        for(Snake prediction: predictions) {
            for(Snake snake: snakes) {
                if (snake.uuid.equals(prediction.uuid))
                    if (apples.removeIf(apple -> prediction.contains(apple.getPoint()))) {
                        snake.moveHead();
                        generateApple();
                    }
                    else
                        snake.step();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Apple apple: apples) {
            stringBuilder.append(apple.toString());
            stringBuilder.append(';');
        }
        for(Snake snake: snakes) {
            stringBuilder.append(snake.toString());
            stringBuilder.append(';');
        }
        return stringBuilder.toString();
    }

    public static World parse(String string) throws IllegalArgumentException{
        List<Snake> snakes = new ArrayList<Snake>();
        List<Apple> apples = new ArrayList<Apple>();
        String[] objects = string.split(";");
        for(String obj: objects) {
            if(obj.startsWith("S")) {
                snakes.add(Snake.parse(obj));
            } else if(obj.startsWith("A")) {
                apples.add(Apple.parse(obj));
            }
            else throw new IllegalArgumentException("Cannot parse obj: " + obj);
        }
        return new World(apples, snakes);
    }

    //  Добавить логику поедания змейками змеек

        //        Добавить логику поедания яблок
}
