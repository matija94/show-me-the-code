package com.matija.easy;

public class SuperReducedString {

    static String superReducedString(String s) {
        int[] changed = new int[]{0};
        s = superReducedStringInner(s, changed);
        while(changed[0] == 1) {
            changed[0] = 0;
            s = superReducedStringInner(s, changed);
        }
        return s.length() == 0 ? "Empty String" : s;
    }

    // Complete the superReducedString function below.
    static String superReducedStringInner(String s, int[] changed) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < s.length()-1) {
            if (s.charAt(i) != s.charAt(i+1)) {
                sb.append(s.substring(i, i+1));
                i += 1;
            }
            else {
                changed[0] = 1;
                i += 2;
            }
        }
        if (i == s.length()-1) {
            sb.append(s.substring(i, i+1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(superReducedString("abba"));
    }
}
