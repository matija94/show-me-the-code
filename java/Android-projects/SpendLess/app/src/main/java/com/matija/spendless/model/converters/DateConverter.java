package com.matija.spendless.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.matija.spendless.utils.Constants;
import com.matija.spendless.utils.DateParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matija on 9.3.18..
 */

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(String value) {
        Date date = null;
        try {
            date = DateParser.backendParser().parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return date;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : DateParser.backendParser().format(date);
    }
}
