package ru.geekbrains.android1.rest.entities;

import com.google.gson.annotations.SerializedName;

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
    public String getLastUpdated() {
        return null;
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
