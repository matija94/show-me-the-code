package trees_graphs;

public class Successor {

	static class Node {
		Node left,parent,right;
		double data;
	}
	
	static class NodeWrapper {
		Node n;
	}
	
	public static Node inOrderSucc(Node node) {
		NodeWrapper w = new NodeWrapper();
		if (node.right == null) {
			leftParent(node.parent, node, w);
		}
		else {
			lowest(node.right, w);
		}
		return w.n;
	}
	
	private static void leftParent(Node parent, Node child, NodeWrapper w) {
		if (parent == null) {
			return;
		}
		else if (parent.left != null && parent.left == child) {
			w.n = parent;
			return;
		}
		leftParent(parent.parent, parent, w);
	}

	private static void lowest(Node node, NodeWrapper w) {
		if (node == null) {
			return;
		}
		w.n = node;
		lowest(node.left, w);
	}
	
	
	public static void main(String[] args) {
		Node ten = new Node();ten.data=10.0;
		Node eight = new Node();eight.data=8.0;ten.left=eight;eight.parent=ten;
		Node nine = new Node();nine.data=9.0;eight.right=nine;nine.parent=eight;
		Node ninefive = new Node();ninefive.data=9.5;nine.right=ninefive;ninefive.parent=nine;
		Node fifteen = new Node();fifteen.data=15.0;ten.right=fifteen;fifteen.parent = ten;
		Node fourteen = new Node();fourteen.data=14.0;fifteen.left=fourteen;fourteen.parent=fifteen;
		Node twelve = new Node();twelve.data=12.0;fourteen.left=twelve;twelve.parent=fourteen;
		Node thirteen = new Node();thirteen.data=13.0;twelve.right=thirteen;thirteen.parent=twelve;
		Node fourteenfive = new Node();fourteenfive.data=14.5;fourteen.right=fourteenfive;fourteenfive.parent=fourteen;
		
		Node next = eight;// THIS IS MOST BOTTOM LEFT NODE IN THE BST(lowest)
		while (next != null) {
			System.out.println(next.data);
			next = inOrderSucc(next);
		}
	} 
}
