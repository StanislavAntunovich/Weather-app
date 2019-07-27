package ru.geekbrains.android1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.ForecastData;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    ForecastData[] data;

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
        viewHolder.txtDay.setText(forecast.getDay());
        viewHolder.txtHighTemp.setText(forecast.getHighTemperature().toString());
        viewHolder.txtLowTemp.setText(forecast.getLowTemperature().toString());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private TextView txtHighTemp;
        private TextView txtLowTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.preview_day);
            txtLowTemp = itemView.findViewById(R.id.preview_temp_low);
            txtHighTemp = itemView.findViewById(R.id.preview_temp_high);
        }
    }
}
