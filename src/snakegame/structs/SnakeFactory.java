package snakegame.structs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeFactory{
    List<Point> points;
    int maxX;
    int maxY;
    int randomEncounters;

    static Random random = new Random();
    public SnakeFactory(List<Point> points){
        this.maxX = 100;
        this.maxY = 100;
        this.randomEncounters = 5;
        this.points = points;
    }

    public SnakeFactory(){
        this.maxX = 100;
        this.maxY = 100;
        this.randomEncounters = 5;
        this.points = new ArrayList<Point>();
        for (int i=0; i<randomEncounters ; i++){
        points.add(0, new SnakePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY))));
        }
    }

    public Snake generateSnake(){
        Direction direction = Direction.right;
        LinkedList<Point> snakePoints = new LinkedList<Point>();
//        snakePoints.add(points.get(random.nextInt(points.size())));
        snakePoints.add(new SnakePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY))));
        Snake snake = new Snake(snakePoints,direction);
        snake.moveHead();
        return snake;
    }

}
