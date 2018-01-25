package arraysAndStrings;


public class URLify {

	public static void urlify(char[] string, int trueLength) {
		int spaces = 0, i=0;
		for(i=0; i<trueLength; i++) {
			if (string[i] == ' ') {
				spaces++;
			}
		}
		int index = trueLength-1 + 2*spaces;
		for (i=trueLength-1; i>=0; i--) {
			if (string[i] == ' ') {
				string[index--] = '0';
				string[index--] = '2';
				string[index--] = '%';
			}
			else {
				string[index--] = string[i];
			}
		}
		System.out.println(new String(string));
	}
	
	public static void main(String[] args) {
		String test = "m ti ja lu      ";
		urlify(test.toCharArray(),10);
	}
}
