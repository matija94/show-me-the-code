package com.matija.easy;

import java.util.ArrayList;
import java.util.List;

public class CutTheSticks {

    static int[] cutTheSticks(int[] arr) {
        List<Integer> res = new ArrayList<>();
        List<Integer> arrList = new ArrayList<>();
        List<Integer> newArrList = new ArrayList<>();
        int m = Integer.MAX_VALUE;
        for (int a: arr) {
            m = Math.min(a, m);
            arrList.add(a);
        }
        while (!arrList.isEmpty()) {
            int nm = Integer.MAX_VALUE;
            res.add(arrList.size());
            for (int i : arrList) {
                int d = i - m;
                if (d > 0) {
                    newArrList.add(d);
                    nm = Math.min(nm, d);
                }
            }
            arrList = newArrList;
            newArrList = new ArrayList<>();
            m = nm;
        }
        return toArray(res);
    }

    static int[] toArray(List<Integer> ints) {
        int[] arr = new int[ints.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ints.get(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        cutTheSticks(new int[]{1,13,3,8,14,9,4,4});
    }
}
