package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.geekbrains.android1.data.ForecastData;

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
        if (intent != null) {
            ForecastData data = (ForecastData) intent.getSerializableExtra(MainActivity.FORECAST);
            //TODO тут будет установка списка карточек прогноза на неделю
        }

    }
}
