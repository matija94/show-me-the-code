package com.matija.medium;

public class ThreeDSurfaceArea {

    static int surfaceArea(int[][] A) {
        int h = A.length;
        int w = A[0].length;
        int area = 2*h*w;

        int[][] a = new int[h+2][w+2];
        for(int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (i == 0 || i == a.length-1 || j == 0 || j == a[0].length-1) {
                    a[i][j] = 0;
                }
                else {
                    a[i][j] = A[i-1][j-1];
                }
            }
        }

        for(int i = 1; i < a.length-1; i++) {
            for (int j = 1; j < a[0].length-1; j++) {
                area += Math.max(0, a[i][j] - a[i-1][j]);
                area += Math.max(0, a[i][j] - a[i+1][j]);
                area += Math.max(0, a[i][j] - a[i][j-1]);
                area += Math.max(0, a[i][j] - a[i][j+1]);
            }
        }
        return area;
    }
}
