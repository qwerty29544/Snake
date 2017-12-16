package snakegame;

import java.awt.*;
import java.util.Random;

public class apple extends Constants {

    private Point ap = new Point();
    private final Random rand = new Random();
    private int step_size=20;
    private int shift=0;
    apple()

    {

        ap.x = step_size*rand.nextInt(((BORDER_WIDTH)/step_size)-2) + step_size + shift;
        ap.y = step_size*rand.nextInt(((BORDER_LENGHT)/step_size)-2) + step_size + shift ;
        if (ap.x<step_size*2||ap.x>BORDER_WIDTH-step_size||ap.y<step_size*2||ap.y>BORDER_LENGHT-step_size)
        {apple ap1=new apple();
            ap1.set();
            ap=ap1.apple1();}
    }
    public Point apple1(){return ap;}
    public void set(){
        ap.x = step_size*rand.nextInt(((BORDER_WIDTH)/step_size)-2) + step_size + shift;
        ap.y = step_size*rand.nextInt(((BORDER_LENGHT)/step_size)-2) + step_size + shift ;
        if (ap.x<step_size*2||ap.x>BORDER_WIDTH-step_size||ap.y<step_size*2||ap.y>BORDER_LENGHT-step_size)
        {apple ap1=new apple();
            ap1.set();
            ap=ap1.apple1();}
    }
    public  void set(boolean a){
        ap.x=-100;
        ap.y=-100;}

    public void set(apple ap2){
        ap.x = step_size*rand.nextInt(((BORDER_WIDTH)/step_size)-2) + step_size + shift;
        ap.y = step_size*rand.nextInt(((BORDER_LENGHT)/step_size)-2) + step_size + shift ;
        if (ap.x<step_size*2||ap.x>BORDER_WIDTH-step_size||ap.y<step_size*2||ap.y>BORDER_LENGHT-step_size||(ap==ap2.apple1()))
        {apple ap1=new apple();
            ap1.set();
            ap=ap1.apple1();}}
    public void paint(Graphics2D g2){

        Point p = ap;
        g2.setColor(Color.red);
        g2.fillArc(p.x, p.y, step_size, step_size, 0, 360);
        g2.setColor(Color.red);
        g2.drawArc(p.x, p.y, step_size,step_size, 0, 360);
    }
}
