package ru.geekbrains.android1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {
    private WeatherDataGetter dataGetter;

    private String[] cities;

    public CityWeatherAdapter(String[] cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder cityWeatherViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    public static class CityWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView txtCity;
        TextView txtTemperature;
        TextView txtWeatherCondition;

        public CityWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCity = itemView.findViewById(R.id.text_city);
            txtTemperature = itemView.findViewById(R.id.temperature_main_val);
            txtWeatherCondition = itemView.findViewById(R.id.weather_type);
        }
    }
}
