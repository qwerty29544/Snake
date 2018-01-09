package snakegame.structs;

import snakegame.server.Message;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class World {
    SnakeFactory snakeFactory = new SnakeFactory();//фабрика змеек из класса змеек
    AppleFactory appleFactory = new AppleFactory();//фабрика яблок из класса яблок
    List<Apple> apples;//список яблок в мире
    List<Snake> snakes;//список змеек в мире

    //конструктор класса World (игровое поле)
    public World(List<Apple> apples, List<Snake> snakes) {
        this.apples = apples;//установка значения списка яблок
        this.snakes = snakes;//установка значения списка змеек на поле
    }

    //метод обработчика сообщений от клиента
    public void processMessage(Message message) {
        for(Snake snake: snakes) {//цикл по всем змейкам из списка змеек
            if(snake.uuid.equals(message.getUuid())) {//установка однозначного соответствия по уникальному номеру змейки и сообщения
                snake.setDirection(snake.getDirection().newDirection(message.getKeyCode()));//установка нового значения направления змейки
            }
        }
    }

    //конструктор класса World (игрового поля) по умолчанию
    public World() {
        this.apples = new ArrayList<Apple>();//пустой список яблок
        this.snakes = new ArrayList<Snake>();//пустой список змеек
    }

    //отрисовка объектов на поле
    public void draw(Graphics2D g) {
        for(Snake snake : snakes) {//для всех змеек из списка змеек
            snake.draw(g);//отрисовка змей
        }

        for(Apple apple: apples) {//для всех яблок из списка яблок
            apple.draw(g);//отрисовка яблок
        }
    }

    //метод генерации новых яблок с помощью фабрики AppleFactory и добавления новых в список класса
    public void generateApple() {
        apples.add(appleFactory.generateApple());
    }

    //метод генерации уникальных змеек по уникальному идентификатору пользователя
    public UUID generateSnake() {
        Snake snake = snakeFactory.generateSnake();//метод создания новой змейки с помощью фабрики внутри класса SnakeFactory
        snakes.add(snake);//добавление в список новой змейки
        return snake.getUuid();//вернуть по добавлении уникальный номер змейки пользователя (клиента)
    }

    //метод шага мира (построено на предикатах)
    public void step() {
        ArrayList<Snake> predictions = new ArrayList<Snake>();//список будущих состояний змеек
        for (Snake snake : snakes) {//для всех данных змеек из списка змеек
            predictions.add(snake.predictMove());//добавить в предсказания все имеющиеся змейки
        }
        for (Snake prediction: predictions){//для списка всех предсказаний змеек
            for(Snake otherPrediction:predictions){//прочесать список всех остальных предсказаний змеек
                if (prediction.uuid != otherPrediction.uuid && otherPrediction.contains(prediction.getHead())){//если уникальные номера предсказаний змеек не совпадают и голова высшей совпадает с одной точкой низшей, то
                    snakes.removeIf(snake -> snake.uuid.equals(otherPrediction.uuid));//удалить змейку по условию совпадения при следующем шаге
                }
            }
            if (prediction.ateSelf()) {//если выполняется предсказание собственного поедания
                snakes.removeIf(snake -> snake.uuid.equals(prediction.uuid));//удалить данную змейку при следующем шаге
            }
        }
        predictions.removeIf(prediction -> {
            for(Snake snake: snakes) {
                if (snake.uuid.equals(prediction.uuid))
                    return false;
            }
            return true;
        });//удалить по removeIf только при совпадении данных уникальных идентификаторов
        for(Snake prediction: predictions) {//для всех предсказаний из списка предсказаний
            for(Snake snake: snakes) {//по всем змейкам из списка змеек
                if (snake.uuid.equals(prediction.uuid))//если уникальный номер змейки совпадает с предсказанием
                    if (apples.removeIf(apple -> prediction.contains(apple.getPoint()))) {//проверка удаления яблока при условии совпадения головы одной из них с яблоком
                        snake.moveHead();//простое движение головы змейки
                        generateApple();//генерация нового яблока на поле
                    }
                    else
                        snake.step();//в противном случае, движение головы и отрез хвоста змейки
            }
        }
    }

    //кодирование состояние мира в строковой форме (маршмеллинг)
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();//поле создателя строк
        for(Apple apple: apples) {//для всех яблок из списка яблок
            stringBuilder.append(apple.toString());//добавить в строку состояние яблока
            stringBuilder.append(';');//разделитель
        }
        for(Snake snake: snakes) {//для всех змеек из списка змеек
            stringBuilder.append(snake.toString());//добавить в строку состояние змейки
            stringBuilder.append(';');//разделитель
        }
        return stringBuilder.toString();//перевести строитель строк в строку (стандартный метод класса)
    }

    //интерпретация состояния мира из строки в поля класса
    public static World parse(String string) throws IllegalArgumentException{
        List<Snake> snakes = new ArrayList<Snake>();//объявление списка змеек
        List<Apple> apples = new ArrayList<Apple>();//объявление списка яблок
        String[] objects = string.split(";");//установка разделителей
        for(String obj: objects) {//по всем объектам через разделители
            if(obj.startsWith("S")) {//если объект начинается с "S"
                snakes.add(Snake.parse(obj));//добавить змейку в список змеек через интерпретацию змеек
            } else if(obj.startsWith("A")) {//если объект начинается с "А"
                apples.add(Apple.parse(obj));//добавить яблоко в список яблок через интерпретацию яблок
            }
            else throw new IllegalArgumentException("Cannot parse obj: " + obj);//ошибка невозможности интерпретации строки
        }
        return new World(apples, snakes);//вернуть новый мир, с новым списком змеек и яблок
    }
}
