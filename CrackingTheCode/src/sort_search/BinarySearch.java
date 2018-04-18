package sort_search;

public class BinarySearch {

	
	public static <T extends Comparable<T>> int recursive(T data[], T target) {
		return recursive(data, 0, data.length-1, target);
	}

	private static <T extends Comparable<T>> int recursive(T[] data, int lo, int hi, T target) {
		if (lo <= hi) {
			int mid = (lo+hi)/2;
			if (data[mid].compareTo(target) == 0) {
				return mid;
			}
			else if (data[mid].compareTo(target) < 0) {
				return recursive(data,mid+1,hi,target);
			}
			else {
				return recursive(data,lo,mid-1,target);
			}
		}
		return -1;
	}
	
	public static <T extends Comparable<T>> int iterative(T data[], T target) {
		int lo = 0, hi = data.length-1;
		while(lo <= hi) {
			int mid = (lo+hi)/2;
			if (data[mid].compareTo(target) == 0) {
				return mid;
			}
			else if (data[mid].compareTo(target) < 0) {
				lo=mid+1;
			}
			else {
				hi=mid-1;
			}
		}
		return -1;
	}
	
	
	public static void main(String[] args) {
		Integer[] ints = {3,4,5,6};
		System.out.println(recursive(ints, 6));
		System.out.println(iterative(ints, 6));
	}
}
