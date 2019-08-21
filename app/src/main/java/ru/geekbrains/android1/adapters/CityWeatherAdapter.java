package ru.geekbrains.android1.adapters;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.presenters.SettingsPresenter;
import ru.geekbrains.android1.utils.WeatherIconsConverter;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {

    private WeatherDataSource dataSource;
    private OnItemClickListener listener;
    private Activity activity;
    private SimpleDateFormat formatter;
    private Date currentDate;
    private SettingsPresenter settingsPresenter;

    public CityWeatherAdapter(WeatherDataSource dataSource, Activity activity) {
        this.settingsPresenter = SettingsPresenter.getInstance();
        this.dataSource = dataSource;
        this.activity = activity;
        this.currentDate = new Date();
        Locale locale = activity.getResources().getConfiguration().locale;
        this.formatter = new SimpleDateFormat("EEEE, MMM dd", locale);
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_city_weather, viewGroup, false);
        return new CityWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder holder, int position) {
        WeatherDetailsData data = dataSource.getData(position);
        String date = formatter.format(currentDate);
        String tempSuffix = getTempSuffix();
        int weatherCode = data.getWeatherCode();
        int ico = WeatherIconsConverter.getIconID(weatherCode);

        holder.txtDate.setText(date);
        holder.btnShowForecast.setVisibility(
                activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                        View.GONE : View.VISIBLE
        );

        holder.imgWeatherIcon.setImageResource(ico);
        holder.txtCity.setText(data.getCity());
        holder.txtTemperature.setText(data.getCurrentTemperature());
        holder.txtWeatherCondition.setText(data.getWeatherCondition());
        holder.txtTempSuffix.setText(tempSuffix);
        if (listener != null) {
            holder.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    private String getTempSuffix() {
        String[] units = activity.getResources().getStringArray(R.array.temp_units);
        int unitsIndex = settingsPresenter.getTempUnitIndex();
        return units[unitsIndex];
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
        private TextView txtTempSuffix;
        private TextView txtWeatherCondition;
        private TextView txtDate;
        private ImageView imgWeatherIcon;
        private Button btnShowForecast;

        CityWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.text_date);
            txtCity = itemView.findViewById(R.id.text_city);
            txtTemperature = itemView.findViewById(R.id.temperature_main_val);
            txtWeatherCondition = itemView.findViewById(R.id.weather_type);
            btnShowForecast = itemView.findViewById(R.id.bttn_more_info);
            txtTempSuffix = itemView.findViewById(R.id.txt_current_temp_suffix);
            imgWeatherIcon = itemView.findViewById(R.id.ic_weather_type);
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

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtTempSuffix() {
            return txtTempSuffix;
        }
    }
}
