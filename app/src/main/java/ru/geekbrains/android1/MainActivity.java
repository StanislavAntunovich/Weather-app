package ru.geekbrains.android1;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentInfoPresenter.getFragmentsIndexes().empty() ||
                currentInfoPresenter.getFragmentsIndexes().peek() == R.id.nav_home) {
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
                removeFragment(String.valueOf(currentInfoPresenter.getFragmentsIndexes().pop()));
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (!currentInfoPresenter.getFragmentsIndexes().empty()
                    && currentInfoPresenter.getFragmentsIndexes().pop() != R.id.nav_home) {
                if (!currentInfoPresenter.getFragmentsIndexes().empty()
                        && currentInfoPresenter.getFragmentsIndexes().peek() == R.id.nav_home) {
                    showMainFragments();
                } else {
                    navigationView.setCheckedItem(currentInfoPresenter.getFragmentsIndexes().peek());
                }
            }
            super.onBackPressed();
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
                    .replace(R.id.main_weather_container, mainFragment, String.valueOf(R.id.nav_home));


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
                .replace(R.id.main_container, forecastFragment, String.valueOf(R.id.nav_forecast))
                .commit();

    }

    private void showAddCity() {
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_cities);
        navigationView.setCheckedItem(R.id.nav_cities);

        AddCityFragment addCityFragment = AddCityFragment.create(dataSource);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_container, addCityFragment, String.valueOf(R.id.nav_cities))
                .addToBackStack(null)
                .commit();

    }

    private void showSettings() {
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_settings);
        navigationView.setCheckedItem(R.id.nav_settings);

        SettingsFragment settingsFragment = new SettingsFragment();

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_container, settingsFragment, String.valueOf(R.id.nav_settings))
                .addToBackStack(null)
                .commit();
    }

    private void share() {
        Intent repoInfo = new Intent(Intent.ACTION_SEND);
        repoInfo.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app));
        repoInfo.setType("text/plain");
        if (repoInfo.resolveActivity(getPackageManager()) != null) {
            startActivity(repoInfo);
        } else {
            showToast(getString(R.string.txt_unable_share));
        }
    }

    private void about() {
        Uri uri = Uri.parse(getString(R.string.cv_url));
        Intent cvInfo = new Intent(Intent.ACTION_VIEW, uri);
        if (cvInfo.resolveActivity(getPackageManager()) != null) {
            startActivity(cvInfo);
        } else {
            showToast(getString(R.string.txt_unable_about));
        }
    }

    private void send() {
        Uri uri = Uri.parse("mailto:" + getString(R.string.developer_email));
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_subject));
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            showToast(getString(R.string.txt_unable_send));
        }

    }

    private void removeFragment(String fragmentTag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    private void startFragment(int containerID, Fragment fragment, String tag) {
        //TODO start all fragments from here

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
