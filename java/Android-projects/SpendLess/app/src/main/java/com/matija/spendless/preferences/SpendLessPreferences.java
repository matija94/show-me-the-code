package com.matija.spendless.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by matija on 23.3.18..
 */

public class SpendLessPreferences {

    private static final String DAILY_SPENDINGS = "daily_spendings"; // daily settings set by user
    private static final String REMAINING_DAILY_SPENDING = "remaining_daily_spendings"; // remaining daily savings
    private static final String LAST_TRANSACTION_TIMESTAMP = "last_transaction_timestamp";

    public static Integer getRemainingDailySpendings(Context context) {
        int remainingSpendings = (Integer) getPreference(context, REMAINING_DAILY_SPENDING, Integer.class);
        return remainingSpendings != 0 ? remainingSpendings : getDailySpendings(context);
    }

    public static void setRemainingDailySpending(Context context, int spendings) {
        setPreference(context,spendings, REMAINING_DAILY_SPENDING);
    }

    public static Integer getDailySpendings(Context context) {
        return (Integer) getPreference(context, DAILY_SPENDINGS, Integer.class);
    }

    public static void setDailySpendings(Context context, int spendings) {
        setPreference(context, spendings, DAILY_SPENDINGS);
    }

    public static long getLastTransactionTimestamp(Context context) {
        return (Long) getPreference(context, LAST_TRANSACTION_TIMESTAMP, Long.class);
    }

    public static void setLastTransactionTimestamp(Context context, long timestamp) {
        setPreference(context, timestamp, LAST_TRANSACTION_TIMESTAMP);
    }

    private static void setPreference(Context context, Object preference, String preferenceKey) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        if (preference instanceof Integer) edit.putInt(preferenceKey, (Integer) preference);
        else if (preference instanceof Long) edit.putLong(preferenceKey, (Long) preference);
        edit.apply();
    }

  private static Object getPreference(Context context, String preferenceKey, Class clazz) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(clazz == Integer.class) return defaultSharedPreferences.getInt(preferenceKey, 0);
        if (clazz == Long.class) return defaultSharedPreferences.getLong(preferenceKey, 0);
        return null;
  }


}
