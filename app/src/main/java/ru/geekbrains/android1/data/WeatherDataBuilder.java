package ru.geekbrains.android1.data;

import android.content.res.Resources;

import java.util.Random;

import ru.geekbrains.android1.R;

public class WeatherDataBuilder {
    private static Random r = new Random();
    private static Resources resources;
    private static String[] conditions;

    public static void setResources(Resources resources) {
        WeatherDataBuilder.resources = resources;
        WeatherDataBuilder.conditions = resources.getStringArray(R.array.conditions);
    }

    static WeatherDetailsData buildData(String city) {
        Integer temp = r.nextInt(60) - 30;
        Integer pressure = r.nextInt(160) + 800;
        Integer humidity = r.nextInt(100);
        Integer wind = r.nextInt(20);
        String weatherConditions = conditions[r.nextInt(conditions.length)];

        WeatherDetailsData weatherData = new WeatherDetailsDataImp(
                city, weatherConditions, temp, humidity, pressure, wind
        );
        weatherData.setForecast(getForecast());
        return weatherData;
    }

    private static ForecastData[] getForecast() {
        String[] days = resources.getStringArray(R.array.days_of_week);
        ForecastData[] data = new ForecastData[days.length];

        int i = 0;
        for (String day : days) {
            ForecastData forecastData = new ForecastData();
            forecastData.setDay(day);
            forecastData.setHighTemperature(r.nextInt(60) - 30);
            forecastData.setLowTemperature(forecastData.getHighTemperature() - 5);
            data[i] = forecastData;
            i++;
        }

        return data;
    }
}

