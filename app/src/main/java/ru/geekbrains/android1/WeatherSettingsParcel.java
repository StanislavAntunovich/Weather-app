package ru.geekbrains.android1;

import java.io.Serializable;

public class WeatherSettingsParcel implements Serializable {

    private String city;
    private boolean humidityChecked;
    private boolean pressureChecked;
    private boolean windChecked;


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
