package com.matija.easy;

public class Prisoners {
    static int saveThePrisoner(int n, int m, int s) {
        int r = (m-1+s)%n;
        return r == 0 ? n : r;
    }

    public static void main(String[] args) {
        System.out.println(saveThePrisoner(4 ,3 ,2));
    }
}
