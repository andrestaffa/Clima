package com.calculatedinc.clima.Manager;

import android.util.Log;
import androidx.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class APIManager {

    // Current Weather URL: https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&units=metric&appid={API key}
    // 5 Day Weather Forecast: api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
    // Weather Image Icon URL: https://openweathermap.org/img/wn/{icon}@2x.png

    public static APIManager shared = new APIManager();
    public APIManager() {}

    public void getWeatherData(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&&appid=0b048ee1be9687d2f2c7c716c34033c5";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    final JSONObject mainObject = new JSONObject(response.body().string());
                    Log.wtf("WEATHER DATA", mainObject.toString());
                } catch (JSONException e) { e.printStackTrace(); }
            }
        });
    }

}
