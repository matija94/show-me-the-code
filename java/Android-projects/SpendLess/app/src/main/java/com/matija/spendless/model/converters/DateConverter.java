package com.matija.spendless.model.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by matija on 9.3.18..
 */

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
