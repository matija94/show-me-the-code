package com.matija.easy;

public class AcmTeam {

    static int[] acmTeam(String[] topic) {
        int maxKnown = Integer.MIN_VALUE;
        int teamsMaxKnown = Integer.MIN_VALUE;
        for (int i = 0; i < topic.length; i++) {
            for (int j = i+1; j < topic.length; j++) {
                String l = topic[i];
                String r = topic[j];
                int known = 0;
                for (int z = 0; z < l.length(); z++) {
                    int li = l.charAt(z) == '0' ? 0 : 1;
                    int ri = r.charAt(z) == '0' ? 0 : 1;
                    known += li | ri;
                }
                if (known > maxKnown) {
                    maxKnown = known;
                    teamsMaxKnown = 1;
                }
                else if (known == maxKnown) {
                    teamsMaxKnown += 1;
                }
            }
        }
        return new int[]{maxKnown, teamsMaxKnown};
    }
}
