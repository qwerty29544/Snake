package snakegame.structs;

import java.awt.*;
import java.util.Objects;

public class Apple {
    public Apple(ApplePoint point) {
        this.point = point;
    }

    private ApplePoint point;

    public ApplePoint getPoint() {
        return point;
    }

    public void draw(Graphics2D g) {
        point.draw(g);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("A,");
        builder.append(point.getX());
        builder.append(",");
        builder.append(point.getY());
        return builder.toString();

    }

    public static Apple parse(String string) {
        String[] tokens = string.split(",");
        Point point;
        if(Objects.equals(tokens[0], "A")){
            return new Apple(new ApplePoint(new Remainder(Integer.parseInt(tokens[1])), new Remainder(Integer.parseInt(tokens[2]))));
        }
        throw new IllegalArgumentException("Illegal format for Apple");
    }
}
