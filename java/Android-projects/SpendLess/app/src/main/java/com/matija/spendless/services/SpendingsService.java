package com.matija.spendless.services;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.matija.spendless.preferences.SpendLessPreferences;

/**
 * Created by matija on 2.4.18..
 */

public class SpendingsService extends IntentService {

    //private static final long SAVINGS_UPDATE_CHECK_INTERVAL = 86400000; // 24 hours
    //private static final long SAVINGS_UPDATE_CHECK_INTERVAL = 30000; // 30 sec
    private static final int UPDATE_SPENDINGS_JOB = 1;

    public SpendingsService() {
        super("SavingsService");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SpendingsService.class);
    }

    public static void setServiceAlarm(Context context) {
        Log.d(SpendingsService.class.getCanonicalName(),"setServiceAlarm() called");
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, newIntent(context), 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,  AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    public static boolean isAlarmOn(Context context) {
        PendingIntent pendingIntent = PendingIntent.getService(context,0,newIntent(context), PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(getClass().getCanonicalName(), "onHandleIntent() called");
        Integer remainingDailySpendings = SpendLessPreferences.getRemainingDailySpendings(this);
        remainingDailySpendings+=SpendLessPreferences.getDailySpendings(this);
        SpendLessPreferences.setRemainingDailySpending(this, remainingDailySpendings);
        Log.d(getClass().getCanonicalName(), "Set new remaining daily spends: " + remainingDailySpendings);
    }

/*
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static JobInfo.Builder getServiceJobSchedulerBuilder(Context context) {
        ComponentName serviceComponent = new ComponentName(context, SpendingsService.class);
        JobInfo.Builder builder = new JobInfo.Builder(UPDATE_SPENDINGS_JOB, serviceComponent);
        builder.setPeriodic(SAVINGS_UPDATE_CHECK_INTERVAL);
        return builder;
    }*/

/*    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isServiceScheduled(Context context) {
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        return jobScheduler.getPendingJob(UPDATE_SPENDINGS_JOB) != null;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleService(Context context) {
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        j*//*obScheduler.schedule(getServiceJobSchedulerBuilder(context).build());
    }*/

}
