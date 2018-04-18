package sort_search;

public class MergeSort<T> {
	
	public static <T extends Comparable<T>> void sort(T data[]) {
		@SuppressWarnings("unchecked")
		T helper[] = (T[])new Comparable[data.length];
		sort(data,helper,0,data.length-1);
	}

	private static <T extends Comparable<T>> void sort(T[] data, T[] helper, int lo, int hi) {
		if (lo < hi) {
			int mid = (lo + hi) / 2;
			sort(data,helper,lo,mid);
			sort(data,helper,mid+1,hi);
			merge(data,helper,lo,mid,hi);
		}
	}

	private static <T extends Comparable<T>> void merge(T[] data, T[] helper, int lo, int mid, int hi) {
		for (int i = lo; i<=hi;i++) {
			helper[i] = data[i];
		}
		
		int leftHalf = lo, rightHalf = mid+1;
		int current = lo;
		while (leftHalf <= mid && rightHalf <= hi) {
			if (helper[leftHalf].compareTo(helper[rightHalf]) <= 0) {
				data[current++] = helper[leftHalf++];
			}
			else {
				data[current++] = helper[rightHalf++];
			}
		}
		while(leftHalf <= mid) {
			data[current++] = helper[leftHalf++];
		}
	}
	
	
	public static void main(String[] args) {
		Integer[] ints = {4,3,6,5};
		sort(ints);
		for (int i : ints) {
			System.out.println(i);
		}
	}
}
	
	
