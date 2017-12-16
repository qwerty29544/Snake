package snakegame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class snake extends Constants {//описание класса змейка

    private ArrayList<Point> snake_body = new ArrayList<Point>(5);//переменная авторасширяющегося массива точек змейки
    private Point Apple = new Point();//точка для яблока
    private Point lastApple = new Point();//точка последнего яблока
    private int step_size;//переменная размер шага
    private int snake_direction = 0;//переменная направления движения
    private final Random rand = new Random();//переменная, генерирующая случайные события
    private boolean GameOver = false;//Переменная завершения игры


    snake(int x0, int y0, int step) {//конструктор змейки
        apple ap=new apple();
        step_size = step;//значение шага устанавливается из фактического параметра конструктора
        int x = x0 * step + shift;//положение змейки в пространстве по координате х
        int y = y0 * step + shift;//положение змейки в пространстве по координате у
        for (int i = 0; i < 1; i++) {//цикл для добаление точки в тело змейки
            snake_body.add(new Point(x, y));//функция добавление в тело змейки точки
        }
            Apple=ap.apple1();
            //Apple.x = step * rand.nextInt(48) + step_size;//координаты яблока по х
            //Apple.y = step * rand.nextInt(35) + step_size;//координаты яблока по у

    }
    public int getDirection() {
        return snake_direction;
    }//геттер для получения направления движения

    public void setDirection(int direction) {
        this.snake_direction = direction;
    }//установка напрвления движения змейки

    public Point head (){
        return snake_body.get(0);
    }
    public ArrayList<Point> body(){
        return snake_body;
    }

    public void logic(){//логика работы движения змейки

        int length = snake_body.size();//получить размер массива точек змейки
        Point head = snake_body.get(0);//получение координат головы змейки
        Point last = snake_body.get(length-1);//получение последнего элемента в теле змейки

        for(int i=1; i<snake_body.size();i++){//цикл работы с телом змейки
            snake_body.set(length-i,snake_body.get(length-i-1) );//построение тела змейки по имеющемуся массиву точек
        }

       if(!GameOver) {
           switch (snake_direction) {
               case UP:
                   head = new Point(head.x, head.y - step_size);//объявление новой головы с новыми координатами при повороте змейки наверх
                   break;
               case DOWN:
                   head = new Point(head.x, head.y + step_size);//объявление новой головы с новыми координатами при движении змейки вниз
                   break;
               case LEFT:
                   head = new Point(head.x - step_size, head.y);//объявление новой головы с новыми координатами при движении змейки влево
                   break;
               case RIGHT:
                   head = new Point(head.x + step_size, head.y);//объявление новой головы с новыми координатами при движении змейки вправо
                   break;
           }
           //snake_body.add(0,head);
           snake_body.set(0, head);//установка нового значения точки для головы
       }

        if(head.x==Apple.x && head.y==Apple.y ){//проверка столкновения головы с яблоком
            //lastApple= Apple;
            Apple.x = step_size*rand.nextInt((BORDER_WIDTH/step_size)-step_size)+step_size;//новая координата по х для яблока при столкновении с головой змеи
            Apple.y = step_size*rand.nextInt((BORDER_LENGHT/step_size)-step_size)+step_size;//новая координата по у для яблока при столкновении с головой змеи
            snake_body.add(last);//добавление точки в конец массива змейки
        }

        if(this.snake_direction==RIGHT && head.x>=(BORDER_WIDTH)){
            head = new Point(0,head.y);
            snake_body.set(0,head);//установка нового значения точки для головы
        }

        if(this.snake_direction==LEFT && head.x<=0){
            head = new Point(BORDER_WIDTH-step_size,head.y);
            snake_body.set(0,head);//установка нового значения точки для головы
        }

        if(this.snake_direction==UP && head.y<=0){
            head = new Point(head.x,BORDER_LENGHT-step_size);
            snake_body.set(0,head);//установка нового значения точки для головы
        }

        if(this.snake_direction==DOWN && head.y>=(BORDER_LENGHT)){
            head = new Point(head.x,0);
            snake_body.set(0,head);//установка нового значения точки для головы
        }

        for(int i=1; i<length-1; i++) {
            if (snake_body.get(0).equals(snake_body.get(i))) {
                GameOver=true;
                JOptionPane.showMessageDialog(null, "snakegame.Game over\nYour score:"+length);

                System.exit(0);
            }
        }


    }



    public void paint(Graphics2D g2){
        for(Point p:snake_body){//установить графические опции для тела змейки
            g2.setColor(Color.black);//черный цвет тела
            g2.fillArc(p.x, p.y, step_size, step_size, 0, 360);//закрасить вокруг точки диаметр шага
            g2.setColor(Color.black);//черный цвет тела
            g2.drawArc(p.x, p.y, step_size, step_size, 0, 360);//нарисовать круг для точки тела змейки
        }
        /*Point p = snake_body.get(snake_body.size() - 1);
        g2.setColor(Color.black);
        g2.fillArc(p.x + step_size / 2 - 2, p.y + step_size / 2 - 2, 4, 4, 0, 360);
        g2.setColor(Color.white);
        g2.fillArc(p.x + step_size / 2 - 1, p.y + step_size / 2 - 1, 2, 2, 0, 360);*/

        Point p = Apple;//точка яблока
        g2.setColor(Color.red);//установить красный цвет рисования
        g2.fillArc(p.x, p.y, step_size, step_size, 0, 360);//закрасить окружность вокруг точки с диаметром шага
        g2.setColor(Color.red);//установить красный цвет рисования
        g2.drawArc(p.x, p.y, step_size, step_size, 0, 360);//разрисовка
    }

}
