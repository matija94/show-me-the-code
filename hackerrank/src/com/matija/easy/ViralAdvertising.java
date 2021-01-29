package com.matija.easy;

public class ViralAdvertising {

    static int viralAdvertising(int n) {
        int total = 0, i =1, shared = 5, liked = 0;
        while (i <= n) {
            liked = ((int) Math.floor(shared/2));
            total += liked;
            shared = liked * 3;
            i++;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(viralAdvertising(5));
    }
}
