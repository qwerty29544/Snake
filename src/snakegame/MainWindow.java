package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.util.Random;
import static snakegame.Constants.*;


public class MainWindow extends JFrame
{
    private final Random rand = new Random();//переменная, генерирующая случайные события
    private int lastKey = 0;//Переменная знвчения последней нажатой клавиши клавиатуры
    private boolean GameOver = false;//Переменная завершения игры
    private snake snake1 = new snake(rand.nextInt((BORDER_WIDTH/step_size)-1),rand.nextInt((BORDER_LENGHT/step_size)-1), step_size);//Использование конструктора класса змейки для создания начальной точки на поле

    MainWindow (String title)
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Возможность выхода по кнопке выхода в верней части окна
        setSize(BORDER_WIDTH,BORDER_LENGHT);//Установка размеров окна
        setUndecorated(true);
        setResizable(false);//Установка невозможности изменять границы окна во время игры
        //setFocusable(true);


        addKeyListener(new KeyListener() {//Создание конструктора класса обработчика событий нажатия клавиатуры
            @Override
            public void keyTyped(KeyEvent e) {
                System.exit(0);
            }//обработчик события нажатия символа юникод на клавиатуре (выход из программы с кодом 0)

            @Override
            public void keyPressed(KeyEvent e) {
                Input(e);
            }//обработчки нажатия любой клавиши на клавиатуре (отправляемся на обработчик события нажатия клавиши)

            @Override
            public void keyReleased(KeyEvent e) {//обработчик отжатия любой клавиши на клавиатуре

            }
        });
        Thread th = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    snake1.logic();
                    repaint();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        th.start();

    }

    public void Input(KeyEvent event) {//обработчик события нажатия клавиши
        //Snake snakegame.snake = getSnake();
        switch (event.getKeyCode()) {//распознаём какая клавиша была нажата, и совершаем действия в зависимости от конкретных ситуаций
            case KeyEvent.VK_RIGHT://нажата клавиша: стрелка вправо
                if (lastKey != KeyEvent.VK_LEFT && !GameOver) {//условие, если последней клавишей не была нажата левая стрелка и игра не завершилась
                    snake1.setDirection(snake.RIGHT);//установка движения змейки вправо
                    lastKey = KeyEvent.VK_RIGHT;//установка пременной последней нажатой клавиши в значение правой стрелки
                }
                break;//завершение обработчика
            case KeyEvent.VK_LEFT://нажата клавиша: стрелка влево
                if (lastKey != KeyEvent.VK_RIGHT && !GameOver) {//условие, если последней клавишей не была нажата правая стрелка и игра не завершилась
                    snake1.setDirection(snake.LEFT);//установка направления движения змейки влево
                    lastKey = KeyEvent.VK_LEFT;//установка значения переменной последней нажатой клавиши в значение левой стрелки
                }
                break;//завершение обработчика
            case KeyEvent.VK_DOWN://нажата клавиша: стрелка вниз
                if (lastKey != KeyEvent.VK_UP && !GameOver) {//условие, если последней клавишей не была нажата стрелка вверх и игра не завершилась
                    snake1.setDirection(snake.DOWN);//установка напрвления движения змейки вниз
                    lastKey = KeyEvent.VK_DOWN;//установка значения переменной последней нажатой клавиши в значение стрелки вниз
                }
                break;//завершение обработчика
            case KeyEvent.VK_UP://нажата клавиша: стрелка вверх
                if (lastKey != KeyEvent.VK_DOWN && !GameOver) {//условие, если последней клавишей не была нажата стрелка вниз и игра не завершилась
                    snake1.setDirection(snake.UP);//установка направления движения змейки вверх
                    lastKey = KeyEvent.VK_UP;//установка значения переменной помлденей нажатой клавиши в значение стрелки вверх
                }
                break;//завершение обработчика
            case KeyEvent.VK_ESCAPE://нажата клавиша ESC
                System.exit(0);//выход из игры с кодом 0
                break;//завершение обработчика
        }
        snake1.logic();//реализация функции логика работы класса змейки
        repaint();//Перерисовка экрана игры
    }

    public void paint(Graphics g)//
    {
        Graphics2D g2 = (Graphics2D) g.create();//создание экземлара класса работы с графикой
        //g.setsetRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);'
        g.setColor(Color.white);//выбор белого цвета рисования
        g.fillRect(0, 0, getWidth(), getHeight());//заполнение всего поля белым цветом
        snake1.paint(g2);//вызов функции изначально установленной прорисовки змейки
    }
}
