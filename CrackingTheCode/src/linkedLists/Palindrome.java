package linkedLists;

import java.util.Stack;

public class Palindrome {

	
	public static boolean palindrome(LinkedListNode<String> head) {
		Stack<String> firstHalf = new Stack<>();
		LinkedListNode<String> slow = head;
		LinkedListNode<String> fast = head;
		
		while(fast!=null && fast.next!=null) {
			firstHalf.push(slow.data);
			slow = slow.next;
			fast = fast.next.next;
		}
		slow = slow.next;
		
		// comp1 is first node-data-element of the first half
		// slow is first node of the second half
		String comp1 = firstHalf.pop();
		while (slow != null && comp1 != null) {
			if (comp1 != slow.data) return false;
			slow = slow.next;
			comp1 = firstHalf.isEmpty() ? null: firstHalf.pop();
		}
		return slow == null && comp1 == null;
	}
	
	public static void main(String[] args) {
		LinkedListNode<String> head = new LinkedListNode<String>("a");
		head.appendToTail("n");head.appendToTail("a");head.appendToTail("v");head.appendToTail("o");head.appendToTail("l");head.appendToTail("i");
		head.appendToTail("m");head.appendToTail("i");head.appendToTail("l");head.appendToTail("o");head.appendToTail("v");head.appendToTail("a");head.appendToTail("n");head.appendToTail("a");
		System.out.println(palindrome(head));
	}
	
}
