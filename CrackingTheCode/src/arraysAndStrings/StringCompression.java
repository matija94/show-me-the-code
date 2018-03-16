package arraysAndStrings;
/*
String Compression: Implement a method to perform basic string compression using the counts
of repeated characters. For example, the string aabcccccaaa would become a2b1c5a3. If the
"compressed" string would not become smaller than the original string, your method should return
the original string. You can assume the string has only uppercase and lowercase letters (a - z).*/

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StringCompression {

	public static String compress(String word) {
		Map<Character, Integer> charCountMap = new LinkedHashMap<>();
		
		for (char ch : word.toCharArray()) {
			int val = charCountMap.getOrDefault(ch, 0);
			charCountMap.put(ch, val+1);
		}
		
		StringBuffer sb = new StringBuffer();
		for(Entry<Character, Integer> entry : charCountMap.entrySet()) {
			sb.append(entry.getKey()+entry.getValue().toString());
		}
		return (sb.length() < word.length() ? sb.toString() : word);
	}
	
	
	public static String compress1(String word) {
		StringBuilder sb = new StringBuilder();
		int prevValue = word.charAt(0), count=1;
		char[] charArray = word.toCharArray();
		for (int i =1; i < charArray.length; i++) {
			char ch = charArray[i];
			if ((int)ch != prevValue && prevValue!=0) {
				sb.append(((char)prevValue + Integer.toString(count)));
				count = 1;
			}
			else 
				count++;
			prevValue = ch;
		}
		sb.append(((char)prevValue + Integer.toString(count)));
		return sb.toString();
		
	}
	
	public static void main(String[] args) {
		System.out.println(compress("abcdefgh"));
		System.out.println(compress1("aaabbccdddrrrzzaaa"));
		System.out.println();
	}
	

}
