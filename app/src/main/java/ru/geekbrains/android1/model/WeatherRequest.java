package ru.geekbrains.android1.model;

public class WeatherRequest {
    private CurrentWeatherDataImpl[] data;
    private int count;

    public CurrentWeatherDataImpl[] getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }
}
