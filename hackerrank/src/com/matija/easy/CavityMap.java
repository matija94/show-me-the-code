package com.matija.easy;

public class CavityMap {

    // Complete the cavityMap function below.
    static String[] cavityMap(String[] grid) {
        String[] r = new String[grid.length];
        r[0] = grid[0];
        for (int i = 1; i<grid.length-1; i++) {
            char[] crs = grid[i].toCharArray();
            char[] chars = grid[i].toCharArray();
            for (int j = 1; j < grid[i].length()-1; j++) {
                int up = Integer.parseInt(String.valueOf(grid[i-1].charAt(j)));
                int left = Integer.parseInt(String.valueOf(chars[j-1]));
                int right = Integer.parseInt(String.valueOf(chars[j+1]));
                int down = Integer.parseInt(String.valueOf(grid[i+1].charAt(j)));

                int curr = Integer.parseInt(String.valueOf(chars[j]));
                int biggest = Math.max(up, Math.max(left, Math.max(right, down)));
                if (curr > biggest) {
                    crs[j]='X';
                }
            }
            r[i] = new String(crs);
        }
        r[grid.length-1] = grid[grid.length-1];
        return r;
    }


}
