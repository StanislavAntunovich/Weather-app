package ru.geekbrains.android1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {
    private WeatherDataGetter dataGetter;
    private int currentIndex;

    private String[] cities;

    public CityWeatherAdapter(String[] cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_city_weather, viewGroup, false);
        return new CityWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder holder, int i) {
        currentIndex = i;
        holder.txtCity.setText(cities[i]);
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
