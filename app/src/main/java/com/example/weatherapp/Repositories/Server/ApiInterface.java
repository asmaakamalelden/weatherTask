package com.example.weatherapp.Repositories.Server;

import com.example.weatherapp.Models.WeatherModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Asmaa on 6/5/2020.
 */

public interface ApiInterface {

    @GET("/data/2.5/onecall")
    Observable<WeatherModel> getCurrentWeather(@Query("lat")double lat, @Query("lon")double lon);

}
