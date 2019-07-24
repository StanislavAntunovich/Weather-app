package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.fragments.WeekForecastFragment;

public class ShowForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        Intent intent = getIntent();
        ForecastData[] data = (ForecastData[]) intent.getExtras().getSerializable(MainActivity.FORECAST);

        Fragment forecastFragment = WeekForecastFragment.create(data);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.forecast_activity_container, forecastFragment)
                .commit();

    }
}
