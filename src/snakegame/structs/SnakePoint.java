package snakegame.structs;

import java.awt.*;

public class SnakePoint extends Point {
    static Color color = Color.BLACK;
    static int step_size = 9;

    public SnakePoint(Remainder x, Remainder y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillArc(x.getValue() * step_size, y.getValue() * step_size, step_size, step_size, 0, 360);
        g.setColor(color);
        g.drawArc(x.getValue() * step_size, y.getValue() * step_size, step_size, step_size, 0, 360);
    }
}
