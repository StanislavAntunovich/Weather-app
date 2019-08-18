package ru.geekbrains.android1.rest.entities;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public class ForecastDataImpl implements ForecastData {

    @SerializedName("valid_date")
    private String date;

    @SerializedName("min_temp")
    private float minTemp;

    @SerializedName("max_temp")
    private float maxTemp;

    @SerializedName("weather")
    private WeatherCondition condition;


    @Override
    public String getDay() {
        SettingsPresenter settings = SettingsPresenter.getInstance();
        Locale locale = settings.getCurrentLocale();
        String day = null;
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd", locale)
                    .parse(date);
            if (parsedDate != null) {
                day = new SimpleDateFormat("EEEE", locale).format(parsedDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    @Override
    public String getHighTemperature() {
        return String.valueOf(maxTemp);
    }

    @Override
    public String getLowTemperature() {
        return String.valueOf(minTemp);
    }
}
