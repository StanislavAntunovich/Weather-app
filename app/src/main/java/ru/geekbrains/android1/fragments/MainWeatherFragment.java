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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.CityWeatherAdapter;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.service.WeatherReceiveService;

public class MainWeatherFragment extends Fragment {
    private boolean isHorizontal;
    private WeatherDataSource dataSource;
    private ForecastListener listener;

    private CurrentInfoPresenter indexPresenter;
    private SettingsPresenter settingsPresenter;
    private LinearLayout paginationLayout;

    private List<ImageView> pagination;
    private CityWeatherAdapter adapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        indexPresenter = CurrentInfoPresenter.getInstance();
        settingsPresenter = SettingsPresenter.getInstance();
        pagination = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_main_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            dataSource = (WeatherDataSource) getArguments().getSerializable(MainActivity.DATA_SOURCE); // TODO on savedInstance
        }
        paginationLayout = view.findViewById(R.id.ll_pagination);

        setRecycler(view);
        makePagination();
        sendUpdateRequest(dataSource.getData(indexPresenter.getCurrentIndex()).getCity());

        super.onViewCreated(view, savedInstanceState);
    }


    private void setRecycler(@NonNull View view) {
        adapter = new CityWeatherAdapter(dataSource, Objects.requireNonNull(getActivity()));
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
                        changeData(index);
                        sendUpdateRequest(dataSource.getData(index).getCity());
                    }
                }
            }
        });
        recycler.scrollToPosition(indexPresenter.getCurrentIndex());
    }

    private void sendUpdateRequest(String city) {
        String lang = settingsPresenter.getCurrentLocale().getLanguage();
        Intent intent = new Intent(getContext(), WeatherReceiveService.class);
        intent.putExtra(MainActivity.CITY, city);
        intent.putExtra(MainActivity.ACTION, MainActivity.ACTION_SET);
        intent.putExtra(MainActivity.LANG, lang);
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    private void makePagination() {
        pagination = new ArrayList<>();
        paginationLayout.removeAllViewsInLayout();
        int count = dataSource.size();
        for (int i = 0; i < count; i++) {
            ImageView im = new ImageView(getContext());
            im.setImageResource(R.drawable.ic_pagination);
            paginationLayout.addView(im);
            pagination.add(im);
        }
        pagination.get(indexPresenter.getCurrentIndex())
                .setImageResource(R.drawable.ic_pagination_current);
    }

    private void changePagePosition(int previousIndex, int newIndex) {
        pagination.get(previousIndex).setImageResource(R.drawable.ic_pagination);
        pagination.get(newIndex).setImageResource(R.drawable.ic_pagination_current);

    }

    private void changeData(int index) {
        WeatherDetailsData data = dataSource.getData(index);
        Fragment fragment = DetailsWeatherFragment.create(data);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.weather_details_container, fragment)
                    .commit();
        }
        showForecast(null, data.getForecast());
    }

    private void showForecast(View view, ForecastData[] forecast) {
        FragmentManager manager = getFragmentManager();

        if (isHorizontal && manager != null) {
            Fragment fragment = WeekForecastFragment.create(forecast);
            manager.beginTransaction()
                    .replace(R.id.forecast_container, fragment)
                    .commit();
        }
        if (view != null &&
                getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            listener.show();
        }
    }

    public void setListener(ForecastListener listener) {
        this.listener = listener;
    }

    public void notifyDataUpdated() {
        adapter.notifyDataSetChanged();
        changeData(indexPresenter.getCurrentIndex());
    }

    public static MainWeatherFragment create(WeatherDataSource dataSource) {
        MainWeatherFragment fragment = new MainWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DATA_SOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }

    public interface ForecastListener {
        void show();
    }
}
