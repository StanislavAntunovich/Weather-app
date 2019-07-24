package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.ForecastAdapter;
import ru.geekbrains.android1.data.ForecastData;

public class WeekForecastFragment extends Fragment {
    private ForecastData[] forecastData;
    private ForecastAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forecastData = (ForecastData[]) getArguments().getSerializable(MainActivity.FORECAST); //TODO savedInstance
        if (forecastData != null) {
            setRecycler(view, forecastData);

        }
    }

    private void setRecycler(@NonNull View view, ForecastData[] data) {
        adapter = new ForecastAdapter(data);
        RecyclerView recycler = view.findViewById(R.id.fragment_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }

    public static Fragment create(ForecastData[] forecast) {
        WeekForecastFragment fragment = new WeekForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.FORECAST, forecast);
        fragment.setArguments(args);
        return fragment;
    }

}
