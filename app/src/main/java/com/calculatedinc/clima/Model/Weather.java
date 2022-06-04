package com.calculatedinc.clima.Model;

import android.graphics.Bitmap;

import com.calculatedinc.clima.Manager.APIManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Weather {
    public double temperature; // Celsius
    public String climate;
    public String climateDesc;
    public double feelsLike; // Celsius
    public double pressure; // hPa
    public double humidity; // Humidity, %

    public double windSpeed; // m/s

    public String country;
    public String cityName;
    public int sunriseTime; // timestamp
    public int sunsetTime; // timestamp

    public Bitmap weatherIconBitmap;

    public Weather(JSONObject rootObj) {
        try {
            JSONObject weatherObj = rootObj.getJSONArray("weather").getJSONObject(0);
            JSONObject mainObj = rootObj.getJSONObject("main");
            JSONObject windObj = rootObj.getJSONObject("wind");
            JSONObject sysObj = rootObj.getJSONObject("sys");
            this.climate = weatherObj.getString("main");
            this.climateDesc = weatherObj.getString("description");
            this.temperature = mainObj.getDouble("temp");
            this.feelsLike = mainObj.getDouble("feels_like");
            this.pressure = mainObj.getDouble("pressure");
            this.humidity = mainObj.getDouble("humidity");
            this.windSpeed = windObj.getDouble("speed");
            this.country = sysObj.getString("country");
            this.sunriseTime = sysObj.getInt("sunrise");
            this.sunsetTime = sysObj.getInt("sunset");
            this.cityName = rootObj.getString("name");

            String iconLabel = weatherObj.getString("icon");
            String iconURL = "https://openweathermap.org/img/wn/" + iconLabel + "@2x.png";
            Bitmap bitmap;
            try {
                bitmap = new APIManager.DownloadImage().execute(iconURL).get();
                this.weatherIconBitmap = bitmap;
            } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); this.weatherIconBitmap = null; }

        } catch (JSONException e) { e.printStackTrace(); }
    }

}
