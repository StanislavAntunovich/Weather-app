package ru.geekbrains.android1.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.geekbrains.android1.MainActivity;
import ru.geekbrains.android1.data.ForecastData;
import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.model.CurrentWeatherDataImpl;
import ru.geekbrains.android1.model.ForecastRequest;
import ru.geekbrains.android1.model.WeatherRequest;

public class WeatherReceiveService extends IntentService {
    public static final String BROADCAST_ACTION = "ru.geekbrains.android2.weatherapp";
    private static final String KEY = "b0e94b3031294a12b5958fd1401b2ce4";
    private static final String CURRENT_WEATHER_REQUEST = "https://api.weatherbit.io/v2.0/current?key=%s&lang=%s&city=%s&units=M";
    private static final String FORECAST_REQUEST = "https://api.weatherbit.io/v2.0/forecast/daily?key=%s&lang=%s&city=%s&units=M&days=7";

    public WeatherReceiveService() {
        super("Weather_background_receiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String city = intent.getStringExtra(MainActivity.CITY);
            String lang = intent.getStringExtra(MainActivity.LANG);
            String action = intent.getStringExtra(MainActivity.ACTION);

            WeatherDetailsData data = getCurrentWeather(city, lang);
            ForecastData[] forecast = getForecast(city, lang);
            if (data != null) {
                data.setForecast(forecast);
                makeResult(MainActivity.OK, action, data);
            } else {
                makeResult(MainActivity.ERROR, null, null);
            }
        }
    }

    private CurrentWeatherDataImpl getCurrentWeather(String city, String lang) {
        Gson gson = new Gson();
        CurrentWeatherDataImpl data = null;
        String result = sendRequest(CURRENT_WEATHER_REQUEST, city, lang);
        WeatherRequest request = gson.fromJson(result, WeatherRequest.class);
        if (request != null && !request.isEmpty()) {
            data = request.getData()[0];
            data.setCity(city);
        }
        return data;

    }


    private ForecastData[] getForecast(String city, String lang) {
        Gson gson = new Gson();
        String result = sendRequest(FORECAST_REQUEST, city, lang);
        ForecastData[] forecast = null;
        ForecastRequest request = gson.fromJson(result, ForecastRequest.class);
        if (request != null && !request.isEmpty()) {
            forecast = request.getData();
        }
        return forecast;
    }

    private String sendRequest(String uri, String city, String lang) {
        HttpURLConnection connection = null;
        String result = null;
        try {
            URL url = new URL(String.format(uri, KEY, lang, city));
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            result = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return result;

    }

    private void makeResult(String resultCode, String action, WeatherDetailsData data) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(MainActivity.RESULT, resultCode);
        if (data != null) {
            Bundle args = new Bundle();
            args.putSerializable(MainActivity.WEATHER_DATA, data);
            intent.putExtras(args);
        }
        if (action != null) {
            intent.putExtra(MainActivity.ACTION, action);
        }
        sendBroadcast(intent);
    }

}
