package ru.geekbrains.android1.rest.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.android1.rest.WeatherRepo;

public interface WeatherApi {
    @GET(WeatherRepo.CURRENT_WEATHER)
    Call<WeatherRequest> getCurrentWeather(@Query("key") String key,
                                           @Query("lang") String lang,
                                           @Query("city") String city,
                                           @Query("units") String units);

    @GET(WeatherRepo.FORECAST)
    Call<ForecastRequest> getForecast(@Query("key") String key,
                                      @Query("lang") String lang,
                                      @Query("city") String city,
                                      @Query("units") String units,
                                      @Query("days") int days);

    @GET(WeatherRepo.CURRENT_WEATHER)
    Call<WeatherRequest> getCurrentWeather(@Query("key") String key,
                                           @Query("lang") String lang,
                                           @Query("lat") double lat,
                                           @Query("lon") double lon,
                                           @Query("units") String units);
}
