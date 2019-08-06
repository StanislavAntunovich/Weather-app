package ru.geekbrains.android1.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class FakeDataSourceImp implements WeatherDataSource {
    private List<WeatherDetailsData> dataSource;

    public FakeDataSourceImp(Resources resources) {
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
    public void setData(String city, WeatherDetailsData data) {
        int index = getIndex(city);
        dataSource.set(index, data);
    }

    @Override
    public void setData(int cityIndex, WeatherDetailsData data) {
        dataSource.set(cityIndex, data);
    }

    @Override
    public int getIndex(String city) {
        for (int i = 0; i < dataSource.size(); i++) {
            if (city.equals(dataSource.get(i).getCity())) {
                return i;
            }
        }
        return -1;
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
    public void addData(WeatherDetailsData weatherData) {
        dataSource.add(weatherData);
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
