package com.matija.easy;

import java.util.HashMap;
import java.util.Map;

public class EqualizeArray {

    static int equalizeArray(int[] arr) {
       int[] cnt = new int[101];
       int m = Integer.MIN_VALUE;
       int mi = 0;
       for (int i = 0; i < arr.length; i++) {
           int x = arr[i];
           cnt[x] += 1;
           if (cnt[x] > m) {
               m = cnt[x];
               mi = i;
           }
       }
       return arr.length - cnt[arr[mi]];
    }
}
