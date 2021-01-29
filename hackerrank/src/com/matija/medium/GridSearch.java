package com.matija.medium;

import java.util.Arrays;

public class GridSearch {
    static String gridSearch(String[] G, String[] P) {
        for(int i = 0; i < G.length; i++) {
            String r = G[i];
            String p = P[0];
            for(int j = 0; j <= r.length()-p.length(); j++) {
                if (r.substring(j, j+p.length()).equals(p)) {
                    boolean found = true;
                    for (int z = 1; z < P.length; z++) {
                        String pp = P[z];
                        if (i+z >= G.length || !G[i+z].substring(j, j+pp.length()).equals(pp)) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        return "YES";
                    }
                }
            }
        }
        return "NO";
    }

    public static void main(String[] args) {
        String s = "matija";
        byte[] ns = new byte[2];
        byte[] bytes = Arrays.copyOf(s.getBytes(), 15);
        byte[] bytes1 = Arrays.copyOfRange(s.getBytes(), 0, 15);

        System.out.println(bytes1.length);
        String nss = new String(bytes1);

        System.out.println(nss);
        System.out.println(nss.length());
        System.out.println(nss.getBytes().length);
    }

}
