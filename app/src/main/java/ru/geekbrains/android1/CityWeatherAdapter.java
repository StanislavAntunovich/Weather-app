package ru.geekbrains.android1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.geekbrains.android1.data.FakeData;
import ru.geekbrains.android1.fragments.MainWeatherFragment;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {

    static MainWeatherFragment parent;


    private String[] cities;
    private FakeData data;
    private String[] conditions;

    public CityWeatherAdapter(String[] cities, String[] conditions, MainWeatherFragment parent) {
        this.cities = cities;
        this.data = FakeData.getInstance();
        this.conditions = conditions;
        this.parent = parent;
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_city_weather, viewGroup, false);
        return new CityWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder holder, int i) {
        holder.txtCity.setText(cities[i]);
        holder.txtTemperature.setText(data.getData(i).getCurrentTemperature().toString());
        holder.txtWeatherCondition.setText(conditions[data.getData(i).getWeatherCondition()]);
        holder.btnShowForecast.setOnClickListener(parent::showForecast);
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }


    public static class CityWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView txtCity;
        TextView txtTemperature;
        TextView txtWeatherCondition;
        Button btnShowForecast;

        public CityWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCity = itemView.findViewById(R.id.text_city);
            txtTemperature = itemView.findViewById(R.id.temperature_main_val);
            txtWeatherCondition = itemView.findViewById(R.id.weather_type);
            btnShowForecast = itemView.findViewById(R.id.bttn_more_info);
        }
    }
}
