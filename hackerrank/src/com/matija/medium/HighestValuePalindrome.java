package com.matija.medium;

import java.util.Arrays;

public class HighestValuePalindrome {


    // 12345  2
    // 52345
    // 54345

    // 182 2
    // 282 1

    // 092282  3
    // 292282
    // 292292  1

    // 942239 2
    // 942249 1
    //
    static String highestValuePalindrome(String s, int n, int k) {
        int l = 0;
        int r = n-1;
        char[] chars = s.toCharArray();
        while(l <= r) {
            int li = Integer.parseInt(s.substring(l, l+1));
            int ri = Integer.parseInt(s.substring(r, r+1));
            if (li != ri) {
                if (k > 0) {
                    int mi = Math.max(li, ri);
                    chars[l] = (char)(mi + '0');
                    chars[r] = chars[l];
                    k--;
                }
                else {
                    return "-1";
                }
            }
            l++;
            r--;
        }

        l = 0;
        r = n - 1;
        while(k > 0 && l <= r) {
            if (chars[l] != '9') {
                int i = 1;
                if(l != r && chars[l] == s.charAt(l) && chars[r] == s.charAt(r)) {
                    if (k >= 2) {
                        i = 2;
                    }
                    else {
                        l++;
                        r--;
                        continue;
                    }
                }
                chars[l] = '9';
                chars[r] = '9';
                k-=i;
            }
            l++;
            r--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(highestValuePalindrome("12321", 5, 1));
    }
}
