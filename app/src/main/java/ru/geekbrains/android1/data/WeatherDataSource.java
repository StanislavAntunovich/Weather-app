package ru.geekbrains.android1.data;

import java.io.Serializable;

public interface WeatherDataSource extends Serializable {
    WeatherDetailsData getData(String city);
    WeatherDetailsData getData(int cityIndex);

    int getIndex(String city);

    int size();
    boolean isEmpty();

    void addData(String city);

    void removeData(String city);
}
