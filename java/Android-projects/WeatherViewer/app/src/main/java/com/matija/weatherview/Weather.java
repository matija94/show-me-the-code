package com.matija.weatherview;

import android.icu.text.NumberFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by matija on 26.6.17..
 */

public class Weather {

    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconURL;

    public Weather(long timestamp, double minTemp, double maxTemp,
                        double humidity, String description, String iconName) {

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        this.dayOfWeek = convertTimeStampToDay(timestamp);
        this.minTemp = numberFormat.format(minTemp) + "\u00B0C";
        this.maxTemp = numberFormat.format(maxTemp) + "\u00B0C";
        this.humidity = NumberFormat.getPercentInstance().format(humidity/100.0);
        this.description = description;
        this.iconURL = "http://openweathermap.org/img/w" + iconName + ".png";
    }

    private static String convertTimeStampToDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timestamp*1000);
        TimeZone tz = TimeZone.getDefault();

        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat formater = new SimpleDateFormat("EEEE");
        return formater.format(calendar.getTime());
    }
}
