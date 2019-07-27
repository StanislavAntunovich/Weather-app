package ru.geekbrains.android1.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class FakeDataSource implements WeatherDataSource {
    private List<WeatherDetailsData> dataSource;

    public FakeDataSource(Resources resources) {
        this.dataSource = new ArrayList<>();
        WeatherDataBuilder.setResources(resources);
    }

    @Override
    public WeatherDetailsData getData(String city) {
        for (WeatherDetailsData data : dataSource) {
            if (data.getCity().equals(city)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public WeatherDetailsData getData(int cityIndex) {
        return dataSource.get(cityIndex);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public boolean isEmpty() {
        return dataSource.isEmpty();
    }

    @Override
    public void addData(String city) {
        WeatherDetailsData newData = WeatherDataBuilder.buildData(city);
        dataSource.add(newData);
    }

    @Override
    public void removeData(String city) {
        int size = size();
        for (int i = 0; i < size; i++) {
            WeatherDetailsData data = dataSource.get(i);
            if (city.equals(data.getCity())) {
                dataSource.remove(i);
                return;
            }
        }
    }
}
