package com.matija.medium;

import java.util.*;

public class LillysHomework {

    static int lilysHomework(int[] arr) {
        int[] ints = Arrays.copyOf(arr, arr.length);
        int[] arrC = Arrays.copyOf(arr, arr.length);
        Arrays.sort(ints);
        int[] reversedInts = reverse(ints);

        int r1 = get(arrC, ints);
        int r2 = get(arr, reversedInts);
        return Math.min(r1,r2);
    }

    private static int get(int[] arr, int[] ints) {
        int result = 0;
        Map<Integer, Integer> indexesArr = new HashMap<>();
        for(int i = 0; i < arr.length; i++) {
            indexesArr.put(arr[i], i);
        }
        for(int i = 0; i < arr.length; i++) {
            if (ints[i] != arr[i]) {
                Integer swapIndex = indexesArr.get(ints[i]);
                result++;
                arr[swapIndex] = arr[i];
                arr[i] = ints[i];
                indexesArr.put(arr[swapIndex], swapIndex);
                indexesArr.put(arr[i], i);
            }
        }
        return result;
    }

    private static int[] reverse(int[] ints) {
        int[] r = new int[ints.length];
        int j = 0;
        for(int i = ints.length-1; i>=0; i--) {
            r[j++] = ints[i];
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(lilysHomework(new int[]{3,4,2,5,1}));
    }
}
