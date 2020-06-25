package com.example.weatherapp.Views.ViewModels;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Repositories.WeatherRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    WeatherRepository weatherRepositry;
    MutableLiveData<WeatherModel> currentWeatherLiveData;
    private double longitude;
    private double latitude ;

    public WeatherViewModel() {
        weatherRepositry = new WeatherRepository();
        currentWeatherLiveData = new MutableLiveData<>();

    }

    public void getCurrentWeather() {
        weatherRepositry.getCurrentWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    currentWeatherLiveData.setValue(response);
                }, error -> showError(error.toString()));
    }


    void showError(String errorMsg) {
        Log.d("Error Msg", errorMsg);
    }

    public void getLongLat(double longt, double lat) {
        longitude = longt;
        latitude = lat;
    }

    public LiveData<WeatherModel> getWeatherLiveData() {
        return currentWeatherLiveData;
    }

}
