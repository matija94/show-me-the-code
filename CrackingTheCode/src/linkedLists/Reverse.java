package linkedLists;

public class Reverse {

	
	public static <T> LinkedListNode<T> reverse(LinkedListNode<T> root) {
		LinkedListNode<T> prev = null;
		LinkedListNode<T> current = root;
		while(current != null) {
			LinkedListNode<T> next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		return prev;
	}
	
	
	public static void main(String[] args) {
		LinkedListNode<Integer> root = new LinkedListNode<Integer>(1);
		root.next = new LinkedListNode<Integer>(2);
		root.next.next = new LinkedListNode<Integer>(3);
		root.next.next.next = new LinkedListNode<Integer>(4);
		
		
		root.printLinkedList();
		root = reverse(root);
		root.printLinkedList();
	}
	
}
