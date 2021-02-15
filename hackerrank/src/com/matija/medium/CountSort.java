package com.matija.medium;

import java.io.*;

public class CountSort {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        int i = 0;
        StringBuilder[] sbs = new StringBuilder[100];

        while(i < n) {
            String[] tmp = bufferedReader.readLine().split(" ");
            int x = Integer.parseInt(tmp[0]);
            String s = tmp[1];

            StringBuilder stringBuilder = sbs[x];
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder();
                sbs[x] = stringBuilder;
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            String w = i < n/2 ? "-" : s;
            stringBuilder.append(w);
            i++;
        }
        bufferedReader.close();

        boolean first = true;
        StringBuilder res = new StringBuilder();
        for (StringBuilder sb : sbs) {
            if (sb != null) {
                if (!first) {
                    res.append(" ");
                }
                else {
                    first = false;
                }
                res.append(sb.toString());
            }
        }
        System.out.println(res.toString());
    }
}
