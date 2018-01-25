package arraysAndStrings;

public class IsUnique {

	/**
	 * Tests if the string has all unique characters
	 * @param str
	 * @return true or false
	 */
	public static boolean isUnique(String str) {
		if(str.length() > 128) {
			// ascii table has 128 characters
			return false;
		}
		
		boolean charTable[] = new boolean[128];
		for (char c : str.toCharArray()) {
			int digitRepr = (int)c;
			if (digitRepr > 127 || charTable[digitRepr]) {
				return false;
			}
			charTable[digitRepr] = true;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isUnique("Matija"));
		System.out.println(isUnique("Igor"));
	}
	
}
