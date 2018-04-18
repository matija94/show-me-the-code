package dp;

public class MagicIndex {

	
	public static int find(int[] arr) {
		return find(arr,0,arr.length-1);
	}

	private static int find(int[] arr, int lo, int high) {
		if (lo > high) return -1;
		int mid = (lo+high)/2;
		if (arr[mid] == mid) return mid;
		int left = find(arr,lo,Math.min(arr[mid], mid-1));
		if (left!=-1) {
			return left;
		}
		int right = find(arr,Math.max(mid+1, arr[mid]),high);
		return right;
	}
	
	
	public static void main(String[] args) {
		int[] arr = {-10,-2,2,2,5,10,11,11,11,11,20};
		
		System.out.println(find(arr));
	}
	
}
