package trees_graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class RouteBetweenNodes {

	
	public static <T> List<Node<T>> getRoute(Node<T> a, Node<T> b) {
		Queue<Node<T>> queue = new LinkedList<>();
		Map<Node<T>, Node<T>> previousMap = new HashMap<>();
		Set<Node<T>> visited = new HashSet<>();
		if (a != null) {
			queue.add(a);
			
			while(!queue.isEmpty()) {
				Node<T> n = queue.remove();
				if (n.getAdjacentNodes() != null) {
					for(Node<T> adjacent : n.getAdjacentNodes()) {
						if (!visited.contains(adjacent)) {
							visited.add(adjacent);
							previousMap.put(adjacent, n);
							queue.add(adjacent);
							if (Objects.equals(adjacent, b)) {
								break;
							}
							
						}
					}
				}
			}
			return getPath(new ArrayList<>(), previousMap, b);
		}
	
		return null;
	}
	
	private static <T> List<Node<T>> getPath(List<Node<T>> path, Map<Node<T>, Node<T>> previousMap, Node<T> dest) {
		path.add(dest);
		Node<T> n = previousMap.get(dest);
		if (n != null) {
			return getPath(path, previousMap, n);
		}
		return path;
	}
	
	
	public static void main(String[] args) {
		Node<Integer> one = new Node<Integer>(1, null,null);
		Node<Integer> two = new Node<Integer>(2, null,null);
		Node<Integer> three = new Node<Integer>(3, null,null);
		Node<Integer> four = new Node<Integer>(4, null,null);
		Node<Integer> five = new Node<Integer>(5, null,null);
		Node<Integer> seven = new Node<Integer>(7, null,null);
		Node<Integer> ten = new Node<Integer>(10, null,null);
				
	
		one.outgoing = Arrays.asList(two,three,four);
		two.outgoing = Arrays.asList(four);
		three.outgoing = Arrays.asList(five);
		four.outgoing = Arrays.asList(seven);
		five.outgoing = Arrays.asList(three,seven);
		seven.outgoing = Arrays.asList(ten);
		ten.outgoing = Arrays.asList(two,four,seven);
		
		
		List<Node<Integer>> path = getRoute(one, ten);
		
		for(Node<Integer> n : path) {
			System.out.println(n.data);
		}
	}
}
