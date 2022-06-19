package com.calculatedinc.clima.Model;

import android.graphics.Bitmap;
import com.calculatedinc.clima.Manager.APIManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Weather {
    public double temperature; // Celsius
    public double lowTemp; // Celsius
    public double highTemp; // Celsius
    public String climate;
    public String climateDesc;
    public double feelsLike; // Celsius
    public double pressure; // hPa
    public double humidity; // Humidity, %

    public double visibility; // Meters

    public double windSpeed; // m/s
    public double windDirection; // Degrees

    public String country;
    public String cityName;
    public int sunriseTime; // timestamp
    public int sunsetTime; // timestamp

    public int timezone;
    public long timestamp;
    public String timestampText;
    public String dayOfWeek;

    public String weatherIconString;
    public String weatherIconURL;

    public Weather(JSONObject rootObj) {

        JSONArray weatherObjTemp = (rootObj.has("weather")) ? rootObj.optJSONArray("weather") : null;
        JSONObject weatherObj = null;
        if (weatherObjTemp != null) weatherObj = weatherObjTemp.optJSONObject(0);
        JSONObject mainObj = (rootObj.has("main")) ? rootObj.optJSONObject("main") : null;
        JSONObject windObj = (rootObj.has("wind")) ? rootObj.optJSONObject("wind") : null;
        JSONObject sysObj =  (rootObj.has("sys")) ? rootObj.optJSONObject("sys") : null;

        if (weatherObj != null && mainObj != null && windObj != null && sysObj != null) {
            this.climate = (weatherObj.has("main")) ? weatherObj.optString("main", "") : "";
            this.climateDesc = (weatherObj.has("description")) ? weatherObj.optString("description", "") : "";
            this.temperature = (mainObj.has("temp")) ? mainObj.optDouble("temp", 0.0) : 0.0;
            this.lowTemp = (mainObj.has("temp_min")) ? mainObj.optDouble("temp_min", 0.0) : 0.0;
            this.highTemp = (mainObj.has("temp_max")) ? mainObj.optDouble("temp_max", 0.0) : 0.0;
            this.feelsLike = (mainObj.has("feels_like")) ? mainObj.optDouble("feels_like", 0.0) : 0.0;
            this.pressure = (mainObj.has("pressure")) ? mainObj.optDouble("pressure", 0.0) : 0.0;
            this.humidity = (mainObj.has("humidity")) ? mainObj.optDouble("humidity", 0.0) : 0.0;
            this.visibility = (rootObj.has("visibility")) ? rootObj.optDouble("visibility", 0.0) : 0.0;
            this.windSpeed = (windObj.has("speed")) ? windObj.optDouble("speed", 0.0) : 0.0;
            this.windDirection = (windObj.has("deg")) ? windObj.optDouble("deg", 0.0) : 0.0;
            this.country = (sysObj.has("country")) ? sysObj.optString("country", "") : "";
            this.sunriseTime = (sysObj.has("sunrise")) ? sysObj.optInt("sunrise", 0) : 0;
            this.sunsetTime = (sysObj.has("sunset")) ? sysObj.optInt("sunset", 0) : 0;
            this.cityName = (rootObj.has("name")) ? rootObj.optString("name", "") : "";
            this.timezone = (rootObj.has("timezone")) ? rootObj.optInt("timezone", 0) : 0;
            this.timestamp = (rootObj.has("dt")) ? rootObj.optLong("dt", 0) : 0;
            this.timestampText = (rootObj.has("dt_txt")) ? rootObj.optString("dt_txt", "") : "";
            this.dayOfWeek = "";
            this.weatherIconString = (weatherObj.has("icon")) ? weatherObj.optString("icon") : "";
            this.weatherIconURL = "https://openweathermap.org/img/wn/" + this.weatherIconString + "@2x.png";
        } else {
            this.climate = "";
            this.climateDesc = "";
            this.temperature = 0.0;
            this.lowTemp = 0.0;
            this.highTemp = 0.0;
            this.feelsLike = 0.0;
            this.pressure = 0.0;
            this.humidity = 0.0;
            this.visibility = 0.0;
            this.windSpeed = 0.0;
            this.windDirection = 0.0;
            this.country = "";
            this.sunriseTime = 0;
            this.sunsetTime = 0;
            this.cityName = "";
            this.timezone = 0;
            this.timestamp = 0;
            this.timestampText = "";
            this.dayOfWeek = "";
            this.weatherIconString = "";
            this.weatherIconURL = "";
        }
    }

    public Weather(double temperature, String weatherIconString, String dayOfWeek) {
        this.climate = "";
        this.climateDesc = "";
        this.temperature = temperature;
        this.lowTemp = 0.0;
        this.highTemp = 0.0;
        this.feelsLike = 0.0;
        this.pressure = 0.0;
        this.humidity = 0.0;
        this.visibility = 0.0;
        this.windSpeed = 0.0;
        this.windDirection = 0.0;
        this.country = "";
        this.sunriseTime = 0;
        this.sunsetTime = 0;
        this.cityName = "";
        this.timezone = 0;
        this.timestamp = 0;
        this.timestampText = "";
        this.dayOfWeek = dayOfWeek;
        this.weatherIconString = weatherIconString;
        this.weatherIconURL = "https://openweathermap.org/img/wn/" + this.weatherIconString + "@2x.png";
    }

}
