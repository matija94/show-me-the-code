package stack_queues;

import java.util.Stack;

public class MinStack<T extends Comparable<T>> extends Stack<T>{

	private Stack<T> minStack;
	
	public MinStack() {
		super();
		minStack = new Stack<>();
	}
	
	public T min() {
		return minStack.pop();
	}
	
	@Override
	public T push(T item) {
		if (minStack.isEmpty() || minStack.peek().compareTo(item) > 0) {
			minStack.push(item);
		}
		return super.push(item);
	}
	
	@Override
	public synchronized T pop() {
		T item = super.pop();
		if (item.compareTo(minStack.peek()) == 0) {
			minStack.pop();
		}
		return item;
	}
	
	public static void main(String[] args) {
		MinStack<Integer> minInt = new MinStack<>();
		minInt.push(5);minInt.push(3);minInt.push(2);minInt.push(10);minInt.push(-1);
		
		System.out.println(minInt.min());
		System.out.println(minInt.pop());
		System.out.println(minInt.min());
		System.out.println(minInt.pop());
	}
	
}
