package com.matija.medium;

public class Encryption {

    static String encryption(String s) {
        s = s.replaceAll("\\s+", "");
        int l = s.length();

        double sqrtL = Math.sqrt(l);
        int[] o = findOptimal(Math.floor(sqrtL), Math.ceil(sqrtL), l);

        char[][] r = new char[o[0]][o[1]];
        int z = 0;
        for(int i = 0; i < o[0] && z < s.length(); i++) {
            for (int j = 0; j < o[1] && z < s.length(); j++) {
                r[i][j] = s.charAt(z++);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < o[1]; i++) {
            for (int j = 0; j < o[0]; j++) {
                if (Character.isAlphabetic(r[j][i])) {
                    sb.append(r[j][i]);
                }
            }
            if (i < o[1]-1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    static int[] findOptimal(double floor, double ceil, int l) {

        boolean changeF = true;
        while(floor * ceil < l) {
            if (changeF) {
                floor++;
                changeF = false;
            }else {
                ceil++;
                changeF = true;
            }
        }
        return new int[]{(int)floor, (int)ceil};
    }
    public static void main(String[] args) {
        System.out.println(encryption("chillout"));
    }
}
