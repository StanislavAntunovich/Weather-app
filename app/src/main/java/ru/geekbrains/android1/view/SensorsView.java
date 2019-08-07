package ru.geekbrains.android1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import ru.geekbrains.android1.R;

import static android.content.Context.SENSOR_SERVICE;

public class SensorsView extends LinearLayout {
    private Map<String, Sensor> sensors = new HashMap<>();
    private Map<String, SensorEventListener> listeners = new HashMap<>();
    private SensorManager sensorManager;

    private Context context;
    int color;

    public SensorsView(Context context) {
        super(context);
        this.context = context;
        init(context);
        initSensorsManager(context);
    }

    public SensorsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
        initSensorsManager(context);
        initAttrs(attrs, context);
    }

    public SensorsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
        initSensorsManager(context);
        initAttrs(attrs, context);
    }

    private void initAttrs(AttributeSet attrs, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SensorsView);
        color = typedArray.getColor(R.styleable.SensorsView_textColor,
                context.getResources().getColor(android.R.color.holo_blue_light));
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.view_sensors, this);
        }
    }


    private void initSensorsManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    public void addSensor(int sensorType, String sensorTitle) {
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            sensors.put(sensorTitle, sensor);
            TextView value = inflateSensorInfo(sensorTitle);
            SensorEventListener listener = setSensorEventListener(value);
            listeners.put(sensorTitle, listener);
        }
    }

    private SensorEventListener setSensorEventListener(TextView textView) {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                String dimValue = String.valueOf(event.values[0]);
                textView.setText(dimValue);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void registerListeners() {
        for (String sensorTitle : sensors.keySet()) {
            SensorEventListener listener = listeners.get(sensorTitle);
            Sensor sensor = sensors.get(sensorTitle);
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListeners() {
        for (String sensorTitle : sensors.keySet()) {
            SensorEventListener listener = listeners.get(sensorTitle);
            Sensor sensor = sensors.get(sensorTitle);
            sensorManager.unregisterListener(listener, sensor);
        }
    }

    private TextView inflateSensorInfo(String sensorTitle) {
        LinearLayout layout = makeLayout();

        TextView title = makeTextView(sensorTitle);
        TextView value = makeTextView(null);

        layout.addView(title);
        layout.addView(value);

        addView(layout);

        return value;
    }

    private TextView makeTextView(String text) {
        TextView title = new TextView(context);
        if (text != null) {
            title.setText(text);
        }
        title.setTextColor(color);
        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(8, 8, 8, 8);
        title.setLayoutParams(textViewParams);
        return title;
    }

    private LinearLayout makeLayout() {
        LinearLayout layout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layout.setLayoutParams(layoutParams);
        return layout;
    }


}
