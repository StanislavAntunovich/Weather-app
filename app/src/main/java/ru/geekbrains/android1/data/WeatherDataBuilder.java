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

    public static WeatherDetailsData buildData(String city) {
        WeatherDetailsData weatherData = new WeatherDetailsData();
        weatherData.setCity(city);
        weatherData.setCurrentTemperature(r.nextInt(60) - 30);
        weatherData.setPressure(r.nextInt(160) + 800);
        weatherData.setHumidity(r.nextInt(100));
        weatherData.setWind(r.nextInt(20));
        weatherData.setWeatherCondition(conditions[r.nextInt(conditions.length)]);
        weatherData.setForecast(getForecast());

        return weatherData;
    }

    private static ForecastData[] getForecast() {
        String[] days = resources.getStringArray(R.array.days_of_week);
        System.out.println("*********** " + days);
        ForecastData[] data = new ForecastData[days.length];

        int i = 0;
        for (String day : days) {
            System.out.println(" ------------- " + i);
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
