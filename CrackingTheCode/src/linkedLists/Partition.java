package linkedLists;

public class Partition {

	
	public static void partition(LinkedListNode<Integer> head, Integer partitionEle) {
		LinkedListNode<Integer> before = null;
		LinkedListNode<Integer> after = null;
		
		LinkedListNode<Integer> iterator = head;
		while(iterator!=null) {
			if (iterator.data < partitionEle) {
				add(before, iterator.data);
			}
			else {
				add(after, iterator.data);
			}
			iterator = iterator.next;
		}
	}
	
	public static void add(LinkedListNode<Integer> head, Integer data) {
		if (head == null) {
			head = new LinkedListNode<Integer>(data); 
		}
		else {
			head.appendToTail(data);
		}
	}
	
}
