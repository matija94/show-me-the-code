package com.matija.medium;

import java.io.*;
import java.util.Arrays;

public class ActivityNotifications {

    static int activityNotifications(int[] expenditure, int d) {
        int notifications = 0;
        int[] cs = new int[201];
        for (int i = 0; i < d; i++) {
            cs[expenditure[i]]++;
        }
        for(int i = d; i<expenditure.length; i++) {
            double median = getMedian(d, cs);
            if(expenditure[i] >= (median+median)) {
                System.out.println(expenditure[i]);
                System.out.println(median);
                System.out.println("===================");
                notifications++;
            }
            cs[expenditure[i-d]] -= 1;
            cs[expenditure[i]] += 1;
        }
        return notifications;
    }


    static double median(int d, int[] cs) {
        int mid = d/2;
        int count = 0;
        double prev = 0;
        for(int i = 0; i < cs.length; i++) {
            count += cs[i];
            if (count >= mid+1) {
                if (d % 2 == 1) {
                    return i;
                }
                else {
                    if (cs[i] > 1) {
                        prev = i;
                    }
                    return (((double) i) + prev) / 2.0;
                }
            }
            if (cs[i] > 0) {
                prev = i;
            }
        }
        return 0;
    }

    private static double getMedian(int d, int[] data) {
        double median = 0;
        if (d % 2 == 0) {
            Integer m1 = null;
            Integer m2 = null;
            int count = 0;
            for (int j = 0; j < data.length; j++) {
                count += data[j];
                if (m1 == null && count >= d/2) {
                    m1 = j;
                }
                if (m2 == null && count >= d/2 + 1) {
                    m2 = j;
                    break;
                }
            }
            median = (m1 + m2) / 2.0;
        } else {
            int count = 0;
            for (int j = 0; j < data.length; j++) {
                count += data[j];
                if (count > d/2) {
                    median = j;
                    break;
                }
            }
        }
        return median;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new FileReader(new File("/Users/matijalukovic/Desktop/test_dir/pwd/activity.txt")));
        String[] nd = scanner.readLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] expenditure = new int[n];

        String[] expenditureItems = scanner.readLine().split(" ");

        for (int i = 0; i < n; i++) {
            int expenditureItem = Integer.parseInt(expenditureItems[i]);
            expenditure[i] = expenditureItem;
        }

        int result = activityNotifications(expenditure, d);
        System.out.println(result);
        scanner.close();
    }
}
