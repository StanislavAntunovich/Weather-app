package ru.geekbrains.android1.data;

import java.util.List;

public class WeatherDetailsData {
    private String city;
    private Integer currentTemperature;

    private Integer humidity;
    private Integer pressure;
    private Integer wind;

    private List<ForecastData> forecast;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Integer currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public List<ForecastData> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastData> forecast) {
        this.forecast = forecast;
    }
}
