package ru.geekbrains.android1.data;

import java.io.Serializable;
import java.util.List;

public class WeatherDetailsData implements Serializable {
    private String city;
    private int weatherCondition;
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

    public int getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(int weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}
