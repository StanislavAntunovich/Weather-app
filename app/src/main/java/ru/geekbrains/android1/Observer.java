package ru.geekbrains.android1;

import ru.geekbrains.android1.data.WeatherDetailsData;

public interface Observer {
    void update(WeatherDetailsData newData, int newIndex);
}
