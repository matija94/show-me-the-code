package trees_graphs;

import java.util.List;

public class Node<T> {

	T data;
	List<Node<T>> adjacent;
	
	public Node(T data, List<Node<T>> adjacent) {
		this.data = data;
		this.adjacent = adjacent;
	}
	
	public T getData() {
		return data;
	}
	
	public List<Node<T>> getAdjacentNodes() {
		return adjacent;
	}
}
