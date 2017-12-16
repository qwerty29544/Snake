package snakegame.structs;

import java.awt.*;

public class Apple {
    private ApplePoint point;

    public ApplePoint getPoint() {
        return point;
    }

    public void draw(Graphics2D g) {
        point.draw(g);
    }
}
