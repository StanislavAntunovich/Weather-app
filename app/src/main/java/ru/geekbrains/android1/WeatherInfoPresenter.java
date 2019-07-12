package ru.geekbrains.android1;

public class WeatherInfoPresenter {
    private static WeatherInfoPresenter instance;

    private static final Object lock = new Object();

    private Integer temperature;
    private Integer humidity;
    private Integer pressure;
    private Integer wind;
    private String weatherType;

    private WeatherInfoPresenter() {
        //TODO change types and vals (depends on income JSON)
        this.temperature = 19;
        this.humidity = 20;
        this.pressure = 995;
        this.wind = 4;
        this.weatherType = "Partly Cloudy";
    }

    public static WeatherInfoPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new WeatherInfoPresenter();
            }
        }
        return instance;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getTemperature() {
        return temperature.toString();
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity.toString();
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure.toString();
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind.toString();
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }
}
