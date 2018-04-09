package trees_graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListOfDepths {

	
	public static List<LinkedList<BinaryNode<Integer>>> make(BinaryNode<Integer> root) {
		List<LinkedList<BinaryNode<Integer>>> listOfDepths = new ArrayList<>();
		make(root, listOfDepths,0);
		return listOfDepths;
	}
	
	private static void make(BinaryNode<Integer> node, List<LinkedList<BinaryNode<Integer>>> listOfDepths, int depth) {
		if(node != null) {
			if (listOfDepths.size() <= depth) {
				listOfDepths.add(new LinkedList<>());
			}
			make(node.left, listOfDepths, depth+1);
			listOfDepths.get(depth).add(node);
			make(node.right, listOfDepths, depth+1);
		}
	}
	
	public static void main(String[] args) {
		BinaryNode<Integer> root = new BinaryNode<Integer>(5, null, null);
		root.left = new BinaryNode<Integer>(3, new BinaryNode<>(2, null, null), new BinaryNode<>(4, null, null));
		root.right = new BinaryNode<Integer>(7, new BinaryNode<>(6, null, null), new BinaryNode<>(8, null, null));
	
		List<LinkedList<BinaryNode<Integer>>> make = make(root);
	
		for (LinkedList<BinaryNode<Integer>> ll : make) {
			System.out.println("level list");
			for (BinaryNode<Integer> n : ll) {
				System.out.println(n.data);
			}
		}
	}
}
