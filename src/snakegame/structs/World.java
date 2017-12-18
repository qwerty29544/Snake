package snakegame.structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    List<Apple> apples;
    List<Snake> snakes;

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
        for (Snake snake : snakes) {
            snake.moveHead();
        }
    }


//  Добавить логику поедания змейками змеек
        //        Добавить логику поедания яблоr
}
