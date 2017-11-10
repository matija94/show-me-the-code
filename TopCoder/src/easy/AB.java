package easy;

import java.util.ArrayList;
import java.util.List;

// didnt pass all tests on topcoder
public class AB {
    
	private static int primes[] = {2,3,5,7};
	
	
	// TODO: FIX!!!!
	public String createString(int N, int K) {
		List<Integer> factors = getFactors(K);
		StringBuilder sb = new StringBuilder();
		int n = factors.size();
		int aCount=0,bCount=0,sum=0;
		if (n >=1) {
			factors.add(1);
			aCount = factors.get(0);
			for(int i=0; i<aCount; i++) {
				sb.append("A");
			}
			bCount = factors.stream().reduce((a,b) -> a*b).orElse(1)/aCount;
			for(int i=0;i<bCount;i++) {
				sb.append("B");
			}
		}
		sum = aCount + bCount;
		int diff = N-sum;
		if (diff < 0) {
			return "";
		}
		StringBuilder prefix = new StringBuilder();
		for(int i=0; i<diff;i++) {
			prefix.append('B');
		}
		return prefix.toString() + sb.toString();
	}
    
	
    private List<Integer> getFactors(int K) {
    	List<Integer> factors = new ArrayList<>();
    	getFactors(K, factors);
    	return factors;
    }
    
    private void getFactors(int K, List<Integer> factors) {
    	for(int prime : primes) {
    		if (K >= prime && K%prime == 0) {
    			int factor = K/prime;
    			factors.add(prime);
    			if (factor <= 1) {
    				break;
    			}
    			getFactors(factor, factors);
    			break;
    		}
    	}
    	
    }
    
    public static void main(String[] args) {
		AB ab = new AB();
		System.out.println(ab.createString(5,3));
	}
}
