package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Rules extends Activity {
    private ImageButton button_back;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.background);//установка фона
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        button_back = new ImageButton(this);//кнопка выхода в меню
        button_back.setBackgroundColor(Color.TRANSPARENT);
        button_back.setImageDrawable(getDrawable(R.drawable.menu));
        LinearLayout.LayoutParams param_button_exit = new LinearLayout.LayoutParams(300,150);
        param_button_exit.leftMargin = 750;//расположение кнопки
        param_button_exit.topMargin = 1700;
        button_back.setLayoutParams(param_button_exit);
        layout.addView(button_back);
        textView = new TextView(this);// установка текста правил
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(getColor(R.color.white));
        textView.setAllCaps(true); // делаем буквы заглавными
        LinearLayout.LayoutParams param_button_exit1 = new LinearLayout.LayoutParams(300,150);
        textView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);// вравнивание текста по центру
        textView.setText("Сверху вниз падают фигуры разных цветов и форм. Фигуру можно двигать по горизонтали поворотом экрана в сторону необходимого направления движения фигуры.  С помощью кнопок ее можно вращать по и против часовой стрелки, также соответствующей кнопкой фигуру можно опустить в крайнее нижнее положение. При положении по краям, если движение дальше недоступно, фигура остаётся в последнем доступном положении. Задача игрока собирать фигуры в полностью заполненный ряд. В таком случае ряд исчезает и игрок получает баллы. После того как игрок соберет один ряд, он  получает 10 баллов, за два ряда - 30, за три - 60. Время падения фигур в течении игры уменьшается, начинается игра с падения фигур в течении 2 секунд и сокращается до 0.5 секунды. \n" +
                "\n" +
                "\n" +
                "Разработчики игры - Малахова Ульяна, Позднякова Елизавета");
        textView.setTypeface(Typeface.create("casual", Typeface.NORMAL));// установка шрифта
        textView.setTextSize(17);// устанавливаем высоту текста
        layout.addView(textView);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Обработка нажатия - выход в меню
                Intent intent = new Intent(Rules.this, Menu.class);
                startActivity(intent);
            }
        });
        setContentView(layout);
    }
}