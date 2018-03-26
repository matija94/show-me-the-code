package com.matija.spendless;

import android.app.Application;
import android.content.Context;

/**
 * Created by matija on 26.3.18..
 */

public class SpendLessApplication extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
