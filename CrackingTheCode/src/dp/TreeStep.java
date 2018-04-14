package dp;

public class TreeStep {

	private static int calls = 0;
	
	public static int countWays(int n) {
		return countWays(n , new int[n+1]);
	}
	
	private static int countWays(int n, int[] results) {
		System.out.println("ENTERED " + n);
		calls++;
		if (n < 0 ) return 0;
		if (n == 0) return 1;
		if (results[n] != 0) return results[n];
		else {
			results[n] = countWays(n-1, results) + countWays(n-2, results) + countWays(n-3, results);
			return results[n];
		}
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(countWays(2000));
		System.out.println(calls);
	}
	
}
