package ru.geekbrains.android1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String MAIN_ACTIVITY = "CHOOSE_CITY";
    public static String LOG_TAG = "ACTIVITIES_LOG"; // чтобы посмотреть колбэки при переходе мLOG_TAG

    private EditText editCity;
    private CheckBox checkBoxHumidity;
    private CheckBox checkBoxPressure;
    private CheckBox checkBoxWind;
    private ListView citiesList;

    private WeatherSettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        String logMess = null;

        if (savedInstanceState == null) {
            logMess = "first call of ";
        } else {
            logMess = "another call of ";
        }

        initViews();

        settingsPresenter = WeatherSettingsPresenter.getInstance();

        checkBoxWind.setChecked(settingsPresenter.isWindChecked());
        checkBoxPressure.setChecked(settingsPresenter.isPressureChecked());
        checkBoxHumidity.setChecked(settingsPresenter.isHumidityChecked());

        setListeners();

        Log.d(LOG_TAG, logMess + "onCreate() " + MAIN_ACTIVITY);
    }

    private void initViews() {
        editCity = findViewById(R.id.edit_city);
        checkBoxHumidity = findViewById(R.id.cb_humidity);
        checkBoxPressure = findViewById(R.id.cb_pressure);
        checkBoxWind = findViewById(R.id.cb_wind);
        citiesList = findViewById(R.id.list_cities);
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

        settingsPresenter.setCity(city);

        Intent intent = new Intent(getApplicationContext(), ShowWeatherActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() " + MAIN_ACTIVITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume() " + MAIN_ACTIVITY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause() " + MAIN_ACTIVITY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop() " + MAIN_ACTIVITY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy() " + MAIN_ACTIVITY);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState() " + MAIN_ACTIVITY);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState() " + MAIN_ACTIVITY);
    }
}
