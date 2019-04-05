package com.ssamardzic;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends Thread {

    private LinkedBlockingQueue<ScanningJob> jobQueue;

    private DirectoryCrawler directoryCrawler;

    private ResultRetriever resultRetriever;

    private Properties properties;

    public static void main(String[] args) {
        // write your code here

        Scanner scanner = new Scanner(System.in);
        while (scanner.nextLine() != "stop") {
            switch (scanner.nextLine()) {
                case "aw":
                    // TODO ADD WEB
                case "ad":
                    //TODO ADD DIR
            }
        }
    }
}
