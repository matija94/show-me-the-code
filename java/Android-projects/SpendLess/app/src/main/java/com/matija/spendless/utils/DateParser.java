package com.matija.spendless.utils;

import java.text.SimpleDateFormat;

/**
 * Created by matija on 27.3.18..
 */

public class DateParser {

    private static SimpleDateFormat BACKEND_DATE_PARSER;
    private static SimpleDateFormat FRONTEND_DATE_PARSER;

    static {
        BACKEND_DATE_PARSER = new SimpleDateFormat(Constants.BACKEND_TIME_STAMP_FORMAT);
        FRONTEND_DATE_PARSER = new SimpleDateFormat(Constants.FRONTEND_TIME_STAMP_FORMAT);
    }

    public static SimpleDateFormat backendParser() {
        return BACKEND_DATE_PARSER;
    }

    public static SimpleDateFormat frontendParser() {
        return FRONTEND_DATE_PARSER;
    }
}
