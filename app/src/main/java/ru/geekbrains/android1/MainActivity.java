package ru.geekbrains.android1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editCity;
    private CheckBox checkBoxHumidity;
    private CheckBox checkBoxPressure;
    private CheckBox checkBoxWind;

    private WeatherSettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initViews();

        settingsPresenter = WeatherSettingsPresenter.getInstance();

        checkBoxWind.setChecked(settingsPresenter.isWindChecked());
        checkBoxPressure.setChecked(settingsPresenter.isPressureChecked());
        checkBoxHumidity.setChecked(settingsPresenter.isHumidityChecked());

        setListeners();
    }

    private void initViews() {
        editCity = findViewById(R.id.edit_city);
        checkBoxHumidity = findViewById(R.id.cb_humidity);
        checkBoxPressure = findViewById(R.id.cb_pressure);
        checkBoxWind = findViewById(R.id.cb_wind);
    }

    private void setListeners() {
        checkBoxHumidity.setOnCheckedChangeListener(
                (buttonView, isChecked) -> settingsPresenter.setHumidityChecked(isChecked)
        );
        checkBoxPressure.setOnCheckedChangeListener(
                (buttonView, isChecked) -> settingsPresenter.setPressureChecked(isChecked)
        );
        checkBoxWind.setOnCheckedChangeListener(
                (buttonView, isChecked) -> settingsPresenter.setWindChecked(isChecked)
        );
    }

    public void showWeather(View view) {

        String city = editCity.getText().toString();

        if (city.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.fill_city_name, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        settingsPresenter.setCity(city);

        Intent intent = new Intent(getApplicationContext(), ShowWeatherActivity.class);
        startActivity(intent);
    }

}
