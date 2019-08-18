package ru.geekbrains.android1.fragments;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.CityListAdapter;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.network.WeatherDataLoader;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.rest.entities.WeatherRequest;

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
            ((MainActivity) Objects.requireNonNull(getActivity())).savePreferences();
        });
    }

    private void addCity(View view) {
        String city = editCity.getText().toString();
        if (city.isEmpty()) {
            Toast.makeText(getContext(), R.string.fill_city_name, Toast.LENGTH_SHORT)
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
        String lang = settingsPresenter.getCurrentLocale().getLanguage();
        WeatherDataLoader.loadCurrentWeather(city, lang, "M", new Callback<WeatherRequest>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<WeatherRequest> call,
                                   Response<WeatherRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    WeatherDetailsData data = response.body().getData()[0];
                    data.setCity(city);
                    dataSource.addData(data);
                    notifyDataUpdated();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<WeatherRequest> call, Throwable t) {
                Toast.makeText(getContext(), "network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fixIndex() {
        if (dataSource.size() < presenter.getCurrentIndex()) {
            presenter.setCurrentIndex(dataSource.size() - 1);
        }
    }

    private void notifyDataUpdated() {
        recycler.scrollToPosition(dataSource.size() - 1);
        adapter.notifyItemInserted(dataSource.size() - 1);
        ((MainActivity) Objects.requireNonNull(getActivity())).savePreferences();
    }

    public static AddCityFragment create(WeatherDataSource dataSource) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DATA_SOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }

}
