package stack_queues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriorityMinQueue <T extends Comparable<T>> {

	private List<T> data;
	
	public PriorityMinQueue() {
		data = new ArrayList<>();
		data.add(null);
	}
	
	public PriorityMinQueue(List<T> arr) {
		this();
		data.addAll(arr);
		buildMaxHeap(data);
	}
	
	private int left(int i) {
		return i << 1;
	}
	
	private int right(int i) {
		return (i << 1) + 1;
	}
	
	public <T extends Comparable<T>> void heapify(List<T> arr, int i, int n) {
		int left = left(i), right = right(i);
		int largest = i;
		if (left < n && arr.get(left).compareTo(arr.get(largest)) > 0) {
			largest = left;
		}
		if (right < n && arr.get(right).compareTo(arr.get(largest)) > 0) {
			largest = right;
		}
		if (largest != i) {
			T temp = arr.get(i);
			arr.set(i, arr.get(largest));
			arr.set(largest, temp);
			heapify(arr,largest, n);
		}
	}
	
	public <T extends Comparable<T>> void buildMaxHeap(List<T> arr) {
		for (int nonLeaf=(arr.size()-1)/2; nonLeaf>0; nonLeaf--) {
			heapify(arr, nonLeaf, arr.size());
		}
	}
	
	public static void main(String[] args) {
		List<Integer> arr = Arrays.asList(5,3,4,7,8,9,10,12,13);
		PriorityMinQueue<Integer> minHeap = new PriorityMinQueue<>(arr);
		System.out.println(minHeap.data);
	}
}
