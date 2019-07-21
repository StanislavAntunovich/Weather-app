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

import ru.geekbrains.android1.CityPresenter;
import ru.geekbrains.android1.CityWeatherAdapter;
import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.FakeData;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class MainWeatherFragment extends Fragment {
    boolean isHorizontal;
    CityWeatherAdapter adapter; //TODO возможно вывести отедльный интерфейс
    RecyclerView weatherRecycler;

    private CityPresenter cityPresenter;

    private FakeData fakeData;

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

        fakeData = new FakeData(cities);
        cityPresenter = CityPresenter.getInstance();
        cityPresenter.setCurrentData(fakeData.getData(cityPresenter.getCurrentCityIndex()));

        weatherRecycler = view.findViewById(R.id.fragment_weather_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        weatherRecycler.setLayoutManager(layoutManager);

        adapter = new CityWeatherAdapter(cities);
        weatherRecycler.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(weatherRecycler);

        weatherRecycler.scrollToPosition(cityPresenter.getCurrentCityIndex());

        weatherRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    cityPresenter.setCurrentCityIndex(layoutManager.findFirstCompletelyVisibleItemPosition());
                    changeData(fakeData.getData(cityPresenter.getCurrentCityIndex()));
                }
            }

        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void changeData(WeatherDetailsData currentData) {
        Fragment fragment = DetailsWeatherFragment.create(currentData);
        getFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, fragment)
                .commit();
    }

    private void showForecast() {
        if (isHorizontal) {
            getFragmentManager().beginTransaction();

        }
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
