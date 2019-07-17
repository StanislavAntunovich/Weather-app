package ru.geekbrains.android1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static ru.geekbrains.android1.MainActivity.SETTINGS;

public class ShowWeatherActivity extends AppCompatActivity {
    private TextView txtTemperatureMain;
    private TextView txtWeatherType;
    private TextView txtHumidityVal;
    private TextView txtPressureVal;
    private TextView txtWindVal;
    private TextView txtCity;

    private LinearLayout humidityLiner;
    private LinearLayout pressureLiner;
    private LinearLayout windLiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        WeatherInfoPresenter infoPresenter = WeatherInfoPresenter.getInstance();

        initViews();
        WeatherSettingsParcel settingsParcel = (WeatherSettingsParcel) getIntent().getSerializableExtra(SETTINGS);
        if (settingsParcel != null) {
            infoPresenter.setCity(settingsParcel.getCity());
            setDetailsVisibility(settingsParcel);
        }

        setWeatherInfo(infoPresenter);

    }

    private void setWeatherInfo(WeatherInfoPresenter infoPresenter) {
        txtCity.setText(infoPresenter.getCity());
        txtTemperatureMain.setText(infoPresenter.getTemperature());
        txtWeatherType.setText(infoPresenter.getWeatherType());
        txtHumidityVal.setText(infoPresenter.getHumidity());
        txtPressureVal.setText(infoPresenter.getPressure());
        txtWindVal.setText(infoPresenter.getWind());
    }

    private void setDetailsVisibility(WeatherSettingsParcel settingsParcel) {
        humidityLiner.setVisibility(settingsParcel.isHumidityChecked() ? View.VISIBLE : View.GONE);
        pressureLiner.setVisibility(settingsParcel.isPressureChecked() ? View.VISIBLE : View.GONE);
        windLiner.setVisibility(settingsParcel.isWindChecked() ? View.VISIBLE : View.GONE);
    }

    private void initViews() {
        humidityLiner = findViewById(R.id.ll_humidity);
        pressureLiner = findViewById(R.id.ll_pressure);
        windLiner = findViewById(R.id.ll_wind);

        txtTemperatureMain = findViewById(R.id.temperature_main_val);
        txtWeatherType = findViewById(R.id.weather_type);
        txtHumidityVal = findViewById(R.id.text_humidity_val);
        txtPressureVal = findViewById(R.id.text_pressure_val);
        txtWindVal = findViewById(R.id.text_wind_val);
        txtCity = findViewById(R.id.text_city);
    }

    public void onClickMoreInfo(View view) {
        String city = txtCity.getText().toString();
        String urlPattern = "https://ru.wikipedia.org/wiki/%s";
        Uri uri = Uri.parse(String.format(urlPattern, city));
        Intent wikiInfo = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(wikiInfo);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        WeatherSettingsParcel parcel = new WeatherSettingsParcel();
        parcel.setWindChecked(windLiner.getVisibility() == View.VISIBLE);
        parcel.setHumidityChecked(humidityLiner.getVisibility() == View.VISIBLE);
        parcel.setPressureChecked(pressureLiner.getVisibility() == View.VISIBLE);
        outState.putSerializable(SETTINGS, parcel);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        WeatherSettingsParcel parcel = (WeatherSettingsParcel) savedInstanceState.getSerializable(SETTINGS);
        if (parcel != null) {
            setDetailsVisibility(parcel);
        }
    }

}
