package trees_graphs;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

	T data;
	List<Node<T>> ingoing = new ArrayList<>();
	List<Node<T>> outgoing = new ArrayList<>();
	
	public Node(T data, List<Node<T>> ingoing, List<Node<T>> outgoing) {
		this.data = data;
		this.ingoing = ingoing;
		this.outgoing = outgoing;
	}

	public List<Node<T>> getAdjacentNodes() {
		
		ArrayList<Node<T>> adjacent= new ArrayList<Node<T>>();
		adjacent.addAll(outgoing);
		return adjacent;
	}
	
}
