package dp;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {

	
	public static List<List<Integer>> powerSet(List<Integer> set) {
		ArrayList<List<Integer>> ps= new ArrayList<>();
		ps.add(new ArrayList<>());
		powerSet(set,ps);
		return ps;
	}

	private static void powerSet(List<Integer> set, ArrayList<List<Integer>> ps) {
		int n = set.size();
		if (n > 0) {
			int tail = set.remove(n-1);
			List<List<Integer>> newComputedPS = new ArrayList<>();
			for(List<Integer> computedSet: ps ) {
				List<Integer> newComputedSet = new ArrayList<>(computedSet);
				newComputedSet.add(tail);
				newComputedPS.add(newComputedSet);
			}
			ps.addAll(newComputedPS);
			powerSet(set,ps);
		}
	}
	
	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<>();
		ints.add(1);ints.add(2);ints.add(3);
		System.out.println(powerSet(ints));
	}
	
}
