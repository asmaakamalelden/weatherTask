package com.example.weatherapp.Repositories;

import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Repositories.Server.ApiClient;
import com.example.weatherapp.Repositories.Server.ApiInterface;
import io.reactivex.Observable;

public class WeatherRepository {
    private ApiInterface apiInterface = ApiClient.getInstance().getApi();

    public Observable<WeatherModel> getCurrentWeather(double lat, double lon) {
        return apiInterface.getCurrentWeather(lat, lon);
    }

}
