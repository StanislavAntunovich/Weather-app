package ru.geekbrains.android1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.adapters.CityListAdapter;
import ru.geekbrains.android1.data.WeatherDataSource;
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

    private void setListeners() {
        btnAddCity.setOnClickListener(this::addCity);
        btnDone.setOnClickListener(this::done);
    }

    private void intiViews() {
        btnDone = findViewById(R.id.bttn_show_weather);
        btnAddCity = findViewById(R.id.btn_add_city);
        editCity = findViewById(R.id.edit_city);
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

    private void setRecycler() {
        adapter = new CityListAdapter(dataSource);
        recycler = findViewById(R.id.recycler_cities_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(city -> {
            dataSource.removeData(city);
            adapter.notifyDataSetChanged();
        });
    }

    private void addCity(View view) {
        String city = editCity.getText().toString();
        if (city.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.fill_city_name,Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        dataSource.addData(city);
        editCity.setText("");
        adapter.notifyDataSetChanged();
        recycler.scrollToPosition(dataSource.size() - 1);
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
