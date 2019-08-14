package ru.geekbrains.android1.data;

import java.io.Serializable;

public interface WeatherDetailsData extends Serializable {

    String getCity();

    //TODO разбить на Цельсий и Фаренгейт
    String getCurrentTemperature();

    String getHumidity();

    String getPressure();

    String getWind();

    String getWeatherCondition();

    String getLastUpdated();

    ForecastData[] getForecast();

    void setCity(String city);

    void setForecast(ForecastData[] forecast);

}
