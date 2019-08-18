package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.ForecastAdapter;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.network.WeatherDataLoader;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.rest.entities.ForecastRequest;
import ru.geekbrains.android1.utils.UnitsConverter;


public class ForecastFragment extends Fragment {
    private CurrentInfoPresenter infoPresenter;
    private SettingsPresenter settingsPresenter;
    private WeatherDataSource dataSource;
    private ForecastAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        infoPresenter = CurrentInfoPresenter.getInstance();
        settingsPresenter = SettingsPresenter.getInstance();
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int currentIndex = infoPresenter.getCurrentIndex();
        String lang = settingsPresenter.getCurrentLocale().getLanguage();

        Bundle args = getArguments();
        if (args != null) {
            dataSource = (WeatherDataSource) getArguments()
                    .getSerializable(MainActivity.FORECAST);
            WeatherDetailsData cityWeather = Objects.requireNonNull(dataSource)
                    .getData(currentIndex);
            ForecastData[] forecast = cityWeather.getForecast();
            setRecycler(view, forecast);
            sendRequest(cityWeather.getCity(), lang);
        }
    }

    private void sendRequest(String city, String lang) {
        int unitsIndex = settingsPresenter.getTempUnitIndex();
        String units = UnitsConverter.getUntis(unitsIndex);

        WeatherDataLoader.loadForecast(city, lang, units, 7,
                new Callback<ForecastRequest>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<ForecastRequest> call, Response<ForecastRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            ForecastData[] data = response.body().getData();
                            dataSource.getData(city).setForecast(data);
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<ForecastRequest> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.network_error,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setRecycler(@NonNull View view, ForecastData[] data) {
        adapter = new ForecastAdapter(data);
        RecyclerView recycler = view.findViewById(R.id.fragment_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }

    public static ForecastFragment create(WeatherDataSource dataSource) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.FORECAST, dataSource);
        fragment.setArguments(args);
        return fragment;
    }


}
