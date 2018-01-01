package snakegame.structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake {
    UUID uuid;


    public List<Point> getPoints() {
        return points;
    }

    private void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public Snake(LinkedList<Point> points, Direction direction) {
        this.points = points;
        this.direction = direction;
        this.uuid = UUID.randomUUID();
    }

    public Snake(UUID uuid, LinkedList<Point> points, Direction direction) {
        this.uuid = uuid;
        this.points = points;
        this.direction = direction;
    }

    public Snake(Snake snake) {
        this.direction = snake.direction;
        this.points = new LinkedList<Point>(snake.points);
        this.uuid = snake.uuid;
    }

    public Snake predictMove() {
        Snake prediction = new Snake(this);
        prediction.step();
        return prediction;
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

    void step() {
        moveHead();
        dropTail();
    }

    public void moveHead(){
        Point newHead;
        Point oldHead = points.getFirst();
        switch (direction){
            case up:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                break;
            case down:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                break;
            case right:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                break;
            case left:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                break;
            default:
                newHead = oldHead;
        }
        points.addFirst(newHead);
    }

    public static Snake parse(String string) throws IllegalArgumentException {
        String[] tokens = string.split(",");
        Direction direction;
        LinkedList<Point> points = new LinkedList<Point>();
        if (Objects.equals(tokens[0], "S")) {
            UUID uuid = UUID.fromString(tokens[1]);
            int length = Integer.parseInt(tokens[2]);
            int j = 3;
            for (int i = 0; i < length; i++) {
                SnakePoint point = new SnakePoint(new Remainder(Integer.parseInt(tokens[j])),
                                                  new Remainder(Integer.parseInt(tokens[j+1])));
                points.add(point);
                j += 2;
            }
            direction = Direction.valueOf(tokens[j]);
            return new Snake(uuid, points, direction);
        }
        else throw new IllegalArgumentException("huynya snake message");
    }

    private void dropTail(){
        points.removeLast();
    }

    public void draw(Graphics2D g){
        for (Point point : points){
            point.draw(g);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("S,");
        builder.append(uuid.toString());
        builder.append(',');
        builder.append(points.size());
        builder.append(',');
        for(Point point : points) {
            builder.append(point.toString());
            builder.append(',');
        }
        builder.append(direction.toString());
        return builder.toString();
    }

    private LinkedList<Point> points;
    private Direction direction;

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<Apple>();
        List<Snake> snakes = new ArrayList<Snake>();
        Apple apple = new Apple(new ApplePoint(new Remainder(22), new Remainder(33)));
        Snake snake = new SnakeFactory().generateSnake();
        apples.add(apple);
        snakes.add(snake);
        World world = new World(apples, snakes);
        System.out.println(world.toString());
        System.out.println(World.parse(world.toString()));
    }
}
