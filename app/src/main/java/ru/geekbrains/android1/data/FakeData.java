package ru.geekbrains.android1.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FakeData {
    private String[] cities;
    private Map<String, WeatherDetailsData> data;

    public FakeData(String[] cities) {
        this.cities = cities;
        data = initData(cities);
    }

    public WeatherDetailsData getData(String city) {
        return data.get(city);
    }

    public WeatherDetailsData getData(int cityIndex) {
        return data.get(cities[cityIndex]);
    }

    private Map<String, WeatherDetailsData> initData(String[] cities) {
        Map<String, WeatherDetailsData> newData = new HashMap<>();
        Random r = new Random();

        for (String city : cities) {
            WeatherDetailsData weatherData = new WeatherDetailsData();
            weatherData.setCity(city);
            weatherData.setCurrentTemperature(r.nextInt(60) - 30);
            weatherData.setPressure(r.nextInt(160) + 800);
            weatherData.setHumidity(r.nextInt(100));
            weatherData.setWind(r.nextInt(20));
            newData.put(city, weatherData);
        }

        return newData;
    }

}
