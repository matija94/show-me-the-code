package com.matija.easy;

import java.util.Set;

public class Pwd {

    static int minimumNumber(int n, String password) {
        Set<Character> nums = Set.of('0', '1', '2','3','4','5','6','7','8','9');
        Set<Character> lowers = Set.of('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
        Set<Character> uppers = Set.of('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
        Set<Character> specials = Set.of('!','@','#','$','%','^','&','*','(',')','-','+');

        boolean needNums = true;
        boolean needLowers = true;
        boolean needUppers = true;
        boolean needSpecials = true;
        int neededChars = 6;
        for(char c : password.toCharArray()) {
            neededChars -= 1;
            if (nums.contains(c)) {
                needNums = false;
            }
            if (lowers.contains(c)) {
                needLowers = false;
            }
            if (uppers.contains(c)) {
                needUppers = false;
            }
            if (specials.contains(c)) {
                needSpecials = false;
            }
        }
        int r = 0;
        if (needNums) {
            neededChars -= 1;
            r += 1;
        }
        if (needLowers) {
            neededChars -= 1;
            r += 1;
        }
        if (needUppers) {
            neededChars -= 1;
            r += 1;
        }
        if (needSpecials) {
            neededChars -= 1;
            r += 1;
        }
        return r + Math.max(neededChars, 0);
    }

    public static void main(String[] args) {
        System.out.println(minimumNumber(6,"matija"));
    }
}
