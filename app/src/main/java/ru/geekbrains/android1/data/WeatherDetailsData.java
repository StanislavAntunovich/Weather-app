package ru.geekbrains.android1.data;

import java.io.Serializable;
import java.util.Date;

public interface WeatherDetailsData extends Serializable {

    String getCity();

    String getCurrentTemperature();

    String getHumidity();

    String getPressure();

    String getWind();

    String getWeatherCondition();

    Date getLastUpdated();

    ForecastData[] getForecast();

    int getWeatherCode();

    void setCity(String city);

    void setCurrentTemperature(int temperature);

    void setForecast(ForecastData[] forecast);

    void setHumidity(int humidity);

    void setPressure(float pressure);

    void setWind(float wind);

    void setWeatherCondition(String condition);

    void setLastUpdated(Date lastUpdated);

    void setWeatherCode(int code);

    boolean isCurrentLocation();

    void setIsCurrentLocation(boolean isCurrent);

}
