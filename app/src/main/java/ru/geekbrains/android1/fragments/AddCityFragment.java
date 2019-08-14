package ru.geekbrains.android1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.CityListAdapter;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.service.WeatherReceiveService;

public class AddCityFragment extends Fragment {
    private WeatherDataSource dataSource;
    private CurrentInfoPresenter presenter;

    private CityListAdapter adapter;

    private RecyclerView recycler;
    private SettingsPresenter settingsPresenter;

    private EditText editCity;
    private Button btnAddCity;
    private Button btnDone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPresenter = SettingsPresenter.getInstance();
        Bundle args = getArguments();
        if (args != null) {
            dataSource = (WeatherDataSource) args.getSerializable(MainActivity.DATA_SOURCE);
            presenter = CurrentInfoPresenter.getInstance();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiViews(view);
        setRecycler();
        setListeners();
    }

    private void setListeners() {
        btnAddCity.setOnClickListener(this::addCity);
        btnDone.setOnClickListener(this::done);
    }

    private void intiViews(View view) {
        btnDone = view.findViewById(R.id.btn_add_city_done);
        btnAddCity = view.findViewById(R.id.btn_add_city);
        editCity = view.findViewById(R.id.edit_city);
        recycler = view.findViewById(R.id.recycler_cities_list);
    }

    private void setRecycler() {
        adapter = new CityListAdapter(dataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
            Toast.makeText(getContext(), R.string.fill_city_name,Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        sendRequest(city);
        editCity.setText("");
    }

    private void done(View view) {
        fixIndex();
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void sendRequest(String city) {
        Intent intent = new Intent(getContext(), WeatherReceiveService.class);
        intent.putExtra(MainActivity.CITY, city);
        intent.putExtra(MainActivity.ACTION, MainActivity.ACTION_ADD);
        intent.putExtra(MainActivity.LANG, settingsPresenter.getCurrentLocale().getLanguage());
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    private void fixIndex() {
        if (dataSource.size() < presenter.getCurrentIndex()) {
            presenter.setCurrentIndex(dataSource.size() - 1);
        }
    }

    public void notifyDataUpdated() {
        recycler.scrollToPosition(dataSource.size() - 1);
        adapter.notifyItemInserted(dataSource.size() - 1);
    }

    public static AddCityFragment create(WeatherDataSource dataSource) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DATA_SOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }

}
