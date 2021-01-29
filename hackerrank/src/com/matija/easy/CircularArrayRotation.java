package com.matija.easy;

public class CircularArrayRotation {

    static int[] circularArrayRotation(int[] a, int k, int[] queries) {
        int n = a.length;
        int rotations = k % n;
        int[] b = new int[a.length];
        int[] res = new int[queries.length];
        if (rotations > 0) {
            int i = n - rotations;
            int j = 0;
            while(i < n) {
                b[j++] = a[i++];
            }
            i = 0;
            while(j < n) {
                b[j++] = a[i++];
            }
            for(int z = 0; z < queries.length; z++) {
                res[z] = b[queries[z]];
            }
        }
        else {
            for(int i = 0; i < queries.length; i++) {
                res[i] = a[queries[i]];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3}; //5 3 4 -> 4 5 3
        int[] q = {0,1,2};
        System.out.println(circularArrayRotation(a, 2, q));
    }
}
