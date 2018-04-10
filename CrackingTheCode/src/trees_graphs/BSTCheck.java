package trees_graphs;

import java.util.ArrayList;
import java.util.List;

public class BSTCheck {

	
	public static boolean check1(BinaryNode<Integer> root) {
		List<Integer> nodes = new ArrayList<>();
		inOrder(nodes, root);
		return isSorted(nodes);
	}
	
	public static boolean check2(BinaryNode<Integer> root) {
		return check2(root,null,null);
	}
	
	private static boolean check2(BinaryNode<Integer> node, Integer min, Integer max) {
		if (node != null) {
			int current = node.data;
			if (min != null) {
				if (min >= current) return false;
			}
			if (max != null) {
				if (max < current) return false;
			}
			if (!check2(node.left,min,current)) {
				return false;
			}
			if (!check2(node.right,current,max)) {
				return false;
			}
		}
		return true;
	}
	
	private static void inOrder(List<Integer> nodes, BinaryNode<Integer> node) {
		if (node != null) {
			inOrder(nodes, node.left);
			nodes.add(node.data);
			inOrder(nodes, node.right);
		}
	}
	
	private static boolean isSorted(List<Integer> data) {
		for (int i =1; i<data.size(); i++) {
			if (data.get(i) < data.get(i-1)) {
				return false;
			}
		}
		return true;
	} 

	public static void main(String[] args) {
		BinaryNode<Integer> root = new BinaryNode<Integer>(10, 
				new BinaryNode<>(8, 
						new BinaryNode<>(7, null, null), 
						new BinaryNode<>(9, null, null)), 
				new BinaryNode<>(12, 
						new BinaryNode<>(11, null, null), 
						new BinaryNode<>(13, null, null)));
	
	
	System.out.println(check2(root));	
	
	}
	
}
