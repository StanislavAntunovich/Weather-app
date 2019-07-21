package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class WeekForecastFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeatherDetailsData data = (WeatherDetailsData) getArguments().getSerializable(MainActivity.CURRENT_DATA);
        if (data != null) {

        }

    }

    public static WeekForecastFragment create(WeatherDetailsData data) {
        WeekForecastFragment fragment = new WeekForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.CURRENT_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }
}
