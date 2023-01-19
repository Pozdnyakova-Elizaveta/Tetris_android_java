package com.example.tetris;

import android.graphics.Canvas;

public class GameThread extends Thread { //поток для обновления рисования
    static final long FPS = 10;
    private GameView view;
    private boolean running = false;

    public GameThread(GameView view)
    {
        this.view = view;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    public void run() {
        long ticksPS = 1000 / FPS; //скорость отрисовки
        long startTime;
        long sleepTime;
        while (running) { //запуск потока
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
                sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0)
                        sleep(sleepTime);
                    else
                        sleep(10);
                } catch (Exception e) {}
            }
        }
    }
}
