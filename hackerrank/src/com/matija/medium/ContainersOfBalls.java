package com.matija.medium;

import java.util.Arrays;

public class ContainersOfBalls {

    static String organizingContainers(int[][] container) {
        int[] containersCap = new int[container.length];
        int[] ballTypeCap = new int[container[0].length];

        for(int i = 0; i < container.length; i++) {
            int currContCap = 0;
            for (int j = 0; j < container[i].length; j++) {
                currContCap += container[i][j];
                ballTypeCap[j] += container[i][j];
            }
            containersCap[i] = currContCap;
        }

        Arrays.sort(containersCap);
        Arrays.sort(ballTypeCap);
        for(int i = 0; i < containersCap.length; i++) {
            if (containersCap[i] != ballTypeCap[i]) {
                return "Impossible";
            }
        }
        return "Possible";
    }
}
