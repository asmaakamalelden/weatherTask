
package com.example.weatherapp.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hourly {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("temp")
    @Expose
    private Float temp;
    @SerializedName("feels_like")
    @Expose
    private Float feelsLike;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("dew_point")
    @Expose
    private Float dewPoint;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("wind_speed")
    @Expose
    private Float windSpeed;
    @SerializedName("wind_deg")
    @Expose
    private Integer windDeg;
    @SerializedName("weather")
    @Expose
    private List<Weather_> weather = null;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public List<Weather_> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather_> weather) {
        this.weather = weather;
    }

}
