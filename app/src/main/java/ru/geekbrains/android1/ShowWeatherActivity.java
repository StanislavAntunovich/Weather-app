package ru.geekbrains.android1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static ru.geekbrains.android1.MainActivity.LOG_TAG;

public class ShowWeatherActivity extends AppCompatActivity {
    private static String SHOW_WEATHER = "SHOW_WEATHER";
    private TextView txtTemperatureMain;
    private TextView txtWeatherType;
    private TextView txtHumidityVal;
    private TextView txtPressureVal;
    private TextView txtWindVal;
    private TextView txtCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        String logMess = null;

        if (savedInstanceState == null) {
            logMess = "first call of ";
        } else {
            logMess = "another call of ";
        }

        WeatherSettingsPresenter settingsPresenter = WeatherSettingsPresenter.getInstance();
        WeatherInfoPresenter infoPresenter = WeatherInfoPresenter.getInstance();

        initViews();
        infoPresenter.setCity(settingsPresenter.getCity());
        setDetailsVisibility(settingsPresenter);

        txtCity.setText(infoPresenter.getCity());
        txtTemperatureMain.setText(infoPresenter.getTemperature());
        txtWeatherType.setText(infoPresenter.getWeatherType());
        txtHumidityVal.setText(infoPresenter.getHumidity());
        txtPressureVal.setText(infoPresenter.getPressure());
        txtWindVal.setText(infoPresenter.getWind());

        Log.d(LOG_TAG, logMess + "onCreate() " + SHOW_WEATHER);
    }

    private void setDetailsVisibility(WeatherSettingsPresenter settingsPresenter) {
        LinearLayout humidityLiner = findViewById(R.id.ll_humidity);
        LinearLayout pressureLiner = findViewById(R.id.ll_pressure);
        LinearLayout windLiner = findViewById(R.id.ll_wind);

        humidityLiner.setVisibility(settingsPresenter.isHumidityChecked() ? View.VISIBLE : View.GONE);
        pressureLiner.setVisibility(settingsPresenter.isPressureChecked() ? View.VISIBLE : View.GONE);
        windLiner.setVisibility(settingsPresenter.isWindChecked() ? View.VISIBLE : View.GONE);
    }

    private void initViews() {
        txtTemperatureMain = findViewById(R.id.temperature_main_val);
        txtWeatherType = findViewById(R.id.weather_type);
        txtHumidityVal = findViewById(R.id.text_humidity_val);
        txtPressureVal = findViewById(R.id.text_pressure_val);
        txtWindVal = findViewById(R.id.text_wind_val);
        txtCity = findViewById(R.id.text_city);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() "+ SHOW_WEATHER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume() "+ SHOW_WEATHER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause() "+ SHOW_WEATHER);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop() "+ SHOW_WEATHER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy() "+ SHOW_WEATHER);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState() "+ SHOW_WEATHER);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState() "+ SHOW_WEATHER);
    }


}
