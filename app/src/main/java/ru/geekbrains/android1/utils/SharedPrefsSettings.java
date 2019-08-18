package ru.geekbrains.android1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.geekbrains.android1.data.DataSourceImp;
import ru.geekbrains.android1.data.WeatherDataSource;
import ru.geekbrains.android1.presenters.CurrentInfoPresenter;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public final class SharedPrefsSettings {
    private static final String PREFS = "ru.geekbrains.weather_app";
    private static final String HUMIDITY = "HUMIDITY_SETTINGS";
    private static final String PRESSURE = "PRESSURE_SETTINGS";
    private static final String WIND = "WIND_SETTINGS";
    private static final String INDEX = "INDEX_SETTINGS";
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

        editor.putInt(INDEX, index);
        editor.apply();
    }

    public static void saveDataSource(Context context, WeatherDataSource dataSource) {
        String path = getPath(context);
        File file;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {
            file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            fileOutputStream = new FileOutputStream(file, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(dataSource);

            fileOutputStream.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static WeatherDataSource readDataSource(Context context) {
        String path = getPath(context);
        WeatherDataSource dataSource = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            dataSource = (WeatherDataSource) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return dataSource != null ? dataSource : new DataSourceImp();
    }

    public static void readSettings(Context context) {
        SettingsPresenter settingsPresenter = SettingsPresenter.getInstance();
        CurrentInfoPresenter infoPresenter = CurrentInfoPresenter.getInstance();

        SharedPreferences preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean humidity = preferences.getBoolean(HUMIDITY, true);
        boolean pressure = preferences.getBoolean(PRESSURE, true);
        boolean wind = preferences.getBoolean(WIND, true);
        int index = preferences.getInt(INDEX, 0);

        settingsPresenter.setHumidityChecked(humidity);
        settingsPresenter.setPressureChecked(pressure);
        settingsPresenter.setWindChecked(wind);
        infoPresenter.setCurrentIndex(index);

    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit();
    }

    private static String getPath(Context context) {
        return context.getFilesDir() + "/" + PATH;
    }

    private SharedPrefsSettings() {
    }

}
