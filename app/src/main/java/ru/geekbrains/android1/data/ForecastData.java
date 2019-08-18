package ru.geekbrains.android1.data;

import java.io.Serializable;

public interface ForecastData extends Serializable {
    String getDay();
    String getHighTemperature();
    String getLowTemperature();
    int getWeatherCode();
}
