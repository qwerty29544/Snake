package snakegame.structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class World {
    SnakeFactory snakeFactory = new SnakeFactory();
    List<Apple> apples;
    //TO DO HashMap
    List<Snake> snakes;


    //snakes.add(snakeFactory.generateSnake());

    public World(List<Apple> apples, List<Snake> snakes) {
        this.apples = apples;
        this.snakes = snakes;
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

    public void step() {
        ArrayList<Snake> predictions = new ArrayList<Snake>();
        for (Snake snake : snakes) {
            predictions.add(snake.predictMove());
        }
        for (Snake prediction: predictions){
            for(Snake otherPrediction:predictions){
                if (prediction.uuid != otherPrediction.uuid && otherPrediction.contains(prediction.getHead())){
                    snakes.removeIf(snake -> snake.uuid == otherPrediction.uuid);
                }
            }
        }
        predictions.removeIf(prediction -> {
            for(Snake snake: snakes) {
                if (snake.uuid == prediction.uuid)
                    return false;
            }
            return true;
        });
        for(Snake prediction: predictions) {
            for(Snake snake: snakes) {
                if (snake.uuid == prediction.uuid)
                    if (apples.removeIf(apple -> prediction.contains(apple.getPoint())))
                        snake.moveHead();
                    else
                        snake.step();
            }
        }
    }



//  Добавить логику поедания змейками змеек

        //        Добавить логику поедания яблок
}
