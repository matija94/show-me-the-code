package easy;

public class HammingDistance {

	public int hammingDistance(int x, int y) {
        String binaryX = Integer.toBinaryString(x);
        String binaryY = Integer.toBinaryString(y);
        if (binaryX.length() > binaryY.length()) {
        	int diff = binaryX.length() - binaryY.length();
        	binaryY = new String(new char[diff]).replaceAll("\0", "0") + binaryY;
        }
        else if (binaryY.length() > binaryX.length()) {
        	int diff = binaryY.length() - binaryX.length();
        	binaryX = new String(new char[diff]).replaceAll("\0", "0") + binaryX;        	
        }
        return diffCharsOnSamePositions(binaryX, binaryY);
	}
	
	public int diffCharsOnSamePositions(String x , String y) {
		assert x.length() == y.length();
		int n = x.length();
		int different = 0;
		for(int i =0; i<n; i++) {
			if (x.charAt(i) != y.charAt(i)) {
				different+=1;
			}
		}
		return different;
	}
	
	public static void main(String[] args) {
		System.out.println(4^1);
	}
}
