package com.matija.easy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Alternate {

    // Complete the alternate function below.
    static int alternate(String s) {
        Set<Character> st = new HashSet<>();
        for (char c : s.toCharArray()) {
           st.add(c);
        }
        List<Character> l = new ArrayList<>(st);
        int res = 0;
        for(int i = 0; i<l.size(); i++) {
            for (int j = i+1; j<l.size(); j++) {
                int cr = 0;
                char previous = '-';
                for(char c : s.toCharArray()) {
                    if ((c == l.get(i) || c == l.get(j))) {
                        if (previous == c) {
                            cr = 0;
                            break;
                        }
                        previous = c;
                        cr += 1;
                    }
                }
                res = Math.max(res, cr);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(alternate("beabeefeab"));
    }
}
