package ru.geekbrains.android1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.data.WeatherDataSource;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private WeatherDataSource dataSource;
    private OnImgButtonClickListener listener;

    public CityListAdapter(WeatherDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_city_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtCity.setText(dataSource.getData(i).getCity());
        if (listener != null) {
            viewHolder.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setOnClickListener(OnImgButtonClickListener listener) {
        this.listener = listener;
    }

    // TODO дублируется с другим адаптером - вынести
    public interface OnImgButtonClickListener {
        void onBtnClick(String city);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCity;
        private ImageView btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCity = itemView.findViewById(R.id.txt_city_name);
            btnRemove = itemView.findViewById(R.id.btn_remove_city);
        }

        void setOnClickListener(OnImgButtonClickListener listener) {
            btnRemove.setOnClickListener(v -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                String city = txtCity.getText().toString();
                listener.onBtnClick(city);
            });
        }


    }
}
