package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.geekbrains.android1.CityPresenter;
import ru.geekbrains.android1.R;

public class WeekForecastFragment extends Fragment {

    CityPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = CityPresenter.getInstance();

        TextView city = view.findViewById(R.id.tempCity);
        city.setText(presenter.getCurrentData().getCity());


    }

}
