package ru.geekbrains.android1.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import ru.geekbrains.android1.R;

import static android.content.Context.SENSOR_SERVICE;

public class SensorsView extends LinearLayout {
    private Sensor humiditySensor;
    private Sensor temperatureSensor;
    private SensorManager sensorManager;

    private ConstraintLayout humidityLayout;
    private ConstraintLayout temperatureLayout;

    private TextView humidityText;
    private TextView temperatureText;

    private SensorEventListener humiditySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float humidity = event.values[0];
            setHumidity(humidity);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private SensorEventListener temperatureSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float temperature = event.values[0];
            setTemperature(temperature);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public SensorsView(Context context) {
        super(context);
        initViews(context);
        initSensors(context);
    }

    public SensorsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
        initSensors(context);
    }

    public SensorsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initSensors(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initUI();
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.view_sensors, this);
        }
    }

    private void initSensors(Context context) {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    private void initUI() {
        humidityLayout = findViewById(R.id.cl_current_humidity);
        temperatureLayout = findViewById(R.id.cl_current_temperature);
        humidityText = findViewById(R.id.val_curr_humidity);
        temperatureText = findViewById(R.id.val_curr_temperature);

    }

    private void initSensorsLayouts() {
        if (humiditySensor != null) {
            humidityLayout.setVisibility(View.VISIBLE);
        }

        if (temperatureSensor != null) {
            temperatureLayout.setVisibility(View.VISIBLE);
        }
    }

    public void registrListeners() {
        if (humiditySensor != null) {
            sensorManager.registerListener(humiditySensorListener, humiditySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (temperatureSensor != null) {
            sensorManager.registerListener(temperatureSensorListener, temperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListeners() {
        sensorManager.unregisterListener(humiditySensorListener, humiditySensor);
        sensorManager.unregisterListener(temperatureSensorListener, temperatureSensor);
    }

    public void setHumidityVisibility(int visibility) {
        humidityLayout.setVisibility(visibility);
    }

    public void setTemperatureVisibility(int visibility) {
        temperatureLayout.setVisibility(visibility);
    }

    public void setTemperature(float temperature) {
        String temp = String.valueOf(temperature);
        temperatureText.setText(temp);
    }

    public void setTemperature(String temperature) {
        temperatureText.setText(temperature);
    }

    public void setHumidity(float humidity) {
        String temp = String.valueOf(humidity);
        humidityText.setText(temp);
    }

    public void setHumidity(String humidity) {
        humidityText.setText(humidity);
    }

}
