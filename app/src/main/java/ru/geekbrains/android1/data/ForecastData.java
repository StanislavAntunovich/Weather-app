package ru.geekbrains.android1.data;

public class ForecastData {
    private String day;
    private Integer highTemperature;
    private Integer lowTemperature;

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

    public Integer getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(Integer highTemperature) {
        this.highTemperature = highTemperature;
    }

    public Integer getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(Integer lowTemperature) {
        this.lowTemperature = lowTemperature;
    }
}
