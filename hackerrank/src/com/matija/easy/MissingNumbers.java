package com.matija.easy;

import java.util.*;

public class MissingNumbers {

    // Complete the missingNumbers function below.
    static int[] missingNumbers(int[] arr, int[] brr) {
        List<Integer> ints = new ArrayList<>();
        Map<Integer, Integer> arrMap = new HashMap<>();
        Map<Integer, Integer> brrMap = new TreeMap<>();
        count(arr, arrMap);
        count(brr, brrMap);

        for(Map.Entry<Integer, Integer> e : brrMap.entrySet()) {
            if (!arrMap.containsKey(e.getKey()) || arrMap.get(e.getKey()) < e.getValue()) {
                ints.add(e.getKey());
            }
        }

        int[] res = new int[ints.size()];
        for(int i = 0; i<ints.size(); i++) {
            res[i] = ints.get(i);
        }
        return res;
    }

    static void count(int[] arr, Map<Integer, Integer> cntMap) {
        for(int x : arr) {
            if (cntMap.containsKey(x)) {
                cntMap.put(x, cntMap.get(x) + 1);
            }
            else {
                cntMap.put(x, 1);
            }
        }
    }
}
