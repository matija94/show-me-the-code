package com.matija.easy;

public class StrangeCounter {

    static long strangeCounter(long t) {
        long lt;
        long rt;
        long li;
        long v = 0;
        while(true) {
            li = 3 * ((long) Math.pow(2,v));
            lt = 3 * ((long) Math.pow(2,v)) - 2l;
            rt = 3 * ((long) Math.pow(2,v+1)) - 3l;
            v += 1;
            if (rt >= t) break;
        }
        return li - (t - lt);
    }

    public static void main(String[] args) {
        System.out.println(strangeCounter(9));
    }
}
