package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView { //класс отрисовки игры
    private SurfaceHolder holder;
    private Bitmap background;//изображения фона,кнопок,поля
    private Bitmap grid;
    private Bitmap down_button;
    private Bitmap clockwise_arrow;
    private Bitmap counterclockwise_arrow;
    private Bitmap menu_button;
    private boolean first_appear=true;//проверка, первый ли раз появляется фигура
    private long time; //поле для фиксирования времени
    private long fall_time=2000;//время, через которое фигура опускается на одну клетку
    private GameThread gameLoop;//поток для изменения игры
    static Context c; //объект для доступа к базовым функциям
    private Shapes sh;//действующая фигура
    public GameView(Context context)
    {
        super(context);
        gameLoop = new GameThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceDestroyed(SurfaceHolder holder)//удаление области рисования
            {
                boolean retry = true; //закрытие потока
                gameLoop.setRunning(false);
                while (retry) {
                    try {
                        gameLoop.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) //создание области рисования
            {
                gameLoop.setRunning(true); //запуск потока
                gameLoop.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
                    //изменение области рисования
            {
            }
        });
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        //загрузка изображений, изменение размеров
        grid = BitmapFactory.decodeResource(getResources(), R.drawable.grid);
        down_button=BitmapFactory.decodeResource(getResources(), R.drawable.down);
        down_button= Bitmap.createScaledBitmap(down_button, down_button.getWidth()/4, down_button.getHeight()/4, false);
        clockwise_arrow=BitmapFactory.decodeResource(getResources(), R.drawable.clockwise_arrow);
        clockwise_arrow=Bitmap.createScaledBitmap(clockwise_arrow, clockwise_arrow.getWidth()/4, clockwise_arrow.getHeight()/4, false);
        counterclockwise_arrow=BitmapFactory.decodeResource(getResources(), R.drawable.counterclockwise_arrow);
        counterclockwise_arrow=Bitmap.createScaledBitmap(counterclockwise_arrow, counterclockwise_arrow.getWidth()/4, counterclockwise_arrow.getHeight()/4, false);
        menu_button = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        menu_button=Bitmap.createScaledBitmap(menu_button, menu_button.getWidth()/4, menu_button.getHeight()/4, false);
        sh=new Shapes(context);
        time=System.currentTimeMillis();
    }
    protected void Draw(Context context,Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);//отрисовка элементов окна
        canvas.drawBitmap(grid, 110, 200, null);
        canvas.drawBitmap(menu_button, 900, 30, null);
        canvas.drawBitmap(down_button, 460, 1950, null);
        canvas.drawBitmap(clockwise_arrow, 700, 1950, null);
        canvas.drawBitmap(counterclockwise_arrow, 50, 1950, null);
        if(first_appear) { //если фигура появляется в первый раз
            sh.shape_first_appear(canvas);//задаются координаты появления
            first_appear=false;
        }
        if (System.currentTimeMillis()-time>fall_time) {//если прошло время падения - фигура опускается на одну клетку
            sh.movement_vertically();
            time=System.currentTimeMillis();
        }
        sh.draw_shape(canvas);//отрисовка фигуры

    }
}
