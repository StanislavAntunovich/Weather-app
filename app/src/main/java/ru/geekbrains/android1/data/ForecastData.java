package ru.geekbrains.android1.data;

import java.io.Serializable;

public class ForecastData implements Serializable {
    private String day;
    private int highTemperature;
    private int lowTemperature;

    public ForecastData(String day, Integer highTemperature, Integer lowTemperature) {
        this.day = day;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
    }

    public ForecastData() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(int highTemperature) {
        this.highTemperature = highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(int lowTemperature) {
        this.lowTemperature = lowTemperature;
    }
}
