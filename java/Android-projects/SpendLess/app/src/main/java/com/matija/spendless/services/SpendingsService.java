package com.matija.spendless.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.matija.spendless.preferences.SpendLessPreferences;

/**
 * Created by matija on 2.4.18..
 */

public class SpendingsService extends IntentService {

    private static final long SAVINGS_UPDATE_CHECK_INTERVAL = 1000 * (60^3) * 24;

    public SpendingsService() {
        super("SavingsService");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SpendingsService.class);
    }

    public static void setServiceAlarm(Context context) {
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, newIntent(context), 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,System.currentTimeMillis(), SAVINGS_UPDATE_CHECK_INTERVAL, pendingIntent);
    }

    public static boolean isAlarmOn(Context context) {
        PendingIntent pendingIntent = PendingIntent.getService(context,0,newIntent(context), PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Integer remainingDailySpendings = SpendLessPreferences.getRemainingDailySpendings(this);
        remainingDailySpendings+=SpendLessPreferences.getDailySpendings(this);
        SpendLessPreferences.setRemainingDailySpending(this, remainingDailySpendings);
    }
}
