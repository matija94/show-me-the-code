package com.ssamardzic;

import java.util.concurrent.LinkedBlockingQueue;

public class JobDispatcher extends Thread {

    private LinkedBlockingQueue<ScanningJob> jobQueue;

    private FileScanner fileScanner;

    private WebScanner webScanner;

    @Override
    public synchronized void start() {
        super.start();

        ScanningJob scanningJob = jobQueue.poll();
        ScanningType type = scanningJob.getType();

        switch (type){
            case FILE :
                fileScanner.enqueue(scanningJob);
                        break;
            case WEB :
                webScanner.enqueue(scanningJob);
                        break;
             default:
                 // NIJE NI WEB NI FILE
        }
    }
}
