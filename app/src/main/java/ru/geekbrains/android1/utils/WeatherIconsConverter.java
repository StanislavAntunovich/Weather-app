package ru.geekbrains.android1.utils;

import ru.geekbrains.android1.R;

public class WeatherIconsConverter {
    public static int getIconID(int weatherCode) {
        if (weatherCode >= 200 && weatherCode < 300) {
            return R.drawable.ico_storm;
        }
        if (weatherCode >= 300 && weatherCode < 400) {
            return R.drawable.ico_light_rain;
        }
        if (weatherCode >= 500 && weatherCode < 600) {
            return R.drawable.ico_rain;
        }
        if (weatherCode >= 600 && weatherCode < 700) {
            return R.drawable.ico_snow;
        }
        if (weatherCode >= 700 && weatherCode < 800) {
            return R.drawable.ico_fog;
        }
        if (weatherCode == 800) {
            return R.drawable.ico_clear;
        }
        if (weatherCode == 801 || weatherCode == 802) {
            return R.drawable.ico_light_clouds;
        }
        return R.drawable.ico_clouds;
    }
}
