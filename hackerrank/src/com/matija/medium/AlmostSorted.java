package com.matija.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlmostSorted {


    // 1 3 2
    // 1 2 3

    // 1 5 4 2 3 6 7
    // 1 2 3 4 5 6 7


    // 3 1 2
    // 1 2 3


    // 5 2 3 4 1
    // 1 2 3 4 5
    static void almostSorted(int[] arr) {
        int[] arrCp = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCp);
        List<Integer> mismatch = new ArrayList<>();
        for (int i = 0; i<arr.length; i++) {
            if (arrCp[i] != arr[i]) {
                mismatch.add(i);
            }
        }
        if (mismatch.size() == 0) {
            System.out.println("yes");
        }
        else {
            int l = mismatch.get(0);
            int r = mismatch.get(mismatch.size()-1);
            arr = reverse(arr, l, r, mismatch.size() == 2);
            if (isSorted(arr)) {
                System.out.println("yes");
                String s = mismatch.size() == 2 ? "swap" : "reverse";
                System.out.println(s + " " + (l+1) + " " + (r+1));
            }
            else {
                System.out.println("no");
            }
        }
    }

    static int[] reverse(int[] arr, int l, int r, boolean swap) {
        if (swap) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            return arr;
        }
        int[] ar = new int[arr.length];
        int i = 0;
        while (i < ar.length) {
            if(i >= l && i <= r){
                for(int j = r; j >= l; j--) {
                    ar[i++] = arr[j];
                }
            }
            else {
                ar[i] = arr[i];
                i++;
            }
        }
        return ar;
    }

    static boolean isSorted(int[] arr) {
        for(int i = 0; i < arr.length-1; i++) {
            if (arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        almostSorted(new int[]{5,2,3,4,1});
    }
}
