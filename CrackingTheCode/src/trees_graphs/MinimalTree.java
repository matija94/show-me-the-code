package trees_graphs;

public class MinimalTree {

	
	public static BinaryNode<Integer> make(int[] sortedArray) {
		BinaryNode<Integer> root = new BinaryNode<Integer>(null, null, null);
		make(sortedArray, 0, sortedArray.length-1, root);
		return root;
	}
	
	private static void make(int[] sortedArray, int lo, int high, BinaryNode<Integer> root) {
		if (lo>high) {
			return;
		}
		int mid = (lo + high) / 2;
		int item = sortedArray[mid];
		if (root.data == null) {
			root.data = item;
		}
		else {
			if (item <= root.data) {
				root.left = new BinaryNode<Integer>(item, null, null);
				root = root.left;
			}
			else {
				root.right = new BinaryNode<Integer>(item, null, null);
				root = root.right;
			}
		}
		make(sortedArray,mid+1,high,root);
		make(sortedArray,lo,mid-1,root);
	}
	
	public static void main(String[] args) {
		int[] ints = new int[]{1,2,3,4,5,6,7,8};
		BinaryNode<Integer> root = make(ints);
		System.out.println(root);
	}
	
}
