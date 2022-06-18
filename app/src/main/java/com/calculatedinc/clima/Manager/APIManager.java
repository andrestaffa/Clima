package com.calculatedinc.clima.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;

import com.calculatedinc.clima.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class APIManager {

    // Current Weather URL: https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&units=metric&appid={API key}
    // 5 Day Weather Forecast:  https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
    // Weather Image Icon URL: https://openweathermap.org/img/wn/{icon}@2x.png

    public static APIManager shared = new APIManager();
    public APIManager() {}

    public void getWeatherData(double latitude, double longitude, Consumer<Weather> completion) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=0b048ee1be9687d2f2c7c716c34033c5";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    final JSONObject rootObj = new JSONObject(response.body().string());
                    Weather weather = new Weather(rootObj);
                    new Handler(Looper.getMainLooper()).post(() -> { completion.accept(weather); });
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }

    public void getForecastData(double latitude, double longitude, Consumer<ArrayList<Weather>> completion) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=0b048ee1be9687d2f2c7c716c34033c5";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace();}
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<Weather> weatherList = new ArrayList<Weather>();
                        final JSONObject rootObj = new JSONObject(response.body().string());
                        JSONArray forecast = rootObj.getJSONArray("list");
                        for (int i = 0; i < forecast.length(); i++) weatherList.add(new Weather(forecast.getJSONObject(i)));
                        new Handler(Looper.getMainLooper()).post(() -> { completion.accept(weatherList); });
                    } catch (JSONException e) { e.printStackTrace(); }
                }
            }
        });
    }

    public static class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap bitmapImage = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmapImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmapImage;
        }
    }

}
