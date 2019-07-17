package ru.geekbrains.android1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String SETTINGS = "SETTINGS";

    private EditText editCity;
    private CheckBox checkBoxHumidity;
    private CheckBox checkBoxPressure;
    private CheckBox checkBoxWind;
    private ListView citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initViews();
        setListeners();

    }

    private void initViews() {
        editCity = findViewById(R.id.edit_city);
        checkBoxHumidity = findViewById(R.id.cb_humidity);
        checkBoxPressure = findViewById(R.id.cb_pressure);
        checkBoxWind = findViewById(R.id.cb_wind);
        citiesList = findViewById(R.id.list_cities);
    }

    private void setListeners() {
        citiesList.setOnItemClickListener((parent, view, position, id) ->
                editCity.setText(((TextView) view).getText())
        );
    }

    public void showWeather(View view) {

        String city = editCity.getText().toString();

        if (city.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.fill_city_name, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        WeatherSettingsParcel settingsParcel = new WeatherSettingsParcel();
        fillParcel(settingsParcel);
        settingsParcel.setCity(city);

        Intent intent = new Intent(getApplicationContext(), ShowWeatherActivity.class);
        intent.putExtra(SETTINGS, settingsParcel);
        startActivity(intent);
    }

    private void fillParcel(WeatherSettingsParcel parcel) {
        parcel.setWindChecked(checkBoxWind.isChecked());
        parcel.setPressureChecked(checkBoxPressure.isChecked());
        parcel.setHumidityChecked(checkBoxHumidity.isChecked());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
