package ru.geekbrains.android1.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.ShowForecastActivity;
import ru.geekbrains.android1.adapters.CityWeatherAdapter;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.presenters.CurrentIndexPresenter;

public class MainWeatherFragment extends Fragment {
    private boolean isHorizontal;
    private WeatherDataSource dataSource;

    private CityWeatherAdapter adapter;
    private CurrentIndexPresenter indexPresenter;

    private List<ImageView> pagination;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        indexPresenter = CurrentIndexPresenter.getInstance();
        pagination = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_main_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dataSource = (WeatherDataSource) getArguments().getSerializable(MainActivity.DATA_SOURCE); // TODO on savedInstance

        adapter = setRecycler(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private CityWeatherAdapter setRecycler(@NonNull View view) {
        CityWeatherAdapter adapter = new CityWeatherAdapter(dataSource);
        adapter.setListener(city ->
            showForecast(view, dataSource.getData(city).getForecast())
        );

        RecyclerView recycler = view.findViewById(R.id.recycler_main_weather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);

        recycler.setAdapter(adapter);
        new PagerSnapHelper().attachToRecyclerView(recycler);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int index = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (index != RecyclerView.NO_POSITION &&
                            index != indexPresenter.getCurrentIndex()) {
                        changePagePosition(indexPresenter.getCurrentIndex(), index);
                        indexPresenter.setCurrentIndex(index);
                        changeData(dataSource.getData(index));
                    }
                }
            }
        });
        makePagination(view);

        recycler.scrollToPosition(indexPresenter.getCurrentIndex());
        return adapter;
    }

    private void makePagination(View view) {
        int count = dataSource.size();
        LinearLayout ll = view.findViewById(R.id.ll_pagination);
        for (int i = 0; i < count; i++) {
            ImageView im = new ImageView(getContext());
            im.setImageResource(R.drawable.ic_pagination);
            ll.addView(im);
            pagination.add(im);
        }
        pagination.get(indexPresenter.getCurrentIndex())
                .setImageResource(R.drawable.ic_pagination_current);
    }

    private void changePagePosition(int previousIndex, int newIndex) {
        pagination.get(previousIndex).setImageResource(R.drawable.ic_pagination);
        pagination.get(newIndex).setImageResource(R.drawable.ic_pagination_current);

    }



    private void changeData(WeatherDetailsData data) {
        Fragment fragment = DetailsWeatherFragment.create(data);
        getFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, fragment)
                .commit();
        showForecast(null, data.getForecast());
    }

    public void showForecast(View view, ForecastData[] forecast) {
        if (isHorizontal) {
            Fragment fragment = WeekForecastFragment.create(forecast);
            getFragmentManager().beginTransaction()
            .replace(R.id.forecast_container, fragment)
            .commit();
        }
        if (view != null) {
            Intent intent = new Intent(getContext(), ShowForecastActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(MainActivity.FORECAST, forecast);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public static Fragment create(WeatherDataSource dataSource) {
        MainWeatherFragment fragment = new MainWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DATA_SOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }
}
