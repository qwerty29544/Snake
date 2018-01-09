package snakegame.structs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AppleFactory {

    List<ApplePoint> points;//список яблок
    int maxX;//максимальный Х для поля
    int maxY;//максимальный У для поля
    int randomEncounters;//случайное значение для фабрики

    static Random random = new Random();//рандомайзер
    //конструктор класса
    public AppleFactory(List<ApplePoint> points){
        this.maxX = 100;//максимальный Х
        this.maxY = 100;//максимальный У
        this.randomEncounters = 5;//энкаунтеры
        this.points = points;//вводимый список точек
    }

    //конструктор по умолчанию
    public AppleFactory(){
        this.maxX = 100;
        this.maxY = 100;
        this.randomEncounters = 5;
        this.points = new ArrayList<ApplePoint>();//пустой лист точек
        for (int i=0; i<randomEncounters ; i++){//цикл из 5 энкаунтеров
        points.add(0, new ApplePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY))));//добавить случайную точку
        }
    }

    //метод обычной генерации одного яблока
    public Apple generateApple(){
        ApplePoint point = new ApplePoint(new Remainder(random.nextInt(maxX)),new Remainder(random.nextInt(maxY)));//добавить случайную точку
        return new Apple(point);//возвращение точки после ее генерации
    }

}
