package com.matija.spendless.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by matija on 29.3.18..
 */

public class ApplicationExecutors {

    private Executor ioThread;

    public ApplicationExecutors() {
        ioThread = Executors.newSingleThreadExecutor();
    }

    public Executor getIoThread() {
        return ioThread;
    }

}
