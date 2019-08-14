package ru.geekbrains.android1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherCondition implements Serializable {
    @SerializedName("code")
    private String weatherCode;

    private String description;

    public String getWeatherCode() {
        return weatherCode;
    }

    public String getDescription() {
        return description;
    }
}
