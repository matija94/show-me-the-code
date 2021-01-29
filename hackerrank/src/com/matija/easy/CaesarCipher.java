package com.matija.easy;

public class CaesarCipher {

    // Complete the caesarCipher function below.
    static String caesarCipher(String s, int k) {
        char[] r = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            boolean upper=false;
            if(ch >= 65 && ch <= 90) {
                ch += 32;
                upper = true;
            }
            if (ch >= 97 && ch <= 122) {
                ch -= 97;
                int z = ((ch + k) % 26) + 97;
                if (upper) {
                    z-=32;
                }
                r[i] = (char) z;

            }
            else {
                r[i] = s.charAt(i);
            }

        }
        return new String(r);

    }

    public static void main(String[] args) {
        System.out.println(caesarCipher("www.abc.xy", 87));
    }
}
