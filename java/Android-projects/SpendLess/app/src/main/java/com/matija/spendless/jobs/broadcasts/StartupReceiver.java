package com.matija.spendless.jobs.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matija.spendless.jobs.services.SpendingsService;


/**
 * Created by matija on 2.4.18..
 */

public class StartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getCanonicalName(), "onReceive() called");
        if (!SpendingsService.isAlarmOn(context)) {
            SpendingsService.setServiceAlarm(context);
        }
    }
}
