package com.matija.medium;

public class LarrysArray {

    static String larrysArray(int[] A) {
        int inversions = 0;
        for (int i = 0; i < A.length; i++) {
            for(int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j]) {
                    inversions += 1;
                }
            }
        }
        return inversions % 2 == 0 ? "YES" : "NO";
    }

    public static void main(String[] args) {
        System.out.println(larrysArray(new int[]{1,2,3,5,4}));
    }
}
