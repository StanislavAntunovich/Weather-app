package ru.geekbrains.android1.rest.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherCondition implements Serializable {
    @SerializedName("code")
    private String weatherCode;

    private String description;

    public String getWeatherCode() {
        return weatherCode;
    }

    String getDescription() {
        return description;
    }
}
