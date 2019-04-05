package com.ssamardzic;

import com.ssamardzic.errors.CannotReadFile;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class DirectoryCrawler extends Thread {

    private LinkedBlockingQueue<ScanningJob> jobQueue;

    private long bytesSizeThreshold;

    private String directoryToScan;

    private String corpusPrefix;

    private Map<String, Long> lastModified;


    private class FileJob implements ScanningJob {
        private List<URI> uris;

        public FileJob(List<URI> uris) {
            this.uris = uris;
        }

        @Override
        public ScanningType getType() {
            return ScanningType.FILE;
        }

        @Override
        public String getQuery() {
            return "file";
        }

        @Override
        public List<URI> getUris() {
            return null;
        }

        @Override
        public Future<Map<String, Integer>> initiate() {
            return null;
        }



    }

    @Override
    public synchronized void start() {
        super.start();

        File root = new File(directoryToScan);

        File[] files = root.listFiles();
        long currentBytesSize = 0;
        List<URI> uris = new ArrayList<>();

        for (File file : files) {
            long size = file.length();
            if (bytesSizeThreshold >= size + currentBytesSize) {
                currentBytesSize += size;
                uris.add(file.toURI());
            }
            else {
                ScanningJob job = new FileJob(uris);
                uris = new ArrayList<>();
                currentBytesSize = 0;
                try {
                    jobQueue.put(job);
                } catch (InterruptedException e) {
                   // todo log job failed to put on queue
                }
            }
        }
    }



}
