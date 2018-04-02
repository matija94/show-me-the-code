package linkedLists;

public class LoopDetect {

	public static <T> LinkedListNode<T> loopStart(LinkedListNode<T> head) {
		LinkedListNode<T> slow = head, fast = head;
		
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				break; // meeting point
			}
		}
		
		if (fast == null) {
			return null;
		}
		
		slow = head;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return slow;
	}
	
}
