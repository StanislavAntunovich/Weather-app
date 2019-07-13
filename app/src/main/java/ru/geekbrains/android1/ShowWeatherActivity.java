package ru.geekbrains.android1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowWeatherActivity extends AppCompatActivity {
    TextView txtTemperatureMain;
    TextView txtWeatherType;
    TextView txtHumidityVal;
    TextView txtPressureVal;
    TextView txtWindVal;
    TextView txtCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

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


}
