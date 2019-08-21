package ru.geekbrains.android1.data;

import java.io.Serializable;
import java.util.List;

public interface WeatherDataSource extends Serializable {
    WeatherDetailsData getData(String city);
    WeatherDetailsData getData(int cityIndex);

    void setData(String city, WeatherDetailsData data);
    void setData(int cityIndex, WeatherDetailsData data);
    void setAll(List<WeatherDetailsData> dataSource);

    int getIndex(String city);

    int size();
    boolean isEmpty();

    void addData(WeatherDetailsData weatherData);
    void addData(String city);

    void removeData(String city);
}
