package linkedLists;

public class RemoveInputNode {

	
	public static <T> boolean removeNode(LinkedListNode<T> node) {
		if (node == null || node.next == null) return false;
		LinkedListNode<T> next = node.next;
		node.data = next.data;
		node.next = next.next;
		return true;
	}
}
