package ru.geekbrains.android1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.utils.WeatherIconsConverter;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private ForecastData[] data;

    public ForecastAdapter(ForecastData[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_one_day_preview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ForecastData forecast = data[i];
        int weatherCode = forecast.getWeatherCode();
        int ico = WeatherIconsConverter.getIconID(weatherCode);

        String hTemp = String.valueOf(forecast.getHighTemperature());
        String lTemp = String.valueOf(forecast.getLowTemperature());
        String day = forecast.getDay();

        viewHolder.txtDay.setText(day);
        viewHolder.txtHighTemp.setText(hTemp);
        viewHolder.txtLowTemp.setText(lTemp);
        viewHolder.imgWeatherIco.setImageResource(ico);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.length;
        }
        return 0;
    }

    public void setData(ForecastData[] data) {
        this.data = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private TextView txtHighTemp;
        private TextView txtLowTemp;
        private ImageView imgWeatherIco;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.preview_day);
            txtLowTemp = itemView.findViewById(R.id.preview_temp_low);
            txtHighTemp = itemView.findViewById(R.id.preview_temp_high);
            imgWeatherIco = itemView.findViewById(R.id.forecast_weather_ico);
        }
    }
}
