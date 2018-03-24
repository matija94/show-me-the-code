package com.matija.spendless.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by matija on 23.3.18..
 */

public class SpendLessPreferences {

    private static final String DAILY_SAVINGS = "daily_savings";

    public static Integer getDailySavings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(DAILY_SAVINGS, 0);
    }

    public static void setDailySavings(Context context, int dailySavings) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(DAILY_SAVINGS, dailySavings)
                .apply();
    }
}
