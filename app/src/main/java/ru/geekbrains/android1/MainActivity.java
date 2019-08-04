package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.geekbrains.android1.data.DataSourceImpl;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.SettingsDialogFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;
import ru.geekbrains.android1.presenters.CurrentIndexPresenter;

public class MainActivity extends AppCompatActivity {
    public static final String FORECAST = "FORECAST";
    public static final String DATA_SOURCE = "DATA_SOURCE";
    public static final String DETAILS = "DETAILS";
    public static final String SETTINGS_FRAGMENT_TAG = "SETTINGS_TAG";

    private static final int REQUEST_CODE = 42;

    private WeatherDataSource dataSource;
    private CurrentIndexPresenter presenter;
    private SettingsDialogFragment dialogFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = CurrentIndexPresenter.getInstance();

        dialogFragment = new SettingsDialogFragment();
        dialogFragment.setOnDismissListener(dialog -> setFragments());
        dialogFragment.setStartActivityListener(this::showForecast);

        if (savedInstanceState == null) {
            dataSource = new DataSourceImpl();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragments();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        dialogFragment.setOnDismissListener(null);
        super.onSaveInstanceState(outState);
        outState.putSerializable(DATA_SOURCE, dataSource);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dialogFragment.setOnDismissListener(dialog -> setFragments());
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
        if (id == R.id.menu_settings) {
            dialogFragment.show(getSupportFragmentManager(), SETTINGS_FRAGMENT_TAG);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            dataSource = (WeatherDataSource) data.getExtras().getSerializable(DATA_SOURCE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setFragments() {
        int currentIndex = presenter.getCurrentIndex();
        if (currentIndex < 0 || dataSource.isEmpty()) {
            showForecast();
        } else {
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
    }

    private Intent prepareIntent() {
        Intent intent = new Intent(getApplicationContext(), AddCityActivity.class);
        Bundle arg = new Bundle();
        arg.putSerializable(DATA_SOURCE, dataSource);
        intent.putExtras(arg);
        return intent;
    }

    private void showForecast() {
        Intent intent = prepareIntent();
        startActivityForResult(intent, REQUEST_CODE);
    }
}
