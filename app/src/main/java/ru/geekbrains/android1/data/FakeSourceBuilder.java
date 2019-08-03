package ru.geekbrains.android1.data;

import android.content.res.Resources;

import ru.geekbrains.android1.R;

public class FakeSourceBuilder {
    private Resources resources;
    private WeatherDataSource dataSource;

    public FakeSourceBuilder setResources(Resources resources) {
        this.resources = resources;
        dataSource = new FakeDataSourceImp(resources);
        return this;
    }

    public WeatherDataSource build() {
        String[] cities = resources.getStringArray(R.array.cities);

        for (String city : cities) {
            dataSource.addData(city);
        }
        return dataSource;
    }
}
