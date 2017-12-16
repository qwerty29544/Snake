package snakegame;


import java.util.ArrayList;
import java.util.List;

public class Game {
    public static void main(String [] args) {
//        MainWindow MW1 = new MainWindow("ssnake!!!");//Создание окна с игрой
//        MW1.setVisible(true);//Установка видимости окна

        class Point {
            public int i;
            public Point(int i) {
                this.i = i;
            }
        }
        List<Point> list1 = new ArrayList<Point>();
        list1.add(new Point(1));
        list1.add(new Point(1));
        list1.add(new Point(1));
        list1.add(new Point(1));
        List<Point> list2 = new ArrayList<Point>(list1);
        for(Point point: list2) {
            point.i++;
        }
        for(Point point: list1){
            System.out.println(point.i);
        }
    }
}
