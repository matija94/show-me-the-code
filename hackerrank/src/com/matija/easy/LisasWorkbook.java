package com.matija.easy;

public class LisasWorkbook {

    // Complete the workbook function below.
    static int workbook(int n, int k, int[] arr) {
        int specialProblems = 0;
        int page = 0;
        for (int i = 0; i < arr.length; i++) {
            int problems = arr[i];
            int left = 1;
            int right = Math.min(k, problems);
            while(problems > 0) {
                page+=1;
                problems-=k;
                if (page >= left && page <= right) {
                    specialProblems++;
                }
                left = right+1;
                right += Math.min(k, problems);
            }
        }
        return specialProblems;
    }

    public static void main(String[] args) {
        System.out.println(workbook(5, 3, new int[]{4, 2, 6, 1, 10}));
    }
}
