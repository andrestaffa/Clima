package com.calculatedinc.clima.Manager;

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

}
