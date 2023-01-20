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
        if (!collision_down()) {
            for (int i = 0; i != 4; i++) {//задаются новые координаты по y
                s[i].set_y(s[i].get_y() + 1);
                s[i].set_draw_y(s[i].get_draw_y() + Square.side_of_square + 3);
            }
        }
    }
    public void rotation(int k) {
        int x_c = 0, y_c = 0;
        int x = 0, y = 0;
        int coord[][] = new int[4][2];
        if (form == 0 || form == 1 || form == 3 || form == 5 || form == 4) {
            x_c = s[1].get_x();
            y_c = s[1].get_y();
        }
        if (form == 6) {
            x_c = s[2].get_x();
            y_c = s[2].get_y();
        }
        if (form != 2) {
            if (k == 1) {
                for (int i = 0; i != 4; i++) {
                    x = x_c - (s[i].get_y() - y_c);
                    y = y_c + (s[i].get_x() - x_c);
                    s[i].set_x(x);
                    s[i].set_draw_x(s[i].get_x() * Square.side_of_square + 113 + 3 * (s[i].get_x()));
                    s[i].set_y(y);
                    s[i].set_draw_y(207 + s[i].get_y() * Square.side_of_square+3*(s[i].get_y()));
                }

            }
            if (k == 2) {
                for (int i = 0; i != 4; i++) {
                    x = x_c + (s[i].get_y() - y_c);
                    y = y_c - (s[i].get_x() - x_c);
                    s[i].set_x(x);
                    s[i].set_draw_x(s[i].get_x() * Square.side_of_square + 113 + 3 * (s[i].get_x()));
                    s[i].set_y(y);
                    s[i].set_draw_y(207 + s[i].get_y() * Square.side_of_square+3*(s[i].get_y()));
                }
            }
        }
    }
    public boolean collision_down(){
        for (int i=0;i!=4;i++) {
            if (s[i].get_y() == 19) {
                return true;
            }
        }
        return false;
    }
}
