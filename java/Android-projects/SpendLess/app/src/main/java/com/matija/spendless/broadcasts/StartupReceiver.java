package com.matija.spendless.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.matija.spendless.services.SpendingsService;


/**
 * Created by matija on 2.4.18..
 */

public class StartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SpendingsService.isAlaramOn(context)) {
            SpendingsService.setServiceAlarm(context);
        }
    }
}
