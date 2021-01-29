package com.matija.medium;

import java.util.HashMap;
import java.util.Map;

public class WhatFlavors {

    // Complete the whatFlavors function below.
    static void whatFlavors(int[] cost, int money) {
        Map<Integer, Integer> m = new HashMap<>();
        int i = 1;
        for(int x : cost) {
            m.put(x, i);
            i++;
        }
        i = 1;
        for(int x : cost) {
            int s = money - x;
            if (m.containsKey(s) && m.get(s) != i) {
                System.out.println(i + " " + m.get(money-x));
                return;
            }
            i++;
        }
    }
}
