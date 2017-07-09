package com.bignerdranch.android.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by matija on 9.5.17..
 */

public class NotificationReciever extends BroadcastReceiver {

    private final static String TAG = "NotificationReciever";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "recieved result: " + getResultCode());
        if (getResultCode() != Activity.RESULT_OK)  {
            // notification was canceled
            return;
        }

        int requestCode = intent.getIntExtra(PollService.REQUEST_CODE, 0);
        Notification notification = (Notification) intent.getParcelableExtra(PollService.NOTIFICATIOn);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(requestCode, notification);
    }
}
