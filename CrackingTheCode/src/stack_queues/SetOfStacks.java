package stack_queues;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SetOfStacks<T> {

	private static final int POST = 0;
	private static final int GET = 1; 
	
	private int stackThreshold = 10;
	private List<Stack<T>> stacks;
	
	private int currentStack = 0;
	
	public SetOfStacks() {
		this.stacks = new ArrayList<>();
		this.stacks.add(new Stack<>());
	}

	public SetOfStacks(int stackThreshold) {
		this();
		this.stackThreshold = stackThreshold;
	}
	

	public void push(T item) {
		getCurrentStack(POST).push(item);
	}
	
	public T pop() {
		if (currentStack>0 && getCurrentStack(GET).size() == 0) {
			currentStack--;
		}
		return getCurrentStack(GET).pop();
		
	}
	
	public T peek() {
		return getCurrentStack(GET).peek();
	}
	
	public T popAt(int stackIndex) {
		if (stackIndex > currentStack) {
			throw new IndexOutOfBoundsException("There is no stack at position: " + stackIndex);
		}
		T poped = stacks.get(stackIndex).pop();
		shiftLeft(stackIndex);
		return poped;
	}
	
	private Stack<T> getCurrentStack(int procedure) {
		if (procedure == POST && stacks.get(currentStack).size() == stackThreshold) {
			currentStack+=1;
			if (stacks.size() <= currentStack) {
				stacks.add(new Stack<>());
			}
		}
		
		return stacks.get(currentStack);
	}
	
	private void shiftLeft(int stackIndex) {
		if (stackIndex < (stacks.size()-1)) {
			Stack<T> older = stacks.get(stackIndex);
			Stack<T> younger = stacks.get(stackIndex+1);
			if(!younger.isEmpty()) {
				older.push(younger.remove(0));
				shiftLeft(stackIndex+1);
				if (younger.isEmpty()) {
					currentStack--;
				}
			}
			
			
		}
	}
	
	
	public static void main(String[] args) {
		SetOfStacks<Integer> ss = new SetOfStacks<>(3);
		ss.push(1);ss.push(2);ss.push(3);
		ss.push(5);
		System.out.println(ss.popAt(0));
		System.out.println(ss.popAt(0));
		System.out.println(ss.pop());
		System.out.println(ss.popAt(0));
	}
}
