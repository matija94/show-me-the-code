package easy;

public class RangeEncoding {

	/**
	 * You are given a array that contains a set of positive integers. The elements in
	 * array are all distinct and they are given in increasing order.
	 * 
	 * A range is a finite set of consecutive integers. Formally, for any two
	 * positive integers a ≤ b the range [a,b] is defined to be the set of all
	 * integers that lie between a and b, inclusive. For example, [3,3] = {3} and
	 * [4,7] = {4,5,6,7}.
	 * 
	 * You want to represent the set given in arr as a union of some ranges. Return
	 * the minimum number of ranges you need.
	 * 
	 * 
	 * @param arr
	 * @return {@code int} number of ranges needed
	 */

	public int minRanges(int arr[]) {
		int ranges = 1;
		int i = 1, n = arr.length;
		while (i < n) {
			if (arr[i] - arr[i - 1] > 1)
				ranges++;
			i++;
		}
		return ranges;
	}
}
