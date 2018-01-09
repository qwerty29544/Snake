package snakegame.structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Snake {
    public UUID getUuid() {
        return uuid;
    }//геттер уникального идентификатора пользователя

    UUID uuid;//поле с уникальным идентификатором

    public List<Point> getPoints() {
        return points;
    }//геттер списка точек

    private void setPoints(LinkedList<Point> points) {
        this.points = points;
    }//сеттер списка точек

    //конструктор класса змейки
    public Snake(LinkedList<Point> points, Direction direction) {
        this.points = points;
        this.direction = direction;
        this.uuid = UUID.randomUUID();//случайный идентификатор
    }

    //конструктор класса змейки с уникальным идентификатором пользователя
    public Snake(UUID uuid, LinkedList<Point> points, Direction direction) {
        this.uuid = uuid;
        this.points = points;
        this.direction = direction;
    }

    //конструктор копирования
    public Snake(Snake snake) {
        this.direction = snake.direction;
        this.points = new LinkedList<Point>(snake.points);
        this.uuid = snake.uuid;
    }

    //метод движения предсказания змейки
    public Snake predictMove() {
        Snake prediction = new Snake(this);//объявление предикаты
        prediction.step();//шаг предикаты
        return prediction;//возвращение предикаты
    }

    //геттер для направления движения змейки
    public Direction getDirection() {
        return direction;
    }

    //сеттер для направления движения змейки
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //геттер для получения координаты головы змейки
    public Point getHead(){
        return points.get(0);
    }

    //проверка на содержание в точке другой точки
    public boolean contains(Point anotherPoint){
        for (Point point:points){//для списка всех точек
            if (point.equals(anotherPoint)){//проверка на наличие другой точки
                return true;
            }
        }
        return false;
    }

    //метод для проверки самопоедания
    public boolean ateSelf() {
        if (points.isEmpty())//если список точек пуст
            return false;//не проверять
        Iterator<Point> pointIterator = points.iterator();//счетчик по телу змейки
        Point head = pointIterator.next();//голова начало счетчика
        while(pointIterator.hasNext()) {//если у счетчика есть следующее значение, будет идти по всему телу
            if (head.equals(pointIterator.next()))//проверка на схожесть координат головы и следующей точки
                return true;
        }
        return false;
    }

    //движение головы и отрубание хвоста
    void step() {
        moveHead();
        dropTail();
    }

    //движение головы, построенное на кострукторах копирования(точек) новой головы
    public void moveHead(){
        Point newHead;//точка новой головы
        Point oldHead = points.getFirst();//точка старой головы
        switch (direction){//переключатель между направлениями
            case up:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(-1));
                break;
            case down:
                newHead = new SnakePoint(oldHead.getX(), oldHead.getY().copyAdd(1));
                break;
            case right:
                newHead = new SnakePoint(oldHead.getX().copyAdd(1), oldHead.getY());
                break;
            case left:
                newHead = new SnakePoint(oldHead.getX().copyAdd(-1), oldHead.getY());
                break;
            default:
                newHead = oldHead;
        }
        points.addFirst(newHead);//добавить новую голову
    }

    //интерпретаци данных о змейке из текстового вида
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
        else throw new IllegalArgumentException("IllegalArgumentException Snake parse");
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
}
