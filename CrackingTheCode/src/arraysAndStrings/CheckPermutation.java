package arraysAndStrings;

public class CheckPermutation {

	/**
	 * Checks if string t is permutation of string s
	 * @param s
	 * @param t
	 * @return
	 */
	public static boolean test(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		
		int letters [] = new int[128]; // assume ascii char encoding
		
		for(char c : s.toCharArray()) {
			letters[c]++;
		}
		
		for (char c: t.toCharArray()) {
			letters[c]--;
			if (letters[c] < 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(CheckPermutation.test("matija", "tijam "));
	}
}
