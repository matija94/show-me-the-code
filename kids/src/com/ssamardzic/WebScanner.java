package com.ssamardzic;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class WebScanner {

    private ThreadPoolExecutor tpe;

    private LinkedBlockingQueue<ScanningJob> jobQueue;


    public void enqueue(ScanningJob scanningJob) {
        //TODO IMPL
    }
}
