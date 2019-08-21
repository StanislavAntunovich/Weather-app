package ru.geekbrains.android1.rest.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class WeatherCondition implements Serializable {
    @SerializedName("code")
    private int weatherCode;

    private String description;

    int getWeatherCode() {
        return weatherCode;
    }

    String getDescription() {
        return description;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
