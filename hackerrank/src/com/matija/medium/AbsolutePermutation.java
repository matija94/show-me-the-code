package com.matija.medium;

public class AbsolutePermutation {

    static int[] absolutePermutation(int n, int k) {
        int[] res = new int[n];
        int[] used = new int[n+1];
        for(int i = 1; i<=n; i++) {
            int a = i + k;
            int b = Math.abs(i-k);
            if (b >= 1 && Math.abs(b - i) == k && b < a && used[b] == 0) {
                res[i-1] = b;
                used[b] = 1;
            }
            else if(a - i == k && a <= n && used[a] == 0) {
                res[i-1] = a;
                used[a] = 1;
            }
            else {
                return new int[]{-1};
            }
        }
        return res;
    }

//    static int[] absolutePermutation(int n, int k) {
//        int[] res = new int[n];
//        int[] used = new int[n+1];
//        for(int i = 1; i<=n; i++) {
//            for(int j = 1; j<=n; j++) {
//                if (used[j] == 0 && Math.abs(j - i) == k) {
//                    res[i-1] = j;
//                    used[j] = 1;
//                    break;
//                }
//            }
//            if (res[i-1] == 0) {
//                return new int[]{-1};
//            }
//        }
//        return res;
//    }

    public static void main(String[] args) {
        int[] ints = absolutePermutation(10, 1);
        for(int i : ints) {
            System.out.print(i + " ");
        }
    }
}
