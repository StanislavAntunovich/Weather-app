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

    void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
