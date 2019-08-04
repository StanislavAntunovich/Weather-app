package ru.geekbrains.android1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class MainWeatherRequest implements Serializable {
    private int cod;
    private String name;
    private WeatherCondition[] weather;
    private MainWeather main;
    private Wind wind;

    @SerializedName("dt")
    private long lastUpdated;

    public int getCod() {
        return cod;
    }

    public String getName() {
        return name;
    }

    public WeatherCondition[] getWeather() {
        return weather;
    }

    public MainWeather getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Date getLastUpdated() {
        return new Date(lastUpdated);
    }
}
