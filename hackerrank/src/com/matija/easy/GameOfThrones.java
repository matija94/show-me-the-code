package com.matija.easy;

public class GameOfThrones {

    static String gameOfThrones(String s) {
        int[] m = new int[123];
        for (int i = 0; i < s.length(); i++){
            m[s.charAt(i)] += 1;
        }

        int oddCnt = 0;
        for(int i = 97; i < 123; i++) {
            if (m[i] % 2 == 1) {
                oddCnt += 1;
                if (oddCnt > 1) return "NO";
            }
        }
        return "YES";
    }
}
