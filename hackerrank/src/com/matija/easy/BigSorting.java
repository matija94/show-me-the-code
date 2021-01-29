package com.matija.easy;

import java.util.*;

public class BigSorting {

    // Complete the bigSorting function below.
    static String[] bigSorting(String[] unsorted) {
        Arrays.sort(unsorted, (x, y) -> x.length() == y.length() ? x.compareTo(y) : Integer.compare(x.length(), y.length()));
        return unsorted;
    }

    public static void main(String[] args) {
        String[] z = bigSorting(new String[]{"1", "11"});
        for(String x : z) {
            System.out.println(x);
        }
    }
}

