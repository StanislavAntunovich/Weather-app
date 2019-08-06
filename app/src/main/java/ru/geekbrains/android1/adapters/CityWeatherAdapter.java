package ru.geekbrains.android1.adapters;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {

    private WeatherDataSource dataSource;
    private OnItemClickListener listener;
    private Activity activity;

    public CityWeatherAdapter(WeatherDataSource dataSource, Activity activity) {
        this.dataSource = dataSource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_city_weather, viewGroup, false);
        return new CityWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder holder, int position) {
        holder.btnShowForecast.setVisibility(
                activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                        View.GONE : View.VISIBLE
        );

        WeatherDetailsData data = dataSource.getData(position);
        holder.txtCity.setText(data.getCity());
        holder.txtTemperature.setText(data.getCurrentTemperature());
        holder.txtWeatherCondition.setText(data.getWeatherCondition());
        if (listener != null) {
            holder.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(String city);
    }


    public static class CityWeatherViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCity;
        private TextView txtTemperature;
        private TextView txtWeatherCondition;
        private Button btnShowForecast;

        CityWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCity = itemView.findViewById(R.id.text_city);
            txtTemperature = itemView.findViewById(R.id.temperature_main_val);
            txtWeatherCondition = itemView.findViewById(R.id.weather_type);
            btnShowForecast = itemView.findViewById(R.id.bttn_more_info);
        }

        void setOnClickListener(OnItemClickListener listener) {
            btnShowForecast.setOnClickListener(v -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                String city = txtCity.getText().toString();
                listener.onClick(city);
            });
        }

        public TextView getTxtCity() {
            return txtCity;
        }

        public TextView getTxtTemperature() {
            return txtTemperature;
        }

        public TextView getTxtWeatherCondition() {
            return txtWeatherCondition;
        }

    }
}
