package ru.geekbrains.android1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.adapters.CityListAdapter;
import ru.geekbrains.android1.data.FakeForecastBuilder;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.presenters.CurrentIndexPresenter;

import static ru.geekbrains.android1.MainActivity.DATA_SOURCE;

public class AddCityActivity extends AppCompatActivity {
    private WeatherDataSource dataSource;
    private CityListAdapter adapter;

    private RecyclerView recycler;

    private EditText editCity;
    private ImageView btnAddCity;
    private Button btnDone;

    private CurrentIndexPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        if (savedInstanceState == null) {
            dataSource = (WeatherDataSource) getIntent().getExtras().getSerializable(DATA_SOURCE);
        }

        intiViews();
        setListeners();

        presenter = CurrentIndexPresenter.getInstance();
    }

    @Override
    protected void onResume() {
        setRecycler();
        super.onResume();
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

    private void setListeners() {
        btnAddCity.setOnClickListener(this::addCity);
        btnDone.setOnClickListener(this::done);
    }

    private void intiViews() {
        btnDone = findViewById(R.id.bttn_show_weather);
        btnAddCity = findViewById(R.id.btn_add_city);
        editCity = findViewById(R.id.edit_city);
    }

    private void setRecycler() {
        adapter = new CityListAdapter(dataSource);
        recycler = findViewById(R.id.recycler_cities_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(city -> {
            adapter.notifyItemRemoved(dataSource.getIndex(city));
            dataSource.removeData(city);
        });
    }

    private void addCity(View view) {
        String city = editCity.getText().toString();
        if (city.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.fill_city_name,Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        final Handler handler = new Handler();

        new Thread(() -> {
            WeatherDetailsData weatherData = WeatherDataProvider.getData(city);

            if (weatherData == null) {
                handler.post(() ->
                    showAlert(R.string.error, R.string.city_not_found));
                return;
            }
            weatherData.setForecast(FakeForecastBuilder.getForecast(getResources()));
            handler.post(() -> {
                dataSource.addData(weatherData);
                editCity.setText("");
                recycler.scrollToPosition(dataSource.size() - 1);
                adapter.notifyItemInserted(dataSource.size() - 1);
            });

        }).start();

    }

    private void showAlert(int title, int message) {
        AlertDialog dialog = new AlertDialog.Builder(AddCityActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Ok", (dialog1, which) -> dialog1.cancel())
                .create();

        dialog.show();
    }

    private void done(View view) {
        fixIndex();

        Intent intent = new Intent();
        Bundle agr = new Bundle();
        agr.putSerializable(DATA_SOURCE, dataSource);
        intent.putExtras(agr);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void fixIndex() {
        if (dataSource.size() < presenter.getCurrentIndex()) {
            presenter.setCurrentIndex(dataSource.size() - 1);
        }
    }
}
