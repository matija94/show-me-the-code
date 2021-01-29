package com.matija.easy;

public class SequenceEquation {

    static int[] permutationEquation(int[] p) {
        int[] pIndexes = new int[p.length+1];
        for (int i = 0; i < p.length; i++) {
            pIndexes[p[i]] = i+1;
        }
        int[] r = new int[p.length];
        for(int i = 1; i <= p.length; i++) {
            r[i-1] = pIndexes[pIndexes[i]];
        }
        return r;
    }

    public static void main(String[] args) {
        int[] r = permutationEquation(new int[]{4,3,5,1,2});
        System.out.println("done");
    }
}
