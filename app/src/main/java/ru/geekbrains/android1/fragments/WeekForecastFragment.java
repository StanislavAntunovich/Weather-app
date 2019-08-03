package ru.geekbrains.android1.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        FragmentManager manager = getFragmentManager();
        if (view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && manager != null) {
            Fragment thisFragment = manager.findFragmentByTag(MainActivity.FORECAST_FRAGMENT_TAG);
            if (thisFragment != null && thisFragment.isResumed()) {
                manager.popBackStack();
            }
        }

        Bundle args = getArguments();
        if (args != null) {
            forecastData = (ForecastData[]) getArguments().getSerializable(MainActivity.FORECAST);
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

    public static WeekForecastFragment create(ForecastData[] forecast) {
        WeekForecastFragment fragment = new WeekForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.FORECAST, forecast);
        fragment.setArguments(args);
        return fragment;
    }

}
