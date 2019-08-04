package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.android1.data.FakeSourceBuilder;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.fragments.AddCityFragment;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.SettingsFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;

public class MainActivity extends AppCompatActivity {
    public static final String FORECAST = "FORECAST";
    public static final String DATA_SOURCE = "DATA_SOURCE";
    public static final String DETAILS = "DETAILS";

    public static final String SETTINGS_FRAGMENT_TAG = "SETTINGS_TAG";
    public static final String ADD_CITY_FRAGMENT_TAG = "ADD_CITY_FRAGMENT";
    public static final String MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT";
    public static final String FORECAST_FRAGMENT_TAG = "FORECAST_FRAGMENT";

    private static final String cvUrl = "https://spb.hh.ru/resume/293a3520ff0735e2c40039ed1f4e46727a7036";
    private static final String repoUrl = "https://github.com/StanislavAntunovich/Weather-app"; //TODO вынести в строковые ресурсы


    private WeatherDataSource dataSource;
    private CurrentInfoPresenter currentInfoPresenter;

    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentInfoPresenter = CurrentInfoPresenter.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer(toolbar);

        if (savedInstanceState == null) {
            dataSource = new FakeSourceBuilder()
                    .setResources(getResources())
                    .build();
            showMainFragments();
        }

    }

    private void initNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_cities:
                showAddCity();
                break;
            case R.id.nav_home:
                showMainFragments();
                break;
            case R.id.nav_settings:
                showSettings();
                break;
            case R.id.nav_forecast:
                showForecast();
                break;
            case R.id.nav_about_developer:
                about();
                break;
            case R.id.nav_share:
                share();
                break;
            case R.id.nav_send:
                send();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_city) {
            showAddCity();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!currentInfoPresenter.getFragmentsIndexes().empty()) {
            currentInfoPresenter.getFragmentsIndexes().pop();
            if (currentInfoPresenter.getFragmentsIndexes().peek() == R.id.nav_home) {
                showMainFragments();
            } else {
                navigationView.setCheckedItem(currentInfoPresenter.getFragmentsIndexes().peek());
            }
        }
    }

    private void showMainFragments() {
        currentInfoPresenter.getFragmentsIndexes().clear();
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_home);

        int currentIndex = currentInfoPresenter.getCurrentIndex();

        navigationView.setCheckedItem(R.id.nav_home);

        if (currentIndex < 0 || dataSource.isEmpty()) {
            showAddCity();
        } else {

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
            }

            transaction
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showForecast() {
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_forecast);
        navigationView.setCheckedItem(R.id.nav_forecast);

        ForecastData[] data = dataSource.getData(currentInfoPresenter.getCurrentIndex()).getForecast();
        Fragment forecastFragment = WeekForecastFragment.create(data);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .replace(R.id.main_container, forecastFragment, FORECAST_FRAGMENT_TAG)
                .commit();

    }

    private void showAddCity() {
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_cities);
        navigationView.setCheckedItem(R.id.nav_cities);

        AddCityFragment addCityFragment = AddCityFragment.create(dataSource);
        addCityFragment.setOnDoneListener(this::showMainFragments);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_container, addCityFragment, ADD_CITY_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

    }

    private void showSettings() {
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_settings);
        navigationView.setCheckedItem(R.id.nav_settings);

        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setOnDoneListener(this::showMainFragments);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_container, settingsFragment, SETTINGS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    private void share() {
        Intent repoInfo = new Intent(Intent.ACTION_SEND);
        repoInfo.putExtra(Intent.EXTRA_TEXT, repoUrl);
        repoInfo.setType("text/plain");
        if (repoInfo.resolveActivity(getPackageManager()) != null) {
            startActivity(repoInfo);
        }
    }

    private void about() {
        Uri uri = Uri.parse(cvUrl);
        Intent cvInfo = new Intent(Intent.ACTION_VIEW, uri);
        if (cvInfo.resolveActivity(getPackageManager()) != null) {
            startActivity(cvInfo);
        }
    }

    private void send() {
        Uri uri = Uri.parse("mailto:" + getString(R.string.developer_email));
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Question");
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }


}
