package ru.geekbrains.android1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public final class SharedPrefsSettings {
    private static final String PREFS = "ru.geekbrains.weather_app";
    private static final String HUMIDITY = "HUMIDITY_SETTINGS";
    private static final String PRESSURE = "PRESSURE_SETTINGS";
    private static final String WIND = "WIND_SETTINGS";
    private static final String CURRENT_INDEX = "CURRENT_INDEX_SETTINGS";
    private static final String UNITS_INDEX = "UNITS_INDEX_SETTINGS";
    private static final String PATH = "weather_datasource";


    public static void saveHumidity(Context context, boolean isChecked) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putBoolean(HUMIDITY, isChecked);
        editor.apply();
    }

    public static void savePressure(Context context, boolean isChecked) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putBoolean(PRESSURE, isChecked);
        editor.apply();
    }

    public static void saveWind(Context context, boolean isChecked) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putBoolean(WIND, isChecked);
        editor.apply();
    }

    public static void saveCurrentIndex(Context context, int index) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putInt(CURRENT_INDEX, index);
        editor.apply();
    }

    public static void saveUnitsIndex(Context context, int index) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putInt(UNITS_INDEX, index);
        editor.apply();
    }


    public static void readSettings(Context context) {
        SettingsPresenter settingsPresenter = SettingsPresenter.getInstance();
        CurrentInfoPresenter infoPresenter = CurrentInfoPresenter.getInstance();

        SharedPreferences preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean humidity = preferences.getBoolean(HUMIDITY, true);
        boolean pressure = preferences.getBoolean(PRESSURE, true);
        boolean wind = preferences.getBoolean(WIND, true);
        int index = preferences.getInt(CURRENT_INDEX, 0);
        int unitsIndex = preferences.getInt(UNITS_INDEX, 0);

        settingsPresenter.setHumidityChecked(humidity);
        settingsPresenter.setPressureChecked(pressure);
        settingsPresenter.setWindChecked(wind);
        settingsPresenter.setTempUnitIndex(unitsIndex);
        infoPresenter.setCurrentIndex(index);

    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit();
    }

    private SharedPrefsSettings() {
    }

}
