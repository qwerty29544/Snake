package snakegame.structs;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public List<Point> getPoints() {
        return points;
    }

    private void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public Snake(LinkedList<Point> points, Direction direction) {
        this.points = points;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    private void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Point getHead(){
        return points.get(0);
    }
    public boolean contains(Point anotherPoint){
        for (Point point:points){
            if (point.equals(anotherPoint)){
                return true;
            }
        }
        return false;
    }
    private void moveHead(){
        Point newHead;
        Point oldHead = points.getFirst();
        switch (direction){
            case up:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                points.addFirst(newHead);
            case down:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                points.addFirst(newHead);
            case right:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                points.addFirst(newHead);
            case left:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                points.addFirst(newHead);
        }
    }
    private void dropTail(){
        points.removeLast();
    }
    public void draw(Graphics2D g){
        for (Point point : points){
            point.draw(g);
        }
    }

    private LinkedList<Point> points;
    private Direction direction;

}
