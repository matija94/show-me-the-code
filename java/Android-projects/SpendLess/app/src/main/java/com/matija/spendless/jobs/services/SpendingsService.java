package com.matija.spendless.jobs.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.matija.spendless.R;
import com.matija.spendless.preferences.SpendLessPreferences;
import com.matija.spendless.ui.MainActivity;

/**
 * Created by matija on 2.4.18..
 */

public class SpendingsService extends IntentService {

    //private static final long SAVINGS_UPDATE_CHECK_INTERVAL = 86400000; // 24 hours
    //private static final long SAVINGS_UPDATE_CHECK_INTERVAL = 30000; // 30 sec

    public static final String INSERT_NEW_TRANSACTION = "INSERT_NEW_TRANSACTION";
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
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()
                + AlarmManager.INTERVAL_DAY,  AlarmManager.INTERVAL_DAY, pendingIntent);
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

        long lastTransactionTimestamp = SpendLessPreferences.getLastTransactionTimestamp(this);
        if (System.currentTimeMillis() - lastTransactionTimestamp >= AlarmManager.INTERVAL_DAY) {
            sendNotification();
        }
    }

    private void sendNotification() {
        Log.d("SpendingsService", "Sending remainder notification");
        Resources resources = getResources();

        // declare extra insert_new_transaction so activity knows to bring up dialog upon creation
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(INSERT_NEW_TRANSACTION, true);

        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(resources.getString(R.string.transactions_remainder))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.transactions_remainder))
                .setContentText(resources.getString(R.string.transactions_reminder_desc))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, notification);
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
