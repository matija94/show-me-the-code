package trees_graphs;

import java.util.List;

public class Graph<T> {

	private List<Node<T>> nodes;
	
	public Graph(List<Node<T>> nodes) {
		this.nodes = nodes;
	}
	
	public List<Node<T>> getNodes() {
		return nodes;
	}
	
}
