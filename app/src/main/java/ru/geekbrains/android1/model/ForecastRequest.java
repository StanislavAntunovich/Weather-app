package ru.geekbrains.android1.model;

public class ForecastRequest {
    private ForecastDataImpl[] data;

    public ForecastDataImpl[] getData() {
        return data;
    }

    public boolean isEmpty() {
        return data.length == 0;
    }
}
