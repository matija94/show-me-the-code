package com.matija.spendless.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by matija on 29.3.18..
 */

public class ApplicationExecutors {

    private Executor ioThread;

    private static ApplicationExecutors executors=null;

    public static ApplicationExecutors getInstance() {
        if (executors == null) {
            executors = new ApplicationExecutors();
        }
        return executors;
    }

    private ApplicationExecutors() {
        ioThread = Executors.newSingleThreadExecutor();
    }

    public Executor getIoThread() {
        return ioThread;
    }

}
