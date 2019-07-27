package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class DetailsWeatherFragment extends Fragment {
    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtWind;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        txtHumidity = view.findViewById(R.id.text_humidity_val);
        txtPressure = view.findViewById(R.id.text_pressure_val);
        txtWind = view.findViewById(R.id.text_wind_val);

        WeatherDetailsData data = (WeatherDetailsData) getArguments().getSerializable(MainActivity.DETAILS);
        if (data != null) {
            setData(data);
        }
    }

    private void setData(WeatherDetailsData data) {
        txtHumidity.setText(data.getHumidity().toString());
        txtPressure.setText(data.getPressure().toString());
        txtWind.setText(data.getWind().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment create(WeatherDetailsData data) {
        DetailsWeatherFragment fragment = new DetailsWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DETAILS, data);
        fragment.setArguments(args);
        return fragment;
    }

}
