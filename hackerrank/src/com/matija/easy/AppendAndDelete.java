package com.matija.easy;

public class AppendAndDelete {

    static String appendAndDelete(String s, String t, int k) {
        int n = Math.min(s.length(), t.length());
        int i = 0;
        while(i < n) {
            if (s.charAt(i) != t.charAt(i)) {
                break;
            }
            i++;
        }
        int charsDiff = s.length() - i;
        if (charsDiff > 0) {
            int neededChanges = charsDiff + (t.length()-i);
            return neededChanges <= k ? "Yes" : "No";
        }
        else if (t.length() > s.length()) {
            int diff = t.length() - s.length();
            if (diff < k) {
                int remaining = k - diff;
                if (remaining % 2 == 0) {
                    return "Yes";
                }
                return "No";
            }
            else if (diff == k) {
                return "Yes";
            }
            return "No";
        }
        return "Yes";
    }

    public static void main(String[] args) {
        appendAndDelete("y", "yu", 2);
    }
}
