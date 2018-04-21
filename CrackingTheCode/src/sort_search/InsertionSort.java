package sort_search;

public class InsertionSort {

	
	public static void sort(int[] arr) {
		for(int i=1; i<arr.length; i++) {
			int j = i-1;
			int current = arr[i];
			while(j>=0 && arr[j]>current) {
				arr[i] = arr[j];
				i-=1;
				j-=1;
			}
			arr[j+1] = current;
		}
	}
	
	
	public static void main(String[] args) {
		int[] ints = {5,4,2,1,3};
		sort(ints);
		for(int i : ints) {
			System.out.println(i);
		}
		
	}
}
