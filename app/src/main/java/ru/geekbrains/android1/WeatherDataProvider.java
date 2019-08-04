package ru.geekbrains.android1;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.data.WeatherDetailsDataImpl;
import ru.geekbrains.android1.model.MainWeatherRequest;

public class WeatherDataProvider {
    private static String KEY = "5bc7abdc816d113c0d373166dc0c91c0";
    private static String REQUEST_CITY_PATTERN = "https://api.openweathermap.org/data/2.5/weather?q=%s,RU&appid=%s";


    public static WeatherDetailsData getData(String city) {

        try {
            URL url = new URL(String.format(REQUEST_CITY_PATTERN, city, KEY));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = in.lines().collect(Collectors.joining("\n"));
            Gson gson = new Gson();
            MainWeatherRequest request = gson.fromJson(result, MainWeatherRequest.class);
            if (request.getCod() == 200) {
                return new WeatherDetailsDataImpl(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
