package com.matija.easy;

import java.util.Arrays;

public class FlatlandSpaceStations {

    static int flatlandSpaceStations(int n, int[] c) {
        if (c.length > n/2) {
            int[] spaceStation = new int[n];
            for (int x : c) {
                spaceStation[x] = 1;
            }
            int r = 0;
            for (int i = 0; i < n; i++) {
                int  z = n;
                int m = 0;
                while((i - m >= 0) || (i + m) < n) {
                    if (i - m >= 0 && spaceStation[i-m] == 1) {
                        z = Math.min(z, m);
                        break;
                    }
                    else if (i + m < n && spaceStation[i+m] == 1) {
                        z = Math.min(z, m);
                    }
                    m += 1;
                }
                r = Math.max(r,z);
            }
            return r;
        }
        else {
            int r = 0;
            for (int i = 0; i < n; i++) {
                int z = n;
                for (int x : c) {
                    z = Math.min(z, Math.abs(i -x ));
                }
                r = Math.max(r,z);
            }
            return r;
        }
    }

    public static void main(String[] args) {
        System.out.println(flatlandSpaceStations(20, new int[]{13, 1, 11, 10, 6}));
    }
}
