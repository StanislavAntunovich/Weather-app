package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.geekbrains.android1.data.FakeSourceBuilder;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;
import ru.geekbrains.android1.presenters.CurrentIndexPresenter;

public class MainActivity extends AppCompatActivity {
    public static final String FORECAST = "FORECAST";
    public static final String DATA_SOURCE = "DATA_SOURCE";
    public static final String DETAILS = "DETAILS";

    private final int REQUEST_CODE = 42;

    WeatherDataSource dataSource;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            dataSource = new FakeSourceBuilder()
                    .setResources(getResources())
                    .build();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        CurrentIndexPresenter presenter = CurrentIndexPresenter.getInstance();
        int currentIndex = presenter.getCurrentIndex();

        Fragment mainFragment = MainWeatherFragment.create(dataSource);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_weather_container, mainFragment)
                .commit();

        Fragment detailsFragment = DetailsWeatherFragment.create(dataSource.getData(currentIndex));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, detailsFragment)
                .commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment forecastFragment = WeekForecastFragment.create(
                    dataSource.getData(currentIndex).getForecast()
            );
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.forecast_container, forecastFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA_SOURCE, dataSource);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataSource = (WeatherDataSource) savedInstanceState.getSerializable(DATA_SOURCE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_cities:
                startActivityForResult(prepareIntent(), REQUEST_CODE);
                return true;
                //TODO settings
            default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE || resultCode == RESULT_OK) {
            dataSource = (WeatherDataSource) data.getExtras().getSerializable(DATA_SOURCE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Intent prepareIntent() {
        Intent intent = new Intent(getApplicationContext(), AddCityActivity.class);
        Bundle arg = new Bundle();
        arg.putSerializable(DATA_SOURCE, dataSource);
        intent.putExtras(arg);
        return intent;
    }
}
