package linkedLists;

public class KthToLast {

	
	// k-th element to last
	public static <T> LinkedListNode<T> kthToLast(LinkedListNode<T> head, int k) {
		LinkedListNode<T> iterator=head;
		int length= 0;
		while(iterator != null) {
			length++;
			iterator = iterator.next;
		}
		
		int index = 1, target = length-k;
		iterator = head;
		for (; index < target && iterator != null; iterator = iterator.next) {
			index++;
		}
		if (target != index) {
			return null;
		}
		return iterator;
	}
	
	public static <T> int printKthToLast(LinkedListNode<T> head, int k) {
		if (head == null) {
			return 0;
		}
		int index = printKthToLast(head.next, k);
		if (index == k) {
			System.out.println("K-th to last = " + head.data);
		}
		return index+1;
		
	}

	public static void main(String[] args) {
		LinkedListNode<Integer> head = new LinkedListNode<Integer>(1);
		head.appendToTail(2);
		head.appendToTail(3);
		head.appendToTail(4);
		head.appendToTail(5);
	
	
		System.out.println(kthToLast(head, 1).data);
		printKthToLast(head, 1);
	}
}
