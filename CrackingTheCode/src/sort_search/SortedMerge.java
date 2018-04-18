package sort_search;

public class SortedMerge {

	// both a & b are sorted and a should have enough space to hold elements from b
	public static void merge(int[] a, int[] b) {
		int right = a.length,left=0;
		for(int i=b.length-1; i>=0; i--) {
			a[--right]=b[i];
		}
		
		int[] helper = new int[a.length];
		for(int i=0; i<a.length;i++) {
			helper[i] = a[i];
		}
		int current = 0;
		int boundary = right;
		while(left<boundary && right < a.length) {
			if(helper[left] <= helper[right]) {
				a[current++] = helper[left++];
			}
			else {
				a[current++] = helper[right++];
			}
		}
		while(left<boundary) {
			a[current++]=helper[left++];
		}
		while(right<a.length) {
			a[current++]=helper[right++];
		}
	}
	
	public static void mergeV2(int a[], int b[]) {
		int current = a.length-1;
		int indexB = b.length-1;
		int indexA = a.length - b.length - 1;
		while(indexB >= 0) {
			if (indexA > -1 && a[indexA] > b[indexB]) {
				a[current--] = a[indexA--];
			}
			else {
				a[current--] = b[indexB--];
			}
		}
	}
	
	public static void main(String[] args) {
		int a[] = {2,4,6,0,0,0};
		int b[] = {1,3,5};
		
		mergeV2(a,b);
		
		for(int i : a) {
			System.out.println(i);
		}
		
	}
}
