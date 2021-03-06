package snakegame.structs;

import java.awt.*;

public class ApplePoint extends Point {
    static int step_size = 9;//размер шага
    static Color color = Color.red;//цвет яблока
    public ApplePoint(Remainder x, Remainder y) {
        super(x, y);
    }//конструктор суперкласса из класса Remainder

    //отрисовка точки яблока
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillArc(x.getValue() * step_size, y.getValue() * step_size, step_size, step_size, 0, 360);
        g.setColor(Color.red);
        g.drawArc(x.getValue() * step_size, y.getValue() * step_size, step_size, step_size, 0, 360);
    }
}
