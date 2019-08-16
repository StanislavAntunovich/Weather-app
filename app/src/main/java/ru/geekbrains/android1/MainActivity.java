package ru.geekbrains.android1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
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

import java.util.Locale;
import java.util.Objects;

import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.fragments.AddCityFragment;
import ru.geekbrains.android1.fragments.DetailsWeatherFragment;
import ru.geekbrains.android1.fragments.MainWeatherFragment;
import ru.geekbrains.android1.fragments.SettingsFragment;
import ru.geekbrains.android1.fragments.WeekForecastFragment;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.utils.SharedPrefsSettings;
import ru.geekbrains.android1.view.SensorsView;

import static ru.geekbrains.android1.service.WeatherReceiveService.BROADCAST_ACTION;

public class MainActivity extends AppCompatActivity {
    public static final String FORECAST = "FORECAST";
    public static final String DATA_SOURCE = "DATA_SOURCE";
    public static final String DETAILS = "DETAILS";
    public static final String WEATHER_DATA = "WEATHER_DATA";

    public static final String RESULT = "RESULT_CODE";
    public static final String ACTION = "ACTION_CODE";

    public static final String OK = "RESULT_OK";
    public static final String ERROR = "RESULT_ERROR";
    public static final String ACTION_ADD = "ADD";
    public static final String ACTION_SET = "SET";

    public static final String CITY = "CITY_DATA";
    public static final String LANG = "LANGUAGE";


    private WeatherDataSource dataSource;
    private CurrentInfoPresenter currentInfoPresenter;
    private SettingsPresenter settingsPresenter;

    private NavigationView navigationView;
    private SensorsView sensorsView;

    private WeatherDataReceiver receiver = new WeatherDataReceiver();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentInfoPresenter = CurrentInfoPresenter.getInstance();
        settingsPresenter = SettingsPresenter.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer(toolbar);

        sensorsView = findViewById(R.id.sensors_view);
        sensorsView.addSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, getString(R.string.current_temperature));
        sensorsView.addSensor(Sensor.TYPE_RELATIVE_HUMIDITY, getString(R.string.current_humidity));

        if (savedInstanceState == null) {
            Context context = getApplicationContext();
            SharedPrefsSettings.readSettings(context);
            dataSource = SharedPrefsSettings.readDataSource(context);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Locale currentLocale = getResources().getConfiguration().locale;
        currentLocale.getDisplayLanguage();
        settingsPresenter.setCurrentLocale(currentLocale);
        registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentInfoPresenter.getFragmentsIndexes().empty() ||
                currentInfoPresenter.getFragmentsIndexes().peek() == R.id.nav_home) {
            showMainFragments();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorsView.unregisterListeners();
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
            super.onBackPressed();
            if (!currentInfoPresenter.getFragmentsIndexes().empty()
                    && currentInfoPresenter.getFragmentsIndexes().pop() != R.id.nav_home) {
                if (!currentInfoPresenter.getFragmentsIndexes().empty()
                        && currentInfoPresenter.getFragmentsIndexes().peek() == R.id.nav_home) {
                    showMainFragments();
                } else {
                    navigationView.setCheckedItem(currentInfoPresenter.getFragmentsIndexes().peek());
                }
            } else {
                finish();
            }
        }
    }

    private void savePreferences() {
        Context context = getApplicationContext();
        SharedPrefsSettings.saveDataSource(context, dataSource);
        int index = currentInfoPresenter.getCurrentIndex();
        SharedPrefsSettings.saveCurrentIndex(context, index);
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

    private void showMainFragments() {
        sensorsView.registerListeners();
        currentInfoPresenter.getFragmentsIndexes().clear();
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_home);

        int currentIndex = currentInfoPresenter.getCurrentIndex();

        navigationView.setCheckedItem(R.id.nav_home);


        if (currentIndex < 0 || dataSource.isEmpty()) {
            showAddCity();
        } else {

            MainWeatherFragment mainFragment = MainWeatherFragment.create(dataSource);
            mainFragment.setListener(this::showForecast);
            Fragment detailsFragment = DetailsWeatherFragment.create(dataSource.getData(currentIndex));

            startFragment(R.id.main_weather_container, mainFragment, String.valueOf(R.id.nav_home));
            startFragment(R.id.weather_details_container, detailsFragment, null);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Fragment forecastFragment = WeekForecastFragment.create(
                        dataSource.getData(currentIndex).getForecast()
                );
                startFragment(R.id.forecast_container, forecastFragment, String.valueOf(R.id.nav_forecast));
            }
        }
    }

    private void showForecast() {
        int currentIndex = currentInfoPresenter.getCurrentIndex();
        if (currentIndex < 0 || dataSource.isEmpty()) {
            showAddCity();
        } else {
            sensorsView.unregisterListeners();
            currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_forecast);
            navigationView.setCheckedItem(R.id.nav_forecast);

            ForecastData[] data = dataSource.getData(currentInfoPresenter.getCurrentIndex()).getForecast();
            Fragment forecastFragment = WeekForecastFragment.create(data);

            startFragment(R.id.main_container, forecastFragment, String.valueOf(R.id.nav_forecast));
        }
    }

    private void showAddCity() {
        sensorsView.unregisterListeners();
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_cities);
        navigationView.setCheckedItem(R.id.nav_cities);

        AddCityFragment addCityFragment = AddCityFragment.create(dataSource);

        startFragment(R.id.main_container, addCityFragment, String.valueOf(R.id.nav_cities));
    }

    private void showSettings() {
        sensorsView.unregisterListeners();
        currentInfoPresenter.getFragmentsIndexes().push(R.id.nav_settings);
        navigationView.setCheckedItem(R.id.nav_settings);

        SettingsFragment settingsFragment = new SettingsFragment();

        startFragment(R.id.main_container, settingsFragment, String.valueOf(R.id.nav_settings));
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
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerID, fragment, tag)
                .addToBackStack(null)
                .commit();

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private class WeatherDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(RESULT);
            if (result.equals(OK)) {
                String action = intent.getStringExtra(ACTION);
                WeatherDetailsData data = (WeatherDetailsData)
                        Objects.requireNonNull(intent.getExtras()).getSerializable(WEATHER_DATA);
                if (data != null) {
                    if (action.equals(ACTION_SET)) {
                        dataSource.setData(data.getCity(), data);
                        MainWeatherFragment mainFragment = (MainWeatherFragment)
                                getSupportFragmentManager()
                                        .findFragmentByTag(String.valueOf(R.id.nav_home));
                        if (mainFragment != null) {
                            mainFragment.notifyDataUpdated();
                        }
                    } else if (action.equals(ACTION_ADD)) {
                        dataSource.addData(data);
                        AddCityFragment addCityFragment = (AddCityFragment)
                                getSupportFragmentManager()
                                        .findFragmentByTag(String.valueOf(R.id.nav_cities));
                        if (addCityFragment != null) {
                            addCityFragment.notifyDataUpdated();
                        }
                    }
                }
            } else if (result.equals(ERROR)) {
                showToast(getString(R.string.error));
            } else {
                showToast(getString(R.string.smth_went_wrong));
            }
        }
    }
}
