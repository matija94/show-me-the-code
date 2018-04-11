package trees_graphs;

public class BinaryNode<T> {

	T data;
	BinaryNode<T> left;
	BinaryNode<T> right;
	BinaryNode<T> parent;
	
	public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
	
}
