package ru.geekbrains.android1.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.geekbrains.android1.CityWeatherAdapter;
import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class MainWeatherFragment extends Fragment {
    boolean isHorizontal;
    CityWeatherAdapter adapter; //TODO возможно вывести отедльный интерфейс
    RecyclerView weatherRecycler;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String[] cities = view.getResources().getStringArray(R.array.cities);

        weatherRecycler = view.findViewById(R.id.fragment_weather_main);
        weatherRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        adapter = new CityWeatherAdapter(cities);
        weatherRecycler.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(weatherRecycler);

        super.onViewCreated(view, savedInstanceState);
    }

    public static Fragment create(WeatherDetailsData currentData, int currentIndex) {
        Fragment fragment = new MainWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.CURRENT_DATA, currentData);
        args.putInt(MainActivity.CURRENT_INDEX, currentIndex);
        fragment.setArguments(args);
        return fragment;
    }
}
