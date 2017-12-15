package easy;


class Solution {

	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		return mergeHelper(t1, t2, null);
	}

	private TreeNode mergeHelper(TreeNode t1, TreeNode t2, TreeNode merged) {
		if (t1 == null || t2 == null) {
			merged = t1 == null ? t2 == null? null:t2:t1;
			return merged;
		} 
		else {
			merged = new TreeNode(t1.val + t2.val);
			merged.left = mergeHelper(t1.left, t2.left, merged.left);
			merged.right = mergeHelper(t1.right, t2.right, merged.right);
			return merged;
		}
	}

	
	
	public static void main(String[] args) {
		TreeNode t1 = new TreeNode(1);
		t1.left = new TreeNode(5);
		t1.left.left = new TreeNode(8);
		t1.right = new TreeNode(4);
		
		TreeNode t2 = new TreeNode(3);
		t2.left = new TreeNode(3);
		t2.right = new TreeNode(6);
		t2.right.right = new TreeNode(7);
		
		
		Solution s = new Solution();
		TreeNode merged = s.mergeTrees(t1, t2);
		System.out.println(merged);
		
	}
}