package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;

public class Shapes {//класс фигуры
    private int form;//номер фигуры
    public Square s[];//массив квдратов фигуры
    private int matrix_form[][];//массив для создания фигуры
    int cell_number = (int) (Math.random() * 9);//квадрат верхней строки, от которого начнется
    //отрисовка фигуры
    public Shapes(Context context) {
        form = (int) (Math.random() * 6);//случайное получение фигуры
        Square one_square = new Square(context);
        s =new Square[4];//создание квадратов фигуры
        for (int i=0;i!=4;i++){
            s[i]=new Square(one_square);
        }
        int color = s[0].get_color_num() + 1;
        if (form == 0) matrix_form = new int[][]{{color, color, color, color}, {0, 0, 0, 0}};//I
        if (form == 1) matrix_form = new int[][]{{color, color, color, 0}, {0, color, 0, 0}};//T
        if (form == 2) matrix_form = new int[][]{{color, color, 0, 0}, {color, color, 0, 0}};//O
        if (form == 3) matrix_form = new int[][]{{color, color, color, 0}, {0, 0, color, 0}};//L
        if (form == 4) matrix_form = new int[][]{{0, 0, color, 0}, {color, color, color, 0}};//J
        if (form == 5) matrix_form = new int[][]{{color, color, 0, 0}, {0, color, color, 0}};//S
        if (form == 6) matrix_form = new int[][]{{0, color, color, 0}, {color, color, 0, 0}};//Z
    }
    public void shape_first_appear(Canvas c) {//первое появление фигуры
        int k = 0;
        for (int i = 0; i != 2; i++) {
            for (int j = 0; j != 4; j++) {
                if (matrix_form[i][j] != 0) { //если в массиве фигуры этот элемент - клетка, то задаются координаты появления
                    s[k].set_x(cell_number + i);
                    s[k].set_y(j);
                    s[k].set_draw_x(s[k].get_x() * Square.side_of_square + 113 + 3 * (cell_number));
                    s[k].set_draw_y(207 + s[k].get_y() * Square.side_of_square + 3 * (s[k].get_y()));
                    k = k + 1;
                }
            }
        }
    }
    public void draw_shape(Canvas c) {//отрисовка фигуры
        for (int i = 0; i != 4; i++)
            c.drawBitmap(s[i].getSprite_square(), s[i].get_draw_x(), s[i].get_draw_y(), null);
    }
    public void movement_vertically() {//движение по вертикали
        for (int i=0;i!=4;i++) {//задаются новые координаты по y
            s[i].set_y(s[i].get_y() + 1);
            s[i].set_draw_y(s[i].get_draw_y() + Square.side_of_square + 3);
        }
    }
}