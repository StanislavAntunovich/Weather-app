package ru.geekbrains.android1.data;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android1.rest.entities.CurrentWeatherDataImpl;

public class DataSourceImp implements WeatherDataSource {
    private List<WeatherDetailsData> dataSource;

    public DataSourceImp() {
        this.dataSource = new ArrayList<>();
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
    public void setData(int cityIndex, WeatherDetailsData data) {
        dataSource.set(cityIndex, data);
    }

    @Override
    public void setAll(List<WeatherDetailsData> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getIndex(String city) {
        for (int i = 0; i < dataSource.size(); i++) {
            if (city.equals(dataSource.get(i).getCity()) && !dataSource.get(i).isCurrentLocation()) {
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
        WeatherDetailsData data = new CurrentWeatherDataImpl();
        data.setCity(city);
        dataSource.add(data);
    }

    @Override
    public void removeData(String city) {
        int size = size();
        for (int i = 0; i < size; i++) {
            WeatherDetailsData data = dataSource.get(i);
            if (city.equals(data.getCity()) && !data.isCurrentLocation()) {
                dataSource.remove(i);
                return;
            }
        }
    }

    @Override
    public void addCurrentLocation(WeatherDetailsData data) {
        data.setIsCurrentLocation(true);
        int size = dataSource.size();
        for (int i = 0; i < size; i++) {
            if (dataSource.get(i).isCurrentLocation()) {
                dataSource.set(i, data);
                return;
            }
        }
        dataSource.add(0, data);
    }

    @Override
    public WeatherDetailsData getCurrentLocation() {
        for (WeatherDetailsData data : dataSource) {
            if (data.isCurrentLocation()) {
                return data;
            }
        }
        return null;
    }
}
