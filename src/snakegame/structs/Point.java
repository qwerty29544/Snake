package snakegame.structs;

import java.awt.*;

public abstract class Point {
    protected Remainder x;//кастомный класс целочисленного значения
    protected Remainder y;//кастомный класс целочисленного значения


    //конструктор класса точки
    public Point(Remainder x, Remainder y) {
        this.x = x;//установка значений
        this.y = y;//установка значений
    }

    //геттер точки Х
    public Remainder getX() {
        return x;
    }

    //сеттер точки Х
    public void setX(Remainder x) {
        this.x = x;
    }

    //геттер точки У
    public Remainder getY() {
        return y;
    }

    //сеттер точки У
    public void setY(Remainder y) {
        this.y = y;
    }

    //абстрактный метод рисования
    public abstract void draw(Graphics2D g);

    //сравнение точек
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (!x.equals(point.x)) return false;
        return y.equals(point.y);
    }

    //хэшкод точек
    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    //перевод данных в строковый вид
    @Override
    public String toString() {
        return x.toString() + "," + y;
    }
}
