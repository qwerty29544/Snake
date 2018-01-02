package snakegame.structs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AppleFactory {

    List<ApplePoint> points;
    int maxX;
    int maxY;
    int randomEncounters;

    static Random random = new Random();
    public AppleFactory(List<ApplePoint> points){
        this.maxX = 100;
        this.maxY = 100;
        this.randomEncounters = 5;
        this.points = points;
    }

    public AppleFactory(){
        this.maxX = 100;
        this.maxY = 100;
        this.randomEncounters = 5;
        this.points = new ArrayList<ApplePoint>();
        for (int i=0; i<randomEncounters ; i++){
        points.add(0, new ApplePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY))));
        }
    }

    public Apple generateApple(){
//        ApplePoint point = points.get(random.nextInt(points.size()));
        ApplePoint point = new ApplePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY)));
        return new Apple(point);
    }

}
