package trees_graphs;

public class BinaryNode<T> {

	T data;
	BinaryNode<T> left;
	BinaryNode<T> right;
	
	public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
	
}
