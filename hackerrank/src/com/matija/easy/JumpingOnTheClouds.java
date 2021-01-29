package com.matija.easy;

public class JumpingOnTheClouds {

    static int jumpingOnClouds(int[] c) {
        int i = 0;
        int res = 0;
        int cc = 0;
        while (i < c.length-1) {
            int j = i + 2;
            if (j < c.length && (j == cc + 1 || j == cc + 2) && c[j] == 0) {
                i = j;
            }
            else {
                i += 1;
            }
            cc = i;
            res += 1;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(jumpingOnClouds(new int[]{0, 0, 1, 0, 0, 0, 0, 1, 0, 0}));;
    }
}
