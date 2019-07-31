package ru.geekbrains.android1.data;

import java.io.Serializable;

public interface WeatherDataSource extends Serializable {
    WeatherDetailsData getData(String city);
    WeatherDetailsData getData(int cityIndex);

    void setData(String city, WeatherDetailsData data);
    void setData(int cityIndex, WeatherDetailsData data);

    int getIndex(String city);

    int size();
    boolean isEmpty();

    void addData(WeatherDetailsData weatherData);

    void removeData(String city);
}
