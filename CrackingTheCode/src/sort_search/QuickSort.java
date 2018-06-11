package sort_search;

import java.util.Random;

public class QuickSort<T> {

	private static int calls = 0;
	
	public static <T extends Comparable<T>> void sort(T data[]) {
		sort(data,0,data.length-1);
	}
	
	public static <T extends Comparable<T>> void sort1(T data[]) {
		sort1(data,0,data.length-1);
	}

	public static <T extends Comparable<T>> void sort1NonInc(T data[]) {
	    sort1NonInc(data,0, data.length-1);
    }
	
	private static <T extends Comparable<T>> void sort(T[] data, int left, int right) {
		calls++;
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
	
	private static <T extends Comparable<T>> void sort1(T[] data, int left, int right) {
		if (left < right) {
			int q = partition1(data,left,right);
			sort1(data,left,q-1);
			sort1(data,q+1,right);
		}
	}

	private static <T extends Comparable<T>> void sort1NonInc(T[] data, int left, int right){
	    if (left < right) {
	        int q = partition1NonInc(data,left,right);
	        sort1NonInc(data,left,q-1);
	        sort1NonInc(data,q+1, right);
        }
    }

	private static <T extends Comparable<T>> int partition1(T[] data, int left, int right) {
		T pivot = data[right];
		int i = left-1;
		for (int j=left; j<right; j++) {
			if (data[j].compareTo(pivot) <= 0) {
				i+=1;
				swap(data,i,j);
			}
		}
		swap(data, i+1, right);
		return i+1;
	}

	public static <T extends Comparable<T>> int radonmizedPartition(T[] data, int left, int right) {
		Random r = new Random();
	    int randomIndex = r.nextInt(right-left+1) + left;
	    swap(data, randomIndex, right);
	    return partition1(data,left,right);
	}

	private static <T extends Comparable<T>> int partition1NonInc(T[]data, int left, int right) {
	    T pivot = data[right];
	    int i = left-1;
	    for (int j=left; j<right; j++) {
	        if (data[j].compareTo(pivot) > 0) {
	            i++;
	            swap(data,i,j);
            }
        }
        swap(data, i+1, right);
	    return i+1;
    }

	private static <T extends Comparable<T>> void swap(T[] data, int left, int right) {
		T temp = data[left];
		data[left] = data[right];
		data[right] = temp;
	}
	
	public static void main(String[] args) {
		Integer[] ints = {10,5,3,9,1,2,4};
		sort1NonInc(ints);
		for (int i : ints) {
			System.out.println(i);
		}
	}
}
