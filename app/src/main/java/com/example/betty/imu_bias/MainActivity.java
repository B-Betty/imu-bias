package com.example.betty.imu_bias;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView tv_gyro,tv_acc;
    private SensorManager sensorManager;
    float[] gyroscopesum = new float[3];
    float[] gyroscopeavg = new float[3];
    float[] accelerationsum = new float[3];
    float[] accelerationavg = new float[3];
    int num_gyro = 0;
    int num_acc = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_gyro = (TextView) findViewById(R.id.gyroscope);
        tv_acc = (TextView) findViewById(R.id.acceleration);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_GYROSCOPE:
                num_gyro++;
                gyroscopesum[0] += event.values[0];
                gyroscopesum[1] += event.values[1];
                gyroscopesum[2] += event.values[2];
                if (num_gyro == 500){
                    gyroscopeavg[0] = gyroscopesum[0] / num_gyro;
                    gyroscopeavg[1] = gyroscopesum[1] /num_gyro;
                    gyroscopeavg[2] = gyroscopesum[2]/num_gyro;
                    tv_gyro.setText("陀螺仪零偏 \n"+"\n"+gyroscopeavg[0]+"\n"+gyroscopeavg[1]+"\n"+gyroscopeavg[2]);
                    Log.i("陀螺仪零偏 \n","\n"+gyroscopeavg[0]+"\n"+gyroscopeavg[1]+"\n"+gyroscopeavg[2]);
                }
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                num_acc++;
                accelerationsum[0] += event.values[0];
                accelerationsum[1] += event.values[1];
                accelerationsum[2] += event.values[2];
                if (num_acc == 500){
                    accelerationavg[0] = accelerationsum[0] / num_acc;
                    accelerationavg[1] = accelerationsum[1] /num_acc;
                    accelerationavg[2] = accelerationsum[2]/num_acc;
                    tv_acc.setText("加速度计零偏 \n"+"\n"+accelerationavg[0]+"\n"+accelerationavg[1]+"\n"+accelerationavg[2]);
                    Log.i("加速度计零偏 \n","\n"+accelerationavg[0]+"\n"+accelerationavg[1]+"\n"+accelerationavg[2]);
                }
                break;
            default:
                break;

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
