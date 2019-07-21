package ru.geekbrains.android1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowForecastActivity extends AppCompatActivity {
    private CityPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forecast);
        
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        presenter = CityPresenter.getInstance();

        TextView city = findViewById(R.id.tempCity);
        city.setText(presenter.getCurrentData().getCity());


            //TODO тут будет установка списка карточек прогноза на неделю

    }
}
