package snakegame.structs;

import java.awt.*;

public abstract class Point {
    protected Remainder x;
    protected Remainder y;


    public Point(Remainder x, Remainder y) {
        this.x = x;
        this.y = y;
    }

    public Remainder getX() {
        return x;
    }

    public void setX(Remainder x) {
        this.x = x;
    }

    public Remainder getY() {
        return y;
    }

    public void setY(Remainder y) {
        this.y = y;
    }
    public abstract void draw(Graphics2D g);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (!x.equals(point.x)) return false;
        return y.equals(point.y);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
