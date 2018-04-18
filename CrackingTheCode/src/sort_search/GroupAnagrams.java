package sort_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {

	public static void group(String [] data) {
		Map<String, List<String>> map = new HashMap<>();
		for(String s : data) {
			map.compute(wordKey(s), (k, v) -> (v == null) ? new ArrayList<>(Arrays.asList(s)) : concatArrayLists(v,Arrays.asList(s)));
		}
		int current = 0;
		for (List<String> words : map.values()) {
			for(String word : words) {
				data[current++] = word;
			}
		}
		
	}
	
	private static ArrayList<String> concatArrayLists(List<String> l1, List<String> l2) {
		ArrayList<String> l = new ArrayList<>();
		l.addAll(l1);
		l.addAll(l2);
		return l;
	}

	private static String wordKey(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}
	


	public static void main(String[] args) {
		String[] data = {"matija", "miljan", "jana", "ljanmi", "anaj", "tijama"};
		group(data);
		
		for(String s : data) {
			System.out.println(s);
		}
	}
}
