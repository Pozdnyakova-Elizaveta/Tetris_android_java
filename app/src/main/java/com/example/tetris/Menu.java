package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Menu extends AppCompatActivity {
    private ImageView title;
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
        title = new ImageView(this);
        title.setImageResource(R.drawable.title);
        RelativeLayout.LayoutParams params_title = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params_title.addRule(RelativeLayout.CENTER_IN_PARENT);
        title.setLayoutParams(params_title);
        layout.addView(title);

        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1500);
        animation1.setStartOffset(2000);
        animation1.setFillAfter(true);
        title.startAnimation(animation1);
        button_game = new ImageButton(this);
        button_game.setBackgroundColor(Color.TRANSPARENT);
        button_game.setImageDrawable(getDrawable(R.drawable.start));
        LinearLayout.LayoutParams param_button_game = new LinearLayout.LayoutParams(300,150);
        param_button_game.leftMargin = 385;
        param_button_game.topMargin = 1500;
        button_game.setLayoutParams(param_button_game);
        layout.addView(button_game);
        button_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Обработка нажатия
                Intent intent = new Intent(Menu.this, Game.class);
                startActivity(intent);
            }
        });
        setContentView(layout);
        button_rules = new ImageButton(this);
        button_rules.setBackgroundColor(Color.TRANSPARENT);
        button_rules.setImageDrawable(getDrawable(R.drawable.rules));
        LinearLayout.LayoutParams param_button_rules = new LinearLayout.LayoutParams(300,150);
        param_button_rules.leftMargin = 750;
        param_button_rules.topMargin = 80;
        button_rules.setLayoutParams(param_button_rules);
        layout.addView(button_rules);
        button_rules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Обработка нажатия
                Intent intent = new Intent(Menu.this, Rules.class);
                startActivity(intent);
            }
        });
        setContentView(layout);
        button_exit = new ImageButton(this);
        button_exit.setBackgroundColor(Color.TRANSPARENT);
        button_exit.setImageDrawable(getDrawable(R.drawable.menu));
        LinearLayout.LayoutParams param_button_exit = new LinearLayout.LayoutParams(300,150);
        param_button_exit.leftMargin = 750;
        param_button_exit.topMargin = 1700;
        button_exit.setLayoutParams(param_button_exit);
        layout.addView(button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Обработка нажатия
                System.exit(0);
            }
        });
        setContentView(layout);
    }
}