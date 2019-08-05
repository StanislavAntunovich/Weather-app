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

        String hTemp = String.valueOf(forecast.getHighTemperature());
        String lTemp = String.valueOf(forecast.getLowTemperature());
        String day = forecast.getDay();

        viewHolder.txtDay.setText(day);
        viewHolder.txtHighTemp.setText(hTemp);
        viewHolder.txtLowTemp.setText(lTemp);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private TextView txtHighTemp;
        private TextView txtLowTemp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.preview_day);
            txtLowTemp = itemView.findViewById(R.id.preview_temp_low);
            txtHighTemp = itemView.findViewById(R.id.preview_temp_high);
        }
    }
}
