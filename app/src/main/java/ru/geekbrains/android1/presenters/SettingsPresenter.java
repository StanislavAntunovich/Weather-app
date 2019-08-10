package ru.geekbrains.android1.presenters;

import java.util.Locale;

public class SettingsPresenter {
    private static SettingsPresenter instance;

    private static final Object lock = new Object();

    private int tempUnitIndex;

    private Locale currentLocale;

    private boolean isHumidityChecked;
    private boolean isWindChecked;
    private boolean isPressureChecked;

    public static SettingsPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new SettingsPresenter();
            }
            return instance;
        }
    }

    private SettingsPresenter() {
        this.isHumidityChecked = true;
        this.isPressureChecked = true;
        this.isWindChecked = true;
        this.tempUnitIndex = 0;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    public boolean isHumidityChecked() {
        return isHumidityChecked;
    }

    public void setHumidityChecked(boolean humidityChecked) {
        isHumidityChecked = humidityChecked;
    }

    public boolean isWindChecked() {
        return isWindChecked;
    }

    public void setWindChecked(boolean windChecked) {
        isWindChecked = windChecked;
    }

    public boolean isPressureChecked() {
        return isPressureChecked;
    }

    public void setPressureChecked(boolean pressureChecked) {
        isPressureChecked = pressureChecked;
    }

    public int getTempUnitIndex() {
        return tempUnitIndex;
    }

    public void setTempUnitIndex(int tempUnitIndex) {
        this.tempUnitIndex = tempUnitIndex;
    }
}
