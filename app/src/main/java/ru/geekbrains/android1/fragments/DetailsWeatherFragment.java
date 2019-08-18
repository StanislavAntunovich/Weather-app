package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public class DetailsWeatherFragment extends Fragment {
    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtWind;
    private TextView txtWindSuffix;
    private TextView txtPressureSuffix;

    private SettingsPresenter presenter;
    private LinearLayout llHumidity;
    private LinearLayout llPressure;
    private LinearLayout llWind;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = SettingsPresenter.getInstance();
        return inflater.inflate(R.layout.fragment_details_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (getArguments() != null) {
            WeatherDetailsData data = (WeatherDetailsData) getArguments().getSerializable(MainActivity.DETAILS);
            if (data != null) {
                setData(data);
            }
        }
    }

    @Override
    public void onResume() {
        llHumidity.setVisibility(presenter.isHumidityChecked() ? View.VISIBLE : View.GONE);
        llPressure.setVisibility(presenter.isPressureChecked() ? View.VISIBLE : View.GONE);
        llWind.setVisibility(presenter.isWindChecked() ? View.VISIBLE : View.GONE);

        super.onResume();
    }

    private void initViews(@NonNull View view) {
        txtHumidity = view.findViewById(R.id.text_humidity_val);
        txtPressure = view.findViewById(R.id.text_pressure_val);
        txtWind = view.findViewById(R.id.text_wind_val);

        txtWindSuffix = view.findViewById(R.id.txt_wind_suffix);
        txtPressureSuffix = view.findViewById(R.id.txt_pressure_suffix);

        llHumidity = view.findViewById(R.id.ll_humidity);
        llPressure = view.findViewById(R.id.ll_pressure);
        llWind = view.findViewById(R.id.ll_wind);
    }



    private void setData(WeatherDetailsData data) {
        int unitsIndex = presenter.getTempUnitIndex();
        String pressureSuffix = getPressureSuffix(unitsIndex);
        String windSuffix = getHumSuffix(unitsIndex);

        txtWindSuffix.setText(windSuffix);
        txtPressureSuffix.setText(pressureSuffix);
        txtHumidity.setText(data.getHumidity());
        txtPressure.setText(data.getPressure());
        txtWind.setText(data.getWind());
    }

    private String getPressureSuffix(int unitsIndex) {
        String pressureSuffix;
        if (unitsIndex == 1) {
            pressureSuffix = getString(R.string.pressure_suffix_f);
        } else {
            pressureSuffix = getString(R.string.pressure_suffix_m);
        }
        return pressureSuffix;
    }

    private String getHumSuffix(int unitsIndex) {
        String windSuffix;
        if (unitsIndex == 1) {
            windSuffix = getString(R.string.wind_suffix_f);
        } else {
            windSuffix = getString(R.string.wind_suffix_m);
        }
        return windSuffix;
    }

    public static Fragment create(WeatherDetailsData data) {
        DetailsWeatherFragment fragment = new DetailsWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.DETAILS, data);
        fragment.setArguments(args);
        return fragment;
    }

}
