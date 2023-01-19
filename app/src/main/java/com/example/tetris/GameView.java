package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    public GameView(Context context)
    {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceDestroyed(SurfaceHolder holder)
            {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                Canvas c = holder.lockCanvas(null);
                holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
    }
}
