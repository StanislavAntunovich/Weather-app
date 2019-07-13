package ru.geekbrains.android1;

public class WeatherSettingsPresenter {
    private static WeatherSettingsPresenter instance;

    private static final Object lock = new Object();

    private String city;
    private boolean humidityChecked;
    private boolean pressureChecked;
    private boolean windChecked;

    private WeatherSettingsPresenter() {
        this.humidityChecked = true;
        this.pressureChecked = true;
        this.windChecked = true;
    }

    public static WeatherSettingsPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new WeatherSettingsPresenter();
            }
        }
        return instance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isHumidityChecked() {
        return humidityChecked;
    }

    public void setHumidityChecked(boolean humidityChecked) {
        this.humidityChecked = humidityChecked;
    }

    public boolean isPressureChecked() {
        return pressureChecked;
    }

    public void setPressureChecked(boolean pressureChecked) {
        this.pressureChecked = pressureChecked;
    }

    public boolean isWindChecked() {
        return windChecked;
    }

    public void setWindChecked(boolean windChecked) {
        this.windChecked = windChecked;
    }
}
