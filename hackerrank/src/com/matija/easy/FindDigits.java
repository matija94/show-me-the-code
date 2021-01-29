package com.matija.easy;

public class FindDigits {

    static int findDigits(int n) {
        int result = 0;
        char[] digits = ((Integer) n).toString().toCharArray();
        for(char digit : digits) {
            int divisor = Integer.parseInt(new String(new char[]{digit}));
            if (divisor > 0 && n % divisor == 0) {
                result += 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        findDigits(123);
    }
}
