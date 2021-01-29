package com.matija.easy;

public class SeparateNumbers {

    // Complete the separateNumbers function below.
    static void separateNumbers(String s) {
        if (s.charAt(0) == '0') {
            System.out.println("NO");
            return;
        }
        int i= 1;
        String start = "";
        boolean good = false;
        while (i <= s.length()/2) {
            good = true;
            start = s.substring(0, i);
            String prev = start;
            String curr;
            int l = i;
            int r = allNines(start) ?  l + start.length() + 1 : l + start.length();
            while(l < s.length() && r <= s.length()) {
                curr = s.substring(l, r);
                if (Long.parseLong(curr) - Long.parseLong(prev) != 1) {
                    i += 1;
                    good = false;
                    break;
                }
                else {
                    prev = curr;
                    l = r;
                    r = allNines(curr) ? l + curr.length() + 1 : l + curr.length();
                }
            }
            if (good) {
                String p = s.endsWith(prev) ? "YES " + start : "NO";
                System.out.println(p);
                break;
            }
        }
        if (!good) {
            System.out.println("NO");
        }
    }

    static boolean allNines(String s) {
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        separateNumbers("42949672954294967296429496729");
    }
}
