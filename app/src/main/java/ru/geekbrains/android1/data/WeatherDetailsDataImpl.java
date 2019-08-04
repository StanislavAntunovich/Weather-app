package ru.geekbrains.android1.data;

import ru.geekbrains.android1.model.MainWeatherRequest;

public class WeatherDetailsDataImpl implements WeatherDetailsData {
    private String city;
    private Float currentTemperature;
    private Integer humidity;
    private Integer pressure;
    private String condition;
    private Integer wind;
    private String lastUpdated;

    private ForecastData[] forecast;

    public WeatherDetailsDataImpl(MainWeatherRequest mainWeather) {
        init(mainWeather);
    }

    private void init(MainWeatherRequest request) {
        city = request.getName();
        currentTemperature = request.getMain().getTemp();
        humidity = request.getMain().getHumidity();
        pressure = request.getMain().getPressure();
        condition = request.getWeather()[0].getMain();
        wind = request.getWind().getSpeed();
        lastUpdated = request.getLastUpdated().toString();
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCurrentTemperature() {
        int temp = Math.round(currentTemperature - 273);
        return String.valueOf(temp);
    }

    @Override
    public String getHumidity() {
        return String.valueOf(humidity);
    }

    @Override
    public String getPressure() {
        return String.valueOf(pressure);
    }

    @Override
    public String getWeatherCondition() {
        return condition;
    }

    @Override
    public String getWind() {
        return String.valueOf(wind);
    }

    @Override
    public String getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public ForecastData[] getForecast() {
        return forecast;
    }

    public void setForecast(ForecastData[] forecast) {
        this.forecast = forecast;
    }


}
