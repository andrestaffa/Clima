package com.calculatedinc.clima.Manager;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.calculatedinc.clima.Model.Weather;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class Utils {

    public static Utils shared = new Utils();
    public Utils() {}

    public String getFormattedDateFromEpoch(long epochTime, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(epochTime * 1000));
    }

    public double getAverageTemperature(List<Weather> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).temperature;
        }
        return sum / list.size();
    }

    public String getMostFrequentWeatherIcon(List<Weather> list) {
        Map<String, Integer> hp = new HashMap<String, Integer>();
        for(int i = 0; i < list.size(); i++)  {
            String key = list.get(i).weatherIconString;
            if (hp.containsKey(key)) {
                int freq = hp.get(key);
                freq++;
                hp.put(key, freq);
            } else {
                hp.put(key, 1);
            }
        }

        int max_count = 0;
        String res = "";
        for (Map.Entry<String, Integer> val : hp.entrySet()) {
            if (max_count < val.getValue()) {
                res = val.getKey();
                max_count = val.getValue();
            }
        }

        return res;
    }

    public Bitmap rotateImageView(ImageView source, double angle) {
        BitmapDrawable drawable = (BitmapDrawable) source.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate((float)angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public String getWindDirection(double degree) {
        if (degree >= 0.0 && degree <= 10.0) return "N";
        else if (degree > 10.0 && degree <= 30.0) return "N/NE";
        else if (degree > 30.0 && degree <= 50.0) return "NE";
        else if (degree > 50.0 && degree <= 70.0) return "E/NE";
        else if (degree > 70.0 && degree <= 100.0) return "E";
        else if (degree > 100.0 && degree <= 120.0) return "E/SE";
        else if (degree > 120.0 && degree <= 140.0) return "SE";
        else if (degree > 140.0 && degree <= 160.0) return "S/SE";
        else if (degree > 160.0 && degree <= 190.0) return "S";
        else if (degree > 190.0 && degree <= 210.0) return "S/SW";
        else if (degree > 210.0 && degree <= 230.0) return "SW";
        else if (degree > 230.0 && degree <= 250.0) return "W/SW";
        else if (degree > 250.0 && degree <= 280.0) return "W";
        else if (degree > 280.0 && degree <= 300.0) return "W/NW";
        else if (degree > 300.0 && degree <= 320.0) return "NW";
        else if (degree > 320.0 && degree <= 340.0) return "N/NW";
        else if (degree > 340.0 && degree <= 360.0) return "N";
        else return "Wind Direction";
    }

}
