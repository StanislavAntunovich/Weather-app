package ru.geekbrains.android1;

import ru.geekbrains.android1.data.WeatherDetailsData;

public interface WeatherDataGetter {
    WeatherDetailsData getCurrentData();
    int getCurrentIndex();
}
