package ru.geekbrains.android1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.geekbrains.android1.data.FakeData;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;

public class MainActivity extends AppCompatActivity {
    public static final String CURRENT_DATA = "CURRENT_DATA";
    public static final String CURRENT_INDEX = "CURRENT_INDEX";
    public static final String FORECAST = "FORECAST";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FakeData.setData(getResources().getStringArray(R.array.cities));
        }

        Fragment mainFragment = new MainWeatherFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_weather_container, mainFragment)
                .commit();

        Fragment detailsFragment = new DetailsWeatherFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, detailsFragment)
                .commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment forecastFragment = new WeekForecastFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.forecast_container, forecastFragment)
                    .commit();
        }

    }

}
