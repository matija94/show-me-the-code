package com.matija.medium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueensAttack {

    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
        List<String> cells = new ArrayList<>();
        Set<String> obstaclez = getObstacles(obstacles);

        leftUpD(n, r_q, c_q, cells, obstaclez);
        up(n, r_q, c_q, cells, obstaclez);
        rightUpD(n, r_q, c_q, cells, obstaclez);
        left(n, r_q, c_q, cells, obstaclez);
        right(n, r_q, c_q, cells, obstaclez);
        leftDownD(n, r_q, c_q, cells, obstaclez);
        down(n, r_q, c_q, cells, obstaclez);
        rightDownD(n, r_q, c_q, cells, obstaclez);

        return cells.size();
    }

    static Set<String> getObstacles(int[][] obstacles) {
        Set<String> obstaclez = new HashSet<>();
        for(int i = 0; i < obstacles.length; i++) {
            String cell = obstacles[i][0] + " " + obstacles[i][1];
            obstaclez.add(cell);
        }
        return obstaclez;
    }

    static void leftUpD(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r++;
        c--;
        while(r <= n && c > 0) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r++;
            c--;
        }
    }

    static void up(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r++;
        while (r <= n) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r++;
        }
    }

    static void rightUpD(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r++;
        c++;
        while (r <= n && c <= n) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r++;
            c++;
        }
    }

    static void left(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        c--;
        while (c > 0) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            c--;
        }
    }

    static void right(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        c++;
        while (c <= n) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            c++;
        }
    }

    static void leftDownD(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r--;
        c--;
        while(r > 0 && c > 0) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r--;
            c--;
        }
    }

    static void down(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r--;
        while (r > 0) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r--;
        }
    }

    static void rightDownD(int n, int r, int c, List<String> cells, Set<String> obstacles) {
        r--;
        c++;
        while(r > 0 && c <= n) {
            String cell = r + " " + c;
            if (!obstacles.contains(cell)) {
                cells.add(cell);
            }else {
                return;
            }
            r--;
            c++;
        }
    }

    public static void main(String[] args) {
        System.out.println(queensAttack(5, 3, 4, 3, new int[][]{
                new int[]{5, 5},
                new int[]{4, 2},
                new int[]{2, 3}
        }));
    }
}
