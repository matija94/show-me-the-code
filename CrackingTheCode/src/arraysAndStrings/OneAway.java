package arraysAndStrings;

import java.util.Arrays;

public class OneAway {

	public static boolean oneAwayLogarithmicTime(String s, String t) {
		s = s + t;
		char[] merged = s.toCharArray();
		Arrays.sort(merged);
		int i = 0;
		int oddOccurs = 0;
		while(i < merged.length) {
			char current = merged[i];
			int j =i;
			while (j < merged.length && current == merged[j]) {
				j++;
			}
			if ((j-i)%2!=0) {
				oddOccurs++;
			}
			i=j;
		}
		return oddOccurs<=2;
	}
	
	public static boolean oneAwayLinearTime(String s, String t) {
		boolean oneAway = false;
		if (s.length() == t.length()) {
			oneAway = oneAwayReplace(s,t);
		}
		else if (s.length()+1 == t.length()) {
			oneAway = oneAwayInsertion(s,t);
		}
		else if (t.length()+1 == s.length()) {
			oneAway = oneAwayInsertion(t,s);
		}
		return oneAway;
	}
	
	private static boolean oneAwayReplace(String s1, String s2) {
		boolean foundDifference = false;
		for(int i=0; i<s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				if (foundDifference) {
					return false;
				}
				foundDifference=true;
			}
		}
		return true;
	}
	
	private static boolean oneAwayInsertion(String shorter, String longer) {
		int index1 = 0, index2 = 0;
		while (index1 < shorter.length() && index2 < longer.length()) {
			if (shorter.charAt(index1) != longer.charAt(index2)) {
				if (index1 == index2) {
					index2++;
				}
				else {
					return false;
				}
			}
			else {
				index1++;
				index2++;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		System.out.println(oneAwayLinearTime("bapke", "bape"));
	}
}
