package sort_search;

public class QuickSort<T> {

	
	public static <T extends Comparable<T>> void sort(T data[]) {
		sort(data,0,data.length-1);
	}

	private static <T extends Comparable<T>> void sort(T[] data, int left, int right) {
		int index = partition(data, left, right);
		if (left < index-1) {
			sort(data,left,index-1);
		}
		if (index < right) {
			sort(data,index,right);
		}
	}

	private static <T extends Comparable<T>> int partition(T[] data, int left, int right) {
		T pivot = data[(left+right)/2];
		while (left <= right) {
			
			while (data[left].compareTo(pivot) < 0) left++; // find bigger than pivot
			
			while(data[right].compareTo(pivot) > 0) right--; // find lesser than pivot
		
			if (left <= right) {
				swap(data, left, right);
				left++;
				right--;
			}
		}
		return left;
	}

	private static <T extends Comparable<T>> void swap(T[] data, int left, int right) {
		T temp = data[left];
		data[left] = data[right];
		data[right] = temp;
	}
	
	public static void main(String[] args) {
		Integer[] ints = {4,3,6,5};
		sort(ints);
		for (int i : ints) {
			System.out.println(i);
		}
	}
}
