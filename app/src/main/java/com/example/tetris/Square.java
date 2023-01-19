package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Square {//класс квадратов
    private int x=0;//координаты квадрата на поле
    private int y=0;
    private int draw_x=0;//координаты квадрата с учетом размеров и отступов (для непосредственно рисования)
    private int draw_y=0;
    private int color_num;//номер цвета квадрата
    private Bitmap texture_squares;//текстура и спрайт квадрата
    private Bitmap sprite_square;
    static int side_of_square;//длина стороны
    public Square(Context context){
        texture_squares= BitmapFactory.decodeResource(context.getResources(),R.drawable.texture_squares);//загрузка изображения
        color_num=(int) ( Math.random() * 5 );//рандомный выбор цвета
        side_of_square=texture_squares.getWidth()/6;
        sprite_square = Bitmap.createBitmap(texture_squares, color_num*side_of_square, 0,side_of_square, side_of_square);//выделение спрайта квадрата из текстур
    }
    public Bitmap getSprite_square(){
        return sprite_square;
    }
    public int get_color_num(){
        return color_num;
    }
    public int get_x(){
        return x;
    }
    public int get_y(){
        return y;
    }
    public void set_x(int new_x){
        x=new_x;
    }
    public void set_y(int new_y){
        y=new_y;
    }
    public int get_draw_x(){
        return draw_x;
    }
    public int get_draw_y(){
        return draw_y;
    }
    public void set_draw_x(int new_x){
        draw_x=new_x;
    }
    public void set_draw_y(int new_y){
        draw_y=new_y;
    }
    public Square(Square a){//конструктор копирования
        x=a.x;
        y=a.y;
        draw_x=a.draw_x;
        draw_y=a.draw_y;
        color_num=a.color_num;
        sprite_square=a.sprite_square;
    }
}
