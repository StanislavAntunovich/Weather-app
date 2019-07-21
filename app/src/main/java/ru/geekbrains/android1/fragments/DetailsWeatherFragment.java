package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class DetailsWeatherFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment create(WeatherDetailsData currentData) {
        Fragment fragment = new DetailsWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.CURRENT_DATA, currentData);
        fragment.setArguments(args);
        return fragment;
    }
}
