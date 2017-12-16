package snakegame.structs;

import java.awt.*;

public class ApplePoint extends Point {
    static int step_size = 9;
    static Color color = Color.red;
    public ApplePoint(Remainder x, Remainder y) {
        super(x, y);
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillArc(x.getValue(), y.getValue(), step_size, step_size, 0, 360);
        g.setColor(Color.red);
        g.drawArc(x.getValue(), y.getValue(), step_size,step_size, 0, 360);
    }
}
