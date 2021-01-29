package com.matija.easy;

public class BeautifulDaysAtMovies {

    static int beautifulDays(int i, int j, int k) {
        int beautifulDays = 0;
        while (i <= j) {
            if (Math.abs(i - reverseInt(i)) % k == 0) {
                beautifulDays++;
            }
            i++;
        }
        return beautifulDays;
    }

    static int reverseInt(Integer i) {
        char[] chars = i.toString().toCharArray();
        char[] reversedChars = new char[chars.length];
        for (int j = chars.length-1; j >= 0; j--) {
            reversedChars[chars.length-j-1] = chars[j];
        }
        return Integer.parseInt(new String(reversedChars));
    }

    public static void main(String[] args) {
        beautifulDays(20, 23, 6);
    }
}
