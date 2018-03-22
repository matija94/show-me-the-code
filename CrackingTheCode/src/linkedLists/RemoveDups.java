package linkedLists;

public class RemoveDups {

	// O(N^2) time, O(1) space
	// using hash map provides O(N) time but uses additional space
	public static <T> void removeDups(LinkedListNode<T> node) {
		while (node != null) {
			deleteNode(node, node.data);
			node = node.next;
		}
	}
	
	private static <T> void deleteNode(LinkedListNode<T> start, T data) {
		while(start != null && start.next != null) {
			if (start.next.data.equals(data)) {
				start.next = start.next.next;
			}
			start = start.next;
		}
	}
	
	public static void main(String[] args) {
		LinkedListNode<Integer> head = new LinkedListNode<Integer>(1);
		head.appendToTail(5);
		head.appendToTail(3);
		head.appendToTail(1);
		head.appendToTail(4);
		head.appendToTail(5);
		head.printLinkedList();
		
		removeDups(head);
		head.printLinkedList();
	}
}
