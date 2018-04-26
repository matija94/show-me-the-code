package sort_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import stack_queues.PriorityMinQueue;

public class HeapSort {

	
	public static <T extends Comparable<T>>void sort(List<T> arr) {
		arr.add(0, null);
		PriorityMinQueue<T> minQueue = new PriorityMinQueue<>();
		minQueue.buildMaxHeap(arr);
		for(int n = 1;n<arr.size()-1;n++) {
			T temp = arr.get(1);
			arr.set(1, arr.get(arr.size()-n));
			arr.set(arr.size()-n, temp);
			minQueue.heapify(arr, 1, arr.size()-n);
		}
	}
	
	public static void main(String[] args) {
		List<Integer> arr = new ArrayList<>(Arrays.asList(4,3,5,1,10,9,11));
		sort(arr);
		System.out.println(arr);
	}
	
}
