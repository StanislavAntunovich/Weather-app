package ru.geekbrains.android1.data;

import android.content.res.Resources;

import java.util.Random;

import ru.geekbrains.android1.R;

public class FakeForecastBuilder {
    public static ForecastData[] getForecast(Resources resources) {
        Random r = new Random();
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
