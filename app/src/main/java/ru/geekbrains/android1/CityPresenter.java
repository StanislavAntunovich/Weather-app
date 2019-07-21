package ru.geekbrains.android1;

import ru.geekbrains.android1.data.WeatherDetailsData;

public class CityPresenter {
    private static CityPresenter instance;

    private static final Object lock = new Object();

    private int currentCityIndex;
    private WeatherDetailsData currentData;

    private CityPresenter() {
        currentCityIndex = 0;
    }

    public static CityPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new CityPresenter();
            }
            return instance;
        }
    }

    public WeatherDetailsData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(WeatherDetailsData currentData) {
        this.currentData = currentData;
    }

    public int getCurrentCityIndex() {
        return currentCityIndex;
    }

    public void setCurrentCityIndex(int currentCityIndex) {
        this.currentCityIndex = currentCityIndex;
    }
}


