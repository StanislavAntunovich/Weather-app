package ru.geekbrains.android1.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.android1.rest.entities.WeatherApi;

public class WeatherRepo {
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    public static final String CURRENT_WEATHER = "current";
    public static final String FORECAST = "forecast/daily";
    public static final String KEY = "e32f400fae38481c879fbad93957e183";

    private static WeatherRepo instance;

    private WeatherApi API;

    private WeatherRepo() {
        API = createAdapter();
    }

    public WeatherApi getApi() {
        return this.API;
    }

    private WeatherApi createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(WeatherApi.class);
    }

    public static WeatherRepo getInstance() {
        if (instance == null) {
            instance = new WeatherRepo();
        }
        return instance;
    }

}
