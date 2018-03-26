package com.matija.spendless;

import android.content.Context;

/**
 * Created by matija on 26.3.18..
 */

public class Application extends android.app.Application {

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
