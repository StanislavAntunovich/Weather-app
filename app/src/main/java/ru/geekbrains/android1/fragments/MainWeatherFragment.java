package ru.geekbrains.android1.fragments;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.adapters.CityWeatherAdapter;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.database.CityWeatherTable;
import ru.geekbrains.android1.network.WeatherDataLoader;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.rest.entities.WeatherRequest;
import ru.geekbrains.android1.utils.UnitsConverter;

public class MainWeatherFragment extends Fragment {
    private boolean isHorizontal;
    private WeatherDataSource dataSource;
    private ForecastListener listener;

    private CurrentInfoPresenter indexPresenter;
    private SettingsPresenter settingsPresenter;
    private LinearLayout paginationLayout;

    private List<ImageView> pagination;
    private CityWeatherAdapter adapter;

    private SQLiteDatabase database;

    private boolean isLocationEnabled = false;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateCurrentLocation(location);
            notifyDataUpdated();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            /* empty */
        }

        @Override
        public void onProviderEnabled(String s) {
            /* empty */
        }

        @Override
        public void onProviderDisabled(String s) {
            /* empty */
        }
    };

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
            dataSource = (WeatherDataSource) getArguments()
                    .getSerializable(MainActivity.DATA_SOURCE);
            setRecycler(view);

            isLocationEnabled = getArguments().getBoolean(MainActivity.IS_CURRENT_ENABLED);

        }
        paginationLayout = view.findViewById(R.id.ll_pagination);

        makePagination();
        sendUpdateRequest(dataSource.getData(indexPresenter.getCurrentIndex()).getCity());

        super.onViewCreated(view, savedInstanceState);
    }


    private void setRecycler(@NonNull View view) {
        adapter = new CityWeatherAdapter(dataSource, Objects.requireNonNull(getActivity()));
        adapter.setListener(city ->
                showForecast(view)
        );

        RecyclerView recycler = view.findViewById(R.id.recycler_main_weather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
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

                        sendUpdateRequest(dataSource.getData(index).getCity());
                        changeData(index);
                    }
                }
            }
        });
        recycler.scrollToPosition(indexPresenter.getCurrentIndex());
    }

    private void sendUpdateRequest(String city) {
        int unitsIndex = settingsPresenter.getTempUnitIndex();
        String units = UnitsConverter.getUntis(unitsIndex);

        final int index = indexPresenter.getCurrentIndex();
        String lang = settingsPresenter.getCurrentLocale().getLanguage();
        WeatherDataLoader.loadCurrentWeather(city, lang, units,
                new Callback<WeatherRequest>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<WeatherRequest> call,
                                           Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherDetailsData data = response.body().getData()[0];
                            if (isLocationEnabled && dataSource.getData(index).isCurrentLocation()) {
                                dataSource.addCurrentLocation(data);
                                if (database != null) {
                                    CityWeatherTable.updateCurrentLocation(database, data);
                                }
                            } else {
                                data.setCity(city);
                                dataSource.setData(city, data);
                                if (database != null) {
                                    CityWeatherTable.updateCity(database, data);
                                }
                            }
                            notifyDataUpdated();
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.network_error,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void makePagination() {
        pagination = new ArrayList<>();
        paginationLayout.removeAllViewsInLayout();
        int count = dataSource.size();
        int startIndex = 0;
        if (isLocationEnabled) {
            startIndex = 1;
            ImageView im = new ImageView(getContext());
            if (indexPresenter.getCurrentIndex() == 0) {
                im.setImageResource(R.drawable.ic_location_current);
            } else {
                im.setImageResource(R.drawable.ic_location);
            }
            paginationLayout.addView(im);
            pagination.add(im);
        }
        for (int i = startIndex; i < count; i++) {
            ImageView im = new ImageView(getContext());
            im.setImageResource(R.drawable.ic_pagination);
            paginationLayout.addView(im);
            pagination.add(im);
        }
        int currentIndex = indexPresenter.getCurrentIndex();
        int resToSet;
        if (isLocationEnabled && currentIndex == 0) {
            resToSet = R.drawable.ic_location_current;
        } else {
            resToSet = R.drawable.ic_pagination_current;
        }
        pagination.get(currentIndex)
                .setImageResource(resToSet);
    }

    private void changePagePosition(int previousIndex, int newIndex) {
        boolean isPreviousLocation = isLocationEnabled && previousIndex == 0;
        boolean isNewIsLocation = isLocationEnabled && newIndex == 0;

        if (isPreviousLocation) {
            pagination.get(previousIndex).setImageResource(R.drawable.ic_location);
        } else {
            pagination.get(previousIndex).setImageResource(R.drawable.ic_pagination);
        }

        if (isNewIsLocation) {
            pagination.get(newIndex).setImageResource(R.drawable.ic_location_current);
        } else {
            pagination.get(newIndex).setImageResource(R.drawable.ic_pagination_current);
        }
    }

    private void changeData(int index) {
        WeatherDetailsData data = dataSource.getData(index);
        Fragment fragment = DetailsWeatherFragment.create(data);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.weather_details_container, fragment)
                    .commit();
        }
        showForecast(null);
    }

    private void showForecast(View view) {
        FragmentManager manager = getFragmentManager();

        if (isHorizontal && manager != null) {
            Fragment fragment = ForecastFragment.create(dataSource);
            manager.beginTransaction()
                    .replace(R.id.forecast_container, fragment)
                    .commit();
        }
        if (view != null &&
                getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            listener.show();
        }
    }

    private void updateCurrentLocation(Location location) {
        double lon = location.getLongitude();
        double lat = location.getLatitude();

        int unitsIndex = settingsPresenter.getTempUnitIndex();
        String units = UnitsConverter.getUntis(unitsIndex);
        String lang = settingsPresenter.getCurrentLocale().getLanguage();

        WeatherDataLoader.loadCurrentWeather(lat, lon, lang, units,
                new Callback<WeatherRequest>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<WeatherRequest> call,
                                           Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherDetailsData data = response.body().getData()[0];
                            data.setIsCurrentLocation(true);
                            if (database != null) {
                                CityWeatherTable.updateCurrentLocation(database, data);
                            }
                            dataSource.addCurrentLocation(data);
                            notifyDataUpdated();
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.network_error,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setListener(ForecastListener listener) {
        this.listener = listener;
    }

    private void notifyDataUpdated() {
        adapter.notifyDataSetChanged();
        changeData(indexPresenter.getCurrentIndex());
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.savePreferences();
        }
    }

    public void setDB(SQLiteDatabase database) {
        this.database = database;
    }

    public LocationListener getLocationListener() {
        return this.locationListener;
    }

    public static MainWeatherFragment create(WeatherDataSource dataSource, boolean isCurrentEnabled) {
        MainWeatherFragment fragment = new MainWeatherFragment();
        Bundle args = new Bundle();
        args.putBoolean(MainActivity.IS_CURRENT_ENABLED, isCurrentEnabled);
        args.putSerializable(MainActivity.DATA_SOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }

    public interface ForecastListener {
        void show();
    }
}
