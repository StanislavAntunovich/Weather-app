package ru.geekbrains.android1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.geekbrains.android1.data.FakeSourceBuilder;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.fragments.AddCityFragment;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;
import ru.geekbrains.android1.presenters.CurrentIndexPresenter;

public class MainActivity extends AppCompatActivity {
    public static final String FORECAST = "FORECAST";
    public static final String DATA_SOURCE = "DATA_SOURCE";
    public static final String DETAILS = "DETAILS";

    public static final String SETTINGS_FRAGMENT_TAG = "SETTINGS_TAG";
    public static final String ADD_CITY_FRAGMENT_TAG = "ADD_CITY_FRAGMENT";
    public static final String MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT";
    public static final String FORECAST_FRAGMENT_TAG = "FORECAST_FRAGMENT";

    private WeatherDataSource dataSource;
    private CurrentIndexPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = CurrentIndexPresenter.getInstance();

        if (savedInstanceState == null) {
            dataSource = new FakeSourceBuilder()
                    .setResources(getResources())
                    .build();
        }
    }

    private void initSideMenu(Toolbar toolbar) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMainFragments();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DATA_SOURCE, dataSource);
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
        //TODO

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showMainFragments();
    }

    private void showMainFragments() {
        int currentIndex = presenter.getCurrentIndex();

//        if (currentIndex < 0 || dataSource.isEmpty()) {
//            showAddCity();
//        } else {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        MainWeatherFragment mainFragment = MainWeatherFragment.create(dataSource);
        mainFragment.setListener(this::showForecast);
        transaction
                .replace(R.id.main_weather_container, mainFragment, MAIN_FRAGMENT_TAG);


        Fragment detailsFragment = DetailsWeatherFragment.create(dataSource.getData(currentIndex));
        transaction
                .replace(R.id.weather_details_container, detailsFragment);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment forecastFragment = WeekForecastFragment.create(
                    dataSource.getData(currentIndex).getForecast()
            );
            transaction
                    .replace(R.id.forecast_container, forecastFragment);
//            }
        }
        transaction.commit();
    }

    private void showForecast(Fragment forecastFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .replace(R.id.main_activity_container, forecastFragment, FORECAST_FRAGMENT_TAG)
                .commit();
    }

    private void showAddCity() {
        AddCityFragment addCityFragment = AddCityFragment.create(dataSource);
        addCityFragment.setOnDoneListener(() -> {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ADD_CITY_FRAGMENT_TAG);
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(fragment)
                        .commit();
                showMainFragments();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_activity_container, addCityFragment, ADD_CITY_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

    }
}
