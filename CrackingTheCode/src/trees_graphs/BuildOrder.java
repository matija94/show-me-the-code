package trees_graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BuildOrder<T> {

	
	private Graph<T> g;
	
	private BuildOrder() {}
	
	public BuildOrder(List<T> projects, List<T[]> dependecies) {
		buildGraph(projects, dependecies);
	}
	
	
	public <T> LinkedList<T> build() {
		LinkedList<T> buildorder = new LinkedList<>();
		while(!g.nodes.isEmpty()) {
			Node<T> findStartNode = findStartNode();
			if (findStartNode == null) {
				throw new IllegalArgumentException("there is no valid build order");
			}
			g.nodes.remove(findStartNode);
			for (Node<T> n : findStartNode.ingoing) {
				n.outgoing.remove(findStartNode);
			}
			buildorder.add(findStartNode.data);
		}
		return buildorder;
	}
	
	private void buildGraph(List<T> projects, List<T[]> dependecies) {
		Map<T, Node<T>> projectNodes = new HashMap<>();
		for(T project : projects) {
			projectNodes.put(project, new Node<T>(project, new ArrayList<>(), new ArrayList<>()));
		}
		for (T[] dependecy : dependecies) {
			projectNodes.get(dependecy[1]).outgoing.add(projectNodes.get(dependecy[0]));
			projectNodes.get(dependecy[0]).ingoing.add(projectNodes.get(dependecy[1]));
		}
		g = new Graph<T>(new ArrayList<>(projectNodes.values()));
	}
	
	private <T> Node<T> findStartNode() {
		Node<T> start = null;
		for(Node n : g.nodes) {
			if (n.outgoing == null || n.outgoing.size() == 0) {
				start = n;
				break;
			}
		}
		return start;
	}
	
	public static void main(String[] args) {
		List<String> projects = Arrays.asList("a", "b", "c", "d", "e", "f");
		List<String[]> dependecies = Arrays.asList(new String[] {"a","d"},new String[] {"f","b"},new String[] {"b","d"},new String[] {"f","a"},new String[] {"d","c"});
		
		BuildOrder<String> bo = new BuildOrder<>(projects, dependecies);
		LinkedList<String> build = bo.build();
		while (!build.isEmpty()) {
			System.out.println(build.removeFirst());
		}
	}
}
