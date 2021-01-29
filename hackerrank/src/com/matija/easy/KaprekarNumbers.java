package com.matija.easy;

public class KaprekarNumbers {

    static void kaprekarNumbers(int p, int q) {
        boolean first = true;
        for (int i = p; i <= q; i++) {
            String iS = ((Integer) i).toString();
            int d = iS.length();
            Long iSquared = (long) i * i;
            String iSquaredS = iSquared.toString();
            String l = iSquaredS.substring(0, iSquaredS.length() - d);
            if (l.length() > 0) {
                String r = iSquaredS.substring(l.length());
                if (Integer.parseInt(l) + Integer.parseInt(r) == i) {
                    if (first) {
                        System.out.print(i);
                    }
                    else {
                        System.out.print(" " + i);
                    }
                    first = false;
                }
            }
            else if (i == 1) {
                System.out.print(1);
                first = false;
            }
        }
        if (first) {
            System.out.println("INVALID RANGE");
        }
    }

    public static void main(String[] args) {
        kaprekarNumbers(1, 99999);
    }
}
