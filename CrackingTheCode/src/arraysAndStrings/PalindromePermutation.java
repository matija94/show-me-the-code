package arraysAndStrings;

import java.util.Arrays;
import java.util.HashMap;

public class PalindromePermutation {

	
	// O(n log n)
	public static boolean test(String string) {
		char[] charArray = string.toCharArray();
		Arrays.sort(charArray);
		int limit = 1;
		for(int i=0; i<charArray.length-1; i+=2) {
			if (charArray[i] == ' ') {
				continue;
			}
			if (charArray[i] !=  charArray[i+1]) {
				if (limit == 0) {
					return false;
				}
				limit=0;
				i-=1;
			}
		}
		return true;
	}
	
	// knowing that palindrome can have at most 1 character which count is odd and rest have to be even
	// we can implement more efficient algorithm
	// O(n)
	public static boolean improvedTest(String string) {
		HashMap<Character, Integer> charCount = new HashMap<>();
	
		for(char c : string.toCharArray()) {
			int cnt = charCount.containsKey(c)?charCount.get(c):0;
			charCount.put(c, ++cnt);
		}
		int limit = 1;
		for (int value : charCount.values()) {
			if (value%2!=0) {
				if (limit == 0 ) {
					return false;
				}
				limit = 0;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(improvedTest("tactcoapapa"));
	}
}
