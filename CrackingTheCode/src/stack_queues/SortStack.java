package stack_queues;

import java.util.Stack;

public class SortStack {

	public static <T extends Comparable<T>> void sort(Stack<T> s) {
		Stack<T> s1 = new Stack<>();
		while(!s.isEmpty()) {
			T top = s.pop();
			while(!s1.isEmpty() && s1.peek().compareTo(top) > 0) 
				s.push(s1.pop());
			s1.push(top);
		}
		while(!s1.isEmpty())
			s.push(s1.pop());
	}
	
	public static void main(String[] args) {
		Stack<Integer> ints = new Stack<>();
		ints.push(2);ints.push(5);ints.push(3);ints.push(4);ints.push(1);
		sort(ints);
		while(!ints.isEmpty()) {
			System.out.println(ints.pop());
		}
	}
	
}
