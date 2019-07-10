package ru.geekbrains.android1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static String CITY = "CITY";
    public static String WIND = "WIND";
    public static String PRESSURE = "PRESSURE";
    public static String HUMIDITY = "HUMIDITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choice);
    }

    public void showWeather(View view) {
        // заглушка
        setContentView(R.layout.activity_main);
    }

}
