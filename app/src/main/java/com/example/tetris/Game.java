package com.example.tetris;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));//размещение пользовательского интерфейса
    }
}