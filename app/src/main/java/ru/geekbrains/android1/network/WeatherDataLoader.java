package ru.geekbrains.android1.network;

import retrofit2.Callback;
import ru.geekbrains.android1.rest.WeatherRepo;
import ru.geekbrains.android1.rest.entities.ForecastRequest;
import ru.geekbrains.android1.rest.entities.WeatherApi;
import ru.geekbrains.android1.rest.entities.WeatherRequest;

public final class WeatherDataLoader {

    public static void loadCurrentWeather(String city,
                                          String lang,
                                          String units,
                                          Callback<WeatherRequest> callback) {

        WeatherApi api = WeatherRepo.getInstance().getApi();
        api.getCurrentWeather(WeatherRepo.KEY, lang, city, units)
                .enqueue(callback);
    }

    public static void loadForecast(String city,
                                    String lang,
                                    String units,
                                    int days,
                                    Callback<ForecastRequest> callback) {

        WeatherApi api = WeatherRepo.getInstance().getApi();
        api.getForecast(WeatherRepo.KEY, lang, city, units, days)
                .enqueue(callback);
    }
}
