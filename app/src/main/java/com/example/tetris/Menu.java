package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Menu extends AppCompatActivity {
    private ImageButton button_game;
    private ImageButton button_rules;
    private ImageButton button_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.background);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        button_game = new ImageButton(this);
        button_game.setBackgroundColor(Color.TRANSPARENT);
        button_game.setImageDrawable(getDrawable(R.drawable.start));
        LinearLayout.LayoutParams param_button_game = new LinearLayout.LayoutParams(300,150);
        param_button_game.leftMargin = 385;
        param_button_game.topMargin = 1500;
        button_game.setLayoutParams(param_button_game);
        layout.addView(button_game);
        setContentView(layout);
        button_rules = new ImageButton(this);
        button_rules.setBackgroundColor(Color.TRANSPARENT);
        button_rules.setImageDrawable(getDrawable(R.drawable.rules));
        LinearLayout.LayoutParams param_button_rules = new LinearLayout.LayoutParams(300,150);
        param_button_rules.leftMargin = 750;
        param_button_rules.topMargin = 80;
        button_rules.setLayoutParams(param_button_rules);
        layout.addView(button_rules);
        setContentView(layout);
        button_exit = new ImageButton(this);
        button_exit.setBackgroundColor(Color.TRANSPARENT);
        button_exit.setImageDrawable(getDrawable(R.drawable.menu));
        LinearLayout.LayoutParams param_button_exit = new LinearLayout.LayoutParams(300,150);
        param_button_exit.leftMargin = 750;
        param_button_exit.topMargin = 1700;
        button_exit.setLayoutParams(param_button_exit);
        layout.addView(button_exit);
        setContentView(layout);
    }
}