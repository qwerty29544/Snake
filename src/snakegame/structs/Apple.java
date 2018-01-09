package snakegame.structs;

import java.awt.*;
import java.util.Objects;

public class Apple {
    //конструктор класса яблока через класс ApplePoint
    public Apple(ApplePoint point) {
        this.point = point;
    }

    private ApplePoint point;//поле класса через ApplePoint

    public ApplePoint getPoint() {
        return point;
    }//геттер поля

    public void draw(Graphics2D g) {
        point.draw(g);
    }//отрисовка яблока

    //перевод  данных о классе в строку (маршмеллинг)
    public String toString() {
        StringBuilder builder = new StringBuilder();//строитель строк
        builder.append("A,");//добавление А с разделителем между данными о яблоке
        builder.append(point.getX());//добавление Х из точки яблока
        builder.append(",");//добавление разделителя между Х и У
        builder.append(point.getY());//добавление У из точки яблока
        return builder.toString();//вернуть строку спиленную из данных

    }

    //интерпретация данных из строки в данные поля
    public static Apple parse(String string) {
        String[] tokens = string.split(",");//распознавание разделителя
        Point point;//точка яблока
        if(Objects.equals(tokens[0], "A")){//если первый токен есть А
            return new Apple(new ApplePoint(new Remainder(Integer.parseInt(tokens[1])), new Remainder(Integer.parseInt(tokens[2]))));//вернуть данные о новом яблоки через токен 1 и токен 2, соответственно Х и У
        }
        throw new IllegalArgumentException("Illegal format for Apple");//ошибка обработчика интерпретации
    }
}
