package com.example.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
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
    static Context c;
    private int score=0;
    private boolean first_appear=true;//проверка, первый ли раз появляется фигура
    private long time; //поле для фиксирования времени
    private long fall_time=2000;//время, через которое фигура опускается на одну клетку
    private GameThread gameLoop;//поток для изменения игры
    private Shapes sh,sh1;//действующая фигура
    private int grid_cells[][];//массив, сохраняющий расположения фигур на поле
    public GameView(Context context)
    {
        super(context);
        c=context;
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
        down_button= Bitmap.createScaledBitmap(down_button, down_button.getWidth()/4,
                down_button.getHeight()/4, false);
        clockwise_arrow=BitmapFactory.decodeResource(getResources(), R.drawable.clockwise_arrow);
        clockwise_arrow=Bitmap.createScaledBitmap(clockwise_arrow, clockwise_arrow.getWidth()/4,
                clockwise_arrow.getHeight()/4, false);
        counterclockwise_arrow=BitmapFactory.decodeResource(getResources(), R.drawable.counterclockwise_arrow);
        counterclockwise_arrow=Bitmap.createScaledBitmap(counterclockwise_arrow,
                counterclockwise_arrow.getWidth()/4, counterclockwise_arrow.getHeight()/4, false);
        menu_button = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        menu_button=Bitmap.createScaledBitmap(menu_button, menu_button.getWidth()/4,
                menu_button.getHeight()/4, false);
        time=System.currentTimeMillis();
        grid_cells=new int [10][20];
        sh=new Shapes(context);
    }

    protected void Draw(Context context,Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);//отрисовка элементов окна
        canvas.drawBitmap(grid, 110, 200, null);
        canvas.drawBitmap(menu_button, 900, 30, null);
        canvas.drawBitmap(down_button, 460, 1950, null);
        canvas.drawBitmap(clockwise_arrow, 700, 1950, null);
        canvas.drawBitmap(counterclockwise_arrow, 50, 1950, null);
        if(first_appear) { //если фигура появляется в первый раз
            if (sh.shape_first_appear(canvas,grid_cells)) {//и она может появиться - задаются
                // координаты появления фигуры
                first_appear = false;
                sh.draw_shape(canvas);//отрисовка фигуры
            }
            else {//иначе - выход в главное меню
                Intent intent = new Intent(c, Menu.class);
                c.startActivity(intent);
            }
        }
        else {//иначе - прорисовка всех квадратов по массиву grid_cells
            Bitmap squares= BitmapFactory.decodeResource(getResources(),R.drawable.texture_squares);
            for (int i=0;i!=10;i++) {
                for (int j = 0; j != 20; j++) {
                    if (grid_cells[i][j] != 0) {
                        int x = (grid_cells[i][j] - 1) * Square.side_of_square;
                        int left = i * Square.side_of_square + 3 * (i - 1) + 113;
                        int top = j * Square.side_of_square + 207 + 3 * (j - 1);
                        Bitmap one_squares =Bitmap.createBitmap(squares, x,0, Square.side_of_square, Square.side_of_square);
                        canvas.drawBitmap(one_squares, left, top, null);
                    }
                }
            }
            if (System.currentTimeMillis() - time > fall_time) {//если прошло время падения - фигура опускается на одну клетку
                sh.movement_vertically(grid_cells);
                time = System.currentTimeMillis();
            }
            sh.movement_horizontal(Game.last_x,grid_cells);//движение по горизонтали
            sh.draw_shape(canvas);//отрисовка фигуры
        }
        if (sh.collision_down(grid_cells)){
            sh.write_to_array(grid_cells);//запись в массив поля фигуры
            line_check(grid_cells,canvas);//проверка на наличие заполненных строк
            sh=new Shapes(c);//новая фигура
            first_appear=true;
        }

    }

    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            //обработка нажатий на кнопку
            if (x > 700 && y > 1950){
                sh.rotation(1);//поворот по часовой стрелке
            }
            if (x > 50 && x < 450 && y > 1950){
                sh.rotation(2);//поворот против часовой стрелки

            }
            if (x>450&&x<700&&y>1950){
                while(!sh.collision_down(grid_cells)) {
                    sh.movement_vertically(grid_cells);//падение фигуры до конца поля
                }
            }
            if (x>900&&y>30&&y<120){
                Intent intent = new Intent(c, Menu.class);//выход в главное меню
                c.startActivity(intent);
            }
        }
        return true;
    }

    public void line_check(int massiv[][], Canvas canvas){
        int string_grid[]=new int[20];//массив для отметки, заполнена ли строка
        int coefficient=1;//коэффицент увеличения очков при комбо
        int i,j;//индексы
        for (j=0;j!=20;j++){
            int count=0;
            for (i=0;i!=10;i++){
                if (massiv[i][j]!=0) count++;//проверка, есть ли на этой клетке квадрат
            }
            if (count==10) {//если все клетки в ряду заполнены
                score+=10*coefficient;//добавление баллов
                coefficient++;
                string_grid[j]=1;//отметка в массиве о заполненности строки
            }
            else string_grid[j]=0;
        }
        int k=0;
        while (k!=20) {//проход по всему массиву отметок
            if (string_grid[k] == 1) {//если строка заполнена
                time = System.currentTimeMillis();
                Bitmap brocken_squares=BitmapFactory.decodeResource(getResources(), R.drawable.brocken_squares);
                while (System.currentTimeMillis()-time<1000) {
                    for (i = 0; i != 10; i++) {
                        int x = (grid_cells[i][k] - 1) * Square.side_of_square;
                        Bitmap one_brocken_square = Bitmap.createBitmap(brocken_squares, x, 0, Square.side_of_square, Square.side_of_square);
                        int left = i * Square.side_of_square + 3 * (i - 1) + 113;
                        int top = k * Square.side_of_square + 207 + 3 * (k - 1);
                        canvas.drawBitmap(one_brocken_square, left, top, null);
                    }
                    //sh.draw_shape(canvas);
                }
                for (i = k; i != 0; i--) {
                    for (j = 0; j != 10; j++) {
                        grid_cells[j][i] = grid_cells[j][i - 1];//все строки выше сдвигаются вниз
                    }
                }
                if (k==19) k=k+1;
                else if (string_grid[k + 1] != 1) k = k + 1;
            }
            else k=k+1;
        }
        if (score>=10) fall_time=1500;//при определенном счете скорость игры увеличивается
        if (score>=30) fall_time=500;

    }
}
