package trees_graphs;

public class BalanceCheck {

	private static final int UNBALANCED = -1;
	
	public static <T> boolean check(BinaryNode<T> root) {
		int res = checkInt(root, new boolean[] {true});
		return res != UNBALANCED;
	}
	
	
	private static <T> int checkInt(BinaryNode<T> root, boolean[] balanced) {
		if (root != null) {
			int left = checkInt(root.left,balanced);
			if (!balanced[0]) {
				return UNBALANCED;
			}
			int right = checkInt(root.right,balanced);
			if (!balanced[0]) {
				return UNBALANCED;
			}
			if (Math.abs(left-right) > 1) {
				balanced[0] = false;
			}
			return Math.max(left, right)+1;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		BinaryNode<Integer> root = new BinaryNode<Integer>(5, null, null);
		root.left = new BinaryNode<Integer>(3, new BinaryNode<>(2, null, null), new BinaryNode<>(4, null, null));
		root.right = new BinaryNode<Integer>(7, new BinaryNode<>(6, null, null), new BinaryNode<>(8, null, new BinaryNode<>(9, null, null)));
		
		System.out.println(check(root));
	}
}
