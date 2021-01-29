package com.matija.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BomberMan {

    static String[] bomber(int n, String[] grid) {
        n-=1; // he does nothing first second, seconds two and three are repeated forever
        String[] res = new String[grid.length];
        if (n % 2 == 1) {
            for(int i = 0; i < grid.length; i++) {
                res[i] = Stream.iterate("O", c -> c).limit(grid[i].length()).collect(Collectors.joining());
            }
            return res;
        }

        char[][] sgriid = new char[grid.length+2][grid[0].length()+2];
        for(int i = 1; i <= grid.length; i++) {
            for(int j = 1; j<=grid[i-1].length(); j++) {
                sgriid[i][j] = grid[i-1].charAt(j-1);
            }
        }
        List<char[][]> r = new ArrayList<>();
        r.add(sgriid);
        for(int k = 0; k < 2; k++) {
            char[][] griid = r.get(r.size()-1);
            char[][] ngriid = new char[griid.length][griid[0].length];
            for(int i = 1; i < griid.length-1; i++) {
                for(int j = 1; j < griid[i].length-1; j++) {
                    if (griid[i][j] == 'O' || griid[i-1][j] == 'O' || griid[i][j-1] == 'O' ||
                            griid[i][j+1] == 'O' || griid[i+1][j] == 'O') {
                        ngriid[i][j] = '.';
                    }
                    else {
                        ngriid[i][j] = 'O';
                    }
                }
            }
            r.add(ngriid);
        }

        char[][] griid;
        if (n == 0) {
            return grid;
        }
        if (n % 4 == 0) {
            griid = r.get(2);
        }
        else {
            griid = r.get(1);
        }
        for(int i = 1; i < griid.length-1; i++) {
            res[i - 1] = new String(griid[i]).substring(1, griid[i].length - 1);
        }
        return res;
    }


    public static void main(String[] args) {
        String[] cells = new String[]{
            ".......",
            "...O.O.",
            "....O..",
            "..O....",
            "OO...OO",
            "OO.O..."
        };

        for(int i = 1; i < 30; i++) {
            String[] res = bomber(i, cells);
            for(String r : res) {
                System.out.println(r);
            }

            System.out.println("==================");
        }

    }
}
