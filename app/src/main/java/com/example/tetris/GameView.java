package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView { //класс отрисовки игры
    private SurfaceHolder holder;
    private GameThread gameLoop;
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
    }
}
