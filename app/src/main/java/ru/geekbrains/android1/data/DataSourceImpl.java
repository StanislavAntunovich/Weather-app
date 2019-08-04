package ru.geekbrains.android1.data;

import java.util.ArrayList;
import java.util.List;

public class DataSourceImpl implements WeatherDataSource {
    private List<WeatherDetailsData> data;

    public DataSourceImpl() {
        data = new ArrayList<>();
    }

    @Override
    public WeatherDetailsData getData(String city) {
        for (WeatherDetailsData datum : data) {
            if (datum.getCity().equals(city)) {
                return datum;
            }
        }
        return null;
    }

    @Override
    public WeatherDetailsData getData(int cityIndex) {
        return data.get(cityIndex);
    }

    @Override
    public void setData(String city, WeatherDetailsData data) {
        int index = this.getIndex(city);
        if (index >= 0) {
            this.data.set(index, data);
        } else {
            this.data.add(data);
        }
    }

    @Override
    public void setData(int cityIndex, WeatherDetailsData data) {
        this.data.set(cityIndex, data);
    }

    @Override
    public int getIndex(String city) {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i).getCity().equals(city)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.size() == 0;
    }

    @Override
    public void addData(WeatherDetailsData weatherData) {
        data.add(weatherData);
    }


    @Override
    public void removeData(String city) {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i).getCity().equals(city)) {
                data.remove(i);
                return;
            }
        }
    }
}
