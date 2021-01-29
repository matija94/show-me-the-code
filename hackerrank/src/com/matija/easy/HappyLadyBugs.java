package com.matija.easy;

import java.util.Arrays;

public class HappyLadyBugs {

    // Complete the happyLadybugs function below.
    static String happyLadybugs(String b) {
        if (b.length() == 1) {
            return b.charAt(0) == '_' ? "YES" : "NO";
        }
        char[] chars = b.toCharArray();
        Arrays.sort(chars);
        String res;
        if(chars[chars.length-1] == '_') {
            res = helper(chars);
        }
        else {
            res = helper(b.toCharArray());
        }
        return res == null ? "YES" : res;
    }

    static String helper(char[] chars){
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '_') {
                break;
            }
            if((i == 0 || chars[i] != chars[i-1]) && (i == chars.length-1 || chars[i] != chars[i+1])) {
                return "NO";
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(happyLadybugs("JI_JWHSBIA__JHIWHII_KK__JIBHK__KBS_B"));
    }
}
