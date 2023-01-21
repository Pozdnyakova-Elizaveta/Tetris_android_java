package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            c.drawBitmap(s[i].get_sprite_square(), s[i].get_draw_x(), s[i].get_draw_y(), null);
    }
    public void movement_vertically(int[][] grid) {//движение по вертикали
        if (!collision_down(grid)) {//если возможно движение вниз
            for (int i = 0; i != 4; i++) {//координата y увеличивается на 1
                s[i].set_y(s[i].get_y() + 1);
                s[i].set_draw_y(s[i].get_draw_y() + Square.side_of_square + 3);
            }
        }
    }

    public void movement_horizontal(int last_x,int[][] grid) {//движение по горизонтали
        if (last_x == 1 && !collision_right(grid)) {//если передана единица и движение вправо возможно
            for (int i = 0; i != 4; i++) {//координата x увеличивается на 1
                s[i].set_x(s[i].get_x() + 1);
                s[i].set_draw_x(s[i].get_draw_x() + Square.side_of_square + 3);
            }
        }
        if (last_x == 2 && !collision_left(grid)) {//если передана двойка и движение влево возможно
            for (int i = 0; i != 4; i++) {//координата x уменьшается на 1
                s[i].set_x(s[i].get_x() - 1);
                s[i].set_draw_x(s[i].get_draw_x() - Square.side_of_square - 3);
            }
        }
    }

    public boolean rotation(int k) {//поворот фигуры
        int x_c = 0, y_c = 0;//координаты квадрата - центра вращения
        int x = 0, y = 0;//новые координаты квадратов
        int coord[][] = new int[4][2];//массив для сохранения новых координат квадратов
        if (form == 0 || form == 1 || form == 3 || form == 5 || form == 4) {
            //определение координат вращения
            x_c = s[1].get_x();
            y_c = s[1].get_y();
        }
        if (form == 6) {
            x_c = s[2].get_x();
            y_c = s[2].get_y();
        }
        if (form != 2) {
            if (k == 1) {//поворот по часовой стрелке
                for (int i = 0; i != 4; i++) {
                    x = x_c - (s[i].get_y() - y_c);//получение новых координат
                    y = y_c + (s[i].get_x() - x_c);
                    if (x >= 0 && x <= 9 && y >= 0 && y <= 19) {//если координаты не выходят
                        // за пределы поля - сохраняются в массиве
                        coord[i][0] = x;
                        coord[i][1] = y;
                    }
                    else return false;//иначе выход из функции
                }
                //если все координаты в пределах поля, то они задаются фигуре
                for (int i = 0; i != 4; i++) {
                    s[i].set_x(coord[i][0]);
                    s[i].set_draw_x(s[i].get_x() * Square.side_of_square + 113 + 3 * (s[i].get_x()));
                    s[i].set_y(coord[i][1]);
                    s[i].set_draw_y(207 + s[i].get_y() * Square.side_of_square + 3 * (s[i].get_y()));
                }
            }
            if (k == 2) {//поворот против часовой стрелке
                for (int i = 0; i != 4; i++) {
                    x = x_c + (s[i].get_y() - y_c);//получение новых координат
                    y = y_c - (s[i].get_x() - x_c);
                    if (x >= 0 && x <= 9 && y >= 0 && y <= 19) {//если координаты не выходят
                        // за пределы поля - сохраняются в массиве
                        coord[i][0] = x;
                        coord[i][1] = y;
                    }
                    else return false;//иначе выход из функции
                }
                //если все координаты в пределах поля, то они задаются фигуре
                for (int i = 0; i != 4; i++) {
                    s[i].set_x(coord[i][0]);
                    s[i].set_draw_x(s[i].get_x() * Square.side_of_square + 113 + 3 * (s[i].get_x()));
                    s[i].set_y(coord[i][1]);
                    s[i].set_draw_y(207 + s[i].get_y() * Square.side_of_square + 3 * (s[i].get_y()));
                }
            }
        }
        return true;
    }

    public boolean collision_down(int[][] grid){//проверка на возможность движния вниз
        for (int i=0;i!=4;i++) {
            if (s[i].get_y() != 19) { //если ниже на одну клетку находится квадрат
                if (grid[s[i].get_x()][s[i].get_y() + 1] != 0)
                    return true;//движение невозможно
            }
            else return true;//или если один из квадратов находится на последнем ряду поля
        }
        return false;
    }

    public boolean collision_right(int [][] grid){//проверка на возможность движния вправо
        for (int i=0;i!=4;i++){
            if (s[i].get_x()!=9) {//если правее на одну клетку находится квадрат
                if (grid[s[i].get_x() + 1][s[i].get_y()] != 0)
                    return true;//движение невозможно
            }
            else return true;//или если один из квадратов находится на последем столбце поля
        }
        return false;
    }

    public boolean collision_left(int[][]grid){//проверка на возможность движния влево
        for (int i=0;i!=4;i++){
            if (s[i].get_x()!=0) { //если левее на одну клетку находится квадрат
                if (grid[s[i].get_x() - 1][s[i].get_y()] != 0)
                    return true;//движение невозможно
            }
            else return true;//или один из квадратов находится на первом столбце поля
        }
        return false;
    }

    public void write_to_array(int[][] grid){//запись фигуры в массив поля
        for (int i=0;i!=4;i++)
            grid[s[i].get_x()][s[i].get_y()]= s[i].get_color_num()+1;
    }
}
