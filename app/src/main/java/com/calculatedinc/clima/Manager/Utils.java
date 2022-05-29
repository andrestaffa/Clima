package com.calculatedinc.clima.Manager;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Utils {

    public static Utils shared = new Utils();
    public Utils() {}

    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM h:mm a", Locale.getDefault());
        return df.format(c);
    }




}
