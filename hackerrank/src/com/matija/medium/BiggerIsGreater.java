package com.matija.medium;

public class BiggerIsGreater {

    static String biggerIsGreater(String w) {
        char[] chars = w.toCharArray();
        int i = w.length() - 1;
        while(i > 0 && w.charAt(i-1) >= w.charAt(i))
            i--;
        if (i == 0) {
            return "no answer";
        }

        for(int j = w.length()-1; j >= i; j--) {
            if (chars[j] > chars[i-1]) {
                char c = chars[j];
                chars[j] = chars[i-1];
                chars[i-1] = c;
                break;
            }
        }

        int r = chars.length-1;
        int l = i;
        while(l < r) {
            char c = chars[l];
            chars[l] = chars[r];
            chars[r] = c;
            l++;
            r--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(biggerIsGreater("dkhc"));
    }
}
