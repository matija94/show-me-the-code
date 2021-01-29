package com.matija.easy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeightedUniformString {

    static String[] weightedUniformStrings(String s, int[] queries) {
        String[] res = new String[queries.length];

        Map<Character, List<Integer>> m = new HashMap<>();
        for(char c : s.toCharArray()) {
            final int z = ((int) c) - 97 + 1;
            List<Integer> l = m.getOrDefault(c, new ArrayList<>());
            if (l.isEmpty()) {
                l.add(z);
            }
            else {
                l.add(l.get(l.size()-1) + z);
            }
            m.put(c, l);
        }
        Set<Integer> integers = m.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        for(int i = 0; i<queries.length; i++) {
            res[i] = integers.contains(queries[i]) ? "Yes" : "No";
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(List.of(1,2,3,4).stream().flatMap(Stream::of).collect(Collectors.toList()));
    }
}
