package linkedLists;

public class LinkedListNode<T> {

	
	LinkedListNode<T> next = null;
	T data;
	
	public LinkedListNode(T data) {
		this.data = data;
	}
	
	void appendToTail(T data) {
		LinkedListNode<T> end = new LinkedListNode<T>(data);
		LinkedListNode<T> current = this;
		while (current.next != null) {
			current = current.next;
		}
		current.next = end;
	}
	
	void printLinkedList() {
		LinkedListNode<T> current = this;
		StringBuffer sb = new StringBuffer();
		while(current != null) {
			sb.append(current.data);
			sb.append("-->");
			current = current.next;
		}
		sb.delete(sb.length()-3, sb.length());
		System.out.println(sb.toString());
	}
}
