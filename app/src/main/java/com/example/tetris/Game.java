package com.example.tetris;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Game extends Activity {
    static int last_x = 0;
    private Sensor sensor_rotation;//датчик
    private SensorManager sensorManager;//получение доступа к датчикам устройства
    private SensorEventListener sensor_event;//получение новых данных датчиков

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));//размещение пользовательского интерфейса
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        if (sensorManager != null)
            //получаем датчик вращения по умолчанию
            sensor_rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensor_event = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {//изменение значений датчика
                if (sensorEvent.values[1] > 0.25f) last_x = 1;//движение вправо
                if (sensorEvent.values[1] < -0.25f) last_x = 2;//движение влево
                if (sensorEvent.values[1] < 0.25f && sensorEvent.values[1] > -0.25f) last_x = 0;
                //не двигается
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {//изменении точности датчика

            }
        };
    }

    @Override
    protected void onPause() {//при остановке взаимодействия с окном
        super.onPause();
        sensorManager.unregisterListener(sensor_event);//отменяет регистрацию значений датчиков
        last_x = 0;
    }

    @Override
    protected void onResume() {//при активном окне
        super.onResume();
        //запуск регистрации значений датчиков
        sensorManager.registerListener(sensor_event, sensor_rotation, SensorManager.SENSOR_DELAY_NORMAL);
    }
}