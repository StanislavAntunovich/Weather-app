package ru.geekbrains.android1.rest.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDetailsData;

public class CurrentWeatherDataImpl implements WeatherDetailsData {
    @SerializedName("city_name")
    private String city;

    @SerializedName("country_code")
    private String country;

    @SerializedName("pres")
    private float pressure;

    @SerializedName("wind_spd")
    private float wind;

    @SerializedName("rh")
    private int humidity;

    @SerializedName("temp")
    private float temperature;

    @SerializedName("ts")
    private long lastUpdated;

    @SerializedName("weather")
    private WeatherCondition conditions;

    private ForecastData[] forecast;

    public String getCountry() {
        return country;
    }

    @Override
    public String getPressure() {
        return String.valueOf(pressure);
    }

    public String getWind() {
        return String.valueOf(wind);
    }

    @Override
    public String getWeatherCondition() {
        return conditions.getDescription();
    }

    @Override
    public Date getLastUpdated() {
        return new Date(lastUpdated);
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
    public void setCurrentTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public void setForecast(ForecastData[] forecast) {
        this.forecast = forecast;
    }

    @Override
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    @Override
    public void setWind(float wind) {
        this.wind = wind;
    }

    @Override
    public void setWeatherCondition(String condition) {
        if (this.conditions == null) {
            conditions = new WeatherCondition();
        }
        conditions.setDescription(condition);
    }

    @Override
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated.getTime();
    }

    @Override
    public void setWeatherCode(int code) {
        if (this.conditions == null) {
            conditions = new WeatherCondition();
        }
        conditions.setWeatherCode(code);
    }

    @Override
    public int getWeatherCode() {
        return conditions.getWeatherCode();
    }

    public String getHumidity() {
        return String.valueOf(humidity);
    }

    public String getCity() {
        return city;
    }

    @Override
    public String getCurrentTemperature() {
        return String.valueOf(Math.round(temperature));
    }


}
