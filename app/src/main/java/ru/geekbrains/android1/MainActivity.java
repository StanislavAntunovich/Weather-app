package ru.geekbrains.android1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.geekbrains.android1.data.FakeData;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;

public class MainActivity extends AppCompatActivity {
    public static final String CURRENT_DATA = "CURRENT_DATA";
    public static final String CURRENT_INDEX = "CURRENT_INDEX";

    private WeatherDetailsData currentWeatherData;
    private int currentIndex;
    private FakeData fakeData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = getResources().getStringArray(R.array.cities);
        fakeData = new FakeData(cities);

        if (savedInstanceState == null) {
            currentIndex = 0;
            currentWeatherData = fakeData.getData(0);
        }

        Fragment mainFragment = MainWeatherFragment.create(currentWeatherData, currentIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_weather_container, mainFragment)
                .commit();

        Fragment detailsFragment = DetailsWeatherFragment.create(currentWeatherData);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, detailsFragment)
                .commit();

    }

}
