package stack_queues;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

public class MyQueue2Stacks<T> implements Queue<T>{

	private Stack<T> normal = null, reversed = null;
	private int maxCapacity = -1;
	
	public MyQueue2Stacks() {
		normal = new Stack<>();
		reversed = new Stack<>();
	}
	
	public MyQueue2Stacks(int maxCapacity) {
		super();
		this.maxCapacity = maxCapacity;
	}
	
	@Override
	public int size() {
		return Math.max(normal.size(), reversed.size());
	}

	@Override
	public boolean isEmpty() {
		return normal.isEmpty() && reversed.isEmpty();
	}


	@Override
	public void clear() {
		normal.clear();
		reversed.clear();
	}

	@Override
	public boolean add(T e) {
		if (maxCapacity != -1 && size() >= maxCapacity) {
			throw new IllegalStateException("queue is full");
		}
		transfer(reversed, normal);
		normal.push(e);
		return true;
	}

	@Override
	public boolean offer(T e) {
		boolean ret = false;
		try {
			ret = add(e);
		}
		catch (IllegalStateException ex) {}
		return ret;
	}

	@Override
	public T remove() {
		T e =reversed().pop();
		return e;
	}

	@Override
	public T poll() {
		Stack<T> stack = reversed();
		if (stack.isEmpty()) {
			return null;
		}
		T e = stack.pop();
		return e;
	}

	@Override
	public T element() {
		T e = reversed().peek();
		return e;
	}

	@Override
	public T peek() {
		Stack<T> stack = reversed();
		if (stack.isEmpty()) {
			return null;
		}
		T e = stack.peek();
		return e;
	}
	
	private Stack<T> reversed() {
		transfer(normal, reversed);
		return reversed;
	}

	private void transfer(Stack<T> s1, Stack<T> s2) {
		while(!s1.isEmpty()) {
			s2.push(s1.pop());
		}
	}
	
	@Override
	public boolean contains(Object o) {
		return false;
	}
	
	@Override
	public Iterator<T> iterator() {
		
		return null;
	}
	
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public static void main(String[] args) {
		MyQueue2Stacks<Integer> ints = new MyQueue2Stacks<>();
		ints.add(1);ints.add(2);ints.add(3);
		System.out.println(ints.remove());
		System.out.println(ints.poll());
	
	}
}
