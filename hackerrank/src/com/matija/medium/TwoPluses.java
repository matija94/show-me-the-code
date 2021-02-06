package com.matija.medium;

import java.util.*;

public class TwoPluses {

//"G G G G G G G G",
//"G B G B G G B G",
//"G B G B G G B G",
//"G G G G G G G G",
//"G B G B G G B G",
//"G G G G G G G G",
//"G B G B G G B G",
//"G G G G G G G G"


    static class Plus {
        int length;
        Set<String> pairs;

        Plus(int length, Set<String> pairs) {
            this.length = length;
            this.pairs = pairs;
        }
    }

    // Complete the twoPluses function below.
    static int twoPluses(String[] grid) {
        Set<String> busy = new HashSet<>();
        List<Plus> goodPluses = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            String r = grid[i];
            for(int j = 0; j < r.length(); j++) {
                int c = j;
                while (c < r.length() && r.charAt(c) == 'G') {
                    int l = c - (j - 1);
                    if (l % 2 == 1) {
                        if (l > 1 && i - l/2 >=0 && i + l/2 < grid.length) {
                            boolean goodPlus = true;
                            Set<String> b = new HashSet<>();
                            for(int z = i - l/2; z <= i + l/2; z++) {
                                b.add(z + " " + (l/2 + j));
                                if (grid[z].charAt(l/2 + j) != 'G') {
                                    goodPlus = false;
                                    break;
                                }
                            }
                            if (goodPlus) {
                                busy.addAll(b);
                                for(int k = j; k <= c; k++) {
                                    busy.add(i + " " + k);
                                }
                                goodPluses.add(new Plus(l, busy));
                                busy = new HashSet<>();
                            }
                        }
                    }
                    c++;
                }
            }
        }
        return findMax(goodPluses);
    }

    private static int findMax(List<Plus> goodPluses) {
        int m = goodPluses.size() > 0 ?goodPluses.get(0).length * 2 - 1 : 1;
        for(int i = 0; i<goodPluses.size();i++){
            for(int j = i+1;j<goodPluses.size();j++){
                Plus a = goodPluses.get(i);
                Plus b = goodPluses.get(j);
                if (a.pairs.stream().noneMatch(b.pairs::contains)){
                    int aa = a.length*2-1;
                    int ba = b.length*2-1;
                    m = Math.max(m, aa*ba);
                }
            }
        }
        return m;
    }

    public static void main(String[] args) {
        int z=twoPluses(new String[]{
                "GGGGGGGGG",
                "GBBBGGBGG",
                "GBBBGGBGG",
                "GBBBGGBGG",
                "GBBBGGBGG",
                "GBBBGGBGG",
                "GBBBGGBGG",
                "GGGGGGGGG"
        });
        System.out.println(z);
    }
}
