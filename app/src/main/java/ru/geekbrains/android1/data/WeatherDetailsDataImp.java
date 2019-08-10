package ru.geekbrains.android1.data;

import java.util.Date;

public class WeatherDetailsDataImp implements WeatherDetailsData {
    private String city;
    private String weatherCondition;
    private Integer currentTemperature;

    private Integer humidity;
    private Integer pressure;
    private Integer wind;

    private ForecastData[] forecast;

    WeatherDetailsDataImp(String city, String weatherCondition, Integer currentTemperature, Integer humidity, Integer pressure, Integer wind) {
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.currentTemperature = currentTemperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
    }


    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCurrentTemperature() {
        return currentTemperature.toString();
    }

    @Override
    public String getHumidity() {
        return humidity.toString();
    }

    @Override
    public String getPressure() {
        return pressure.toString();
    }

    @Override
    public String getWind() {
        return wind.toString();
    }

    @Override
    public String getWeatherCondition() {
        return weatherCondition;
    }

    @Override
    public String getLastUpdated() {
        return new Date().toString();
    }

    @Override
    public ForecastData[] getForecast() {
        return forecast;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setForecast(ForecastData[] forecast) {
        this.forecast = forecast;
    }

}
