package ru.geekbrains.android1.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.geekbrains.android1.data.WeatherDetailsData;
import ru.geekbrains.android1.rest.entities.CurrentWeatherDataImpl;

public class CityWeatherTable {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    private static final String TABLE_NAME = "cities";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_PRESSURE = "pressure";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_WIND = "wind";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_CONDITION = "condition";
    private static final String COLUMN_WEATHER_CODE = "weather_code";
    private static final String COLUMN_LAST_UPDATED = "last_updated";
    private static final String COLUMN_REMOVED = "removed";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CITY + " TEXT NOT NULL," +
                COLUMN_TEMPERATURE + " REAL," +
                COLUMN_HUMIDITY + " INTEGER," +
                COLUMN_PRESSURE + " REAL," +
                COLUMN_WIND + " REAL," +
                COLUMN_CONDITION + " TEXT," +
                COLUMN_WEATHER_CODE + " INTEGER," +
                COLUMN_LAST_UPDATED + " NUMERIC," +
                COLUMN_REMOVED + " NUMERIC" +
                " );");
    }

    static void onUpgrade(SQLiteDatabase database) {
        //will be in case of changing
    }

    public static void addCity(SQLiteDatabase database, WeatherDetailsData data) {
        ContentValues values = getContentValues(data);

        database.insert(TABLE_NAME, null, values);
    }

    private static ContentValues getContentValues(WeatherDetailsData data) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, data.getCity());
        values.put(COLUMN_TEMPERATURE, data.getCurrentTemperature());
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        values.put(COLUMN_PRESSURE, data.getPressure());
        values.put(COLUMN_WIND, data.getWind());
        values.put(COLUMN_CONDITION, data.getWeatherCondition());
        values.put(COLUMN_WEATHER_CODE, data.getWeatherCode());
        values.put(COLUMN_LAST_UPDATED, data.getLastUpdated().getTime());
        values.put(COLUMN_REMOVED, FALSE);
        return values;
    }

    public static void removeCity(SQLiteDatabase database, String city) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REMOVED, TRUE);
        database.update(TABLE_NAME, values, COLUMN_CITY + "=?", new String[] {city});
    }

    public static List<WeatherDetailsData> getUsersCities(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null,
                COLUMN_REMOVED + "=" + FALSE,
                null, null, null,
                COLUMN_ID + " ASC");
        return getCitiesFromCursor(cursor);
    }

    public static void updateCity(SQLiteDatabase database, WeatherDetailsData data) {
        ContentValues values = getContentValues(data);
        String cityName = data.getCity();

        database.update(TABLE_NAME, values,
                COLUMN_CITY + "=?", new String[] {cityName});

    }

    private static List<WeatherDetailsData> getCitiesFromCursor(Cursor cursor) {
        List<WeatherDetailsData> result = null;
        if (cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            do {
                WeatherDetailsData data = getWeatherDetailsFromCursor(cursor);
                result.add(data);
            } while (cursor.moveToNext());

        }

        try {
            cursor.close();
        } catch (Exception ignored) {
        }

        return result == null ? new ArrayList<>(0) : result;
    }

    private static WeatherDetailsData getWeatherDetailsFromCursor(Cursor cursor) {
        int cityIndex = cursor.getColumnIndex(COLUMN_CITY);
        int tempIndex = cursor.getColumnIndex(COLUMN_TEMPERATURE);
        int pressureIndex = cursor.getColumnIndex(COLUMN_PRESSURE);
        int humIndex = cursor.getColumnIndex(COLUMN_HUMIDITY);
        int windIndex = cursor.getColumnIndex(COLUMN_WIND);
        int conditionIndex = cursor.getColumnIndex(COLUMN_CONDITION);
        int codeIndex = cursor.getColumnIndex(COLUMN_WEATHER_CODE);
        int lastUpdatedIndex = cursor.getColumnIndex(COLUMN_LAST_UPDATED);

        WeatherDetailsData data = new CurrentWeatherDataImpl();
        data.setCity(cursor.getString(cityIndex));
        data.setCurrentTemperature(cursor.getInt(tempIndex));
        data.setPressure(cursor.getFloat(pressureIndex));
        data.setHumidity(cursor.getInt(humIndex));
        data.setWind(cursor.getFloat(windIndex));
        data.setWeatherCondition(cursor.getString(conditionIndex));
        data.setWeatherCode(cursor.getInt(codeIndex));
        Date updated = new Date(cursor.getLong(lastUpdatedIndex));
        data.setLastUpdated(updated);

        return data;
    }


}
