package linkedLists;

public class Intersection {

	public static <T> LinkedListNode<T> intersection(LinkedListNode<T> l1, LinkedListNode<T> l2) {
		// get lenghts of the lists and last nodes
		Object[] lengthAndTailList1 = getLenghtAndTail(l1);
		Object[] lenghtAndTailList2 = getLenghtAndTail(l2);
		int n1 = (int) lengthAndTailList1[0], n2 = (int) lenghtAndTailList2[0];
		@SuppressWarnings("unchecked")
		LinkedListNode<T> lastl1 = (LinkedListNode<T>) lengthAndTailList1[1], lastl2 = (LinkedListNode<T>) lenghtAndTailList2[1];
		
		if(lastl1 != lastl2) return null; // no intersection node found
		
		// trim larger list
		if (n1 > n2) 
			for(;l1!=null && n1 > n2; l1 = l1.next) n1--;
		else
			for(;l2!=null && n2 > n1; l2 = l2.next) n2--;
		assert n1 == n2;

		// look for intersection node
		while(l1 != null && l2 != null && l1 != l2) {
			l1 = l1.next;
			l2 = l2.next;
		}
		
		return l1;
	}
	
	private static <T> Object[] getLenghtAndTail(LinkedListNode<T> list) {
		int n = 0;
		LinkedListNode<T> last = null;
		for(LinkedListNode<T> iter = list; iter!=null; iter = iter.next) {
			n++;
			last = iter;
		}
		return new Object[] {n,last};
	}
	
	public static void main(String[] args) {
		LinkedListNode<Integer> intersection = new LinkedListNode<Integer>(5);intersection.appendToTail(4);intersection.appendToTail(3);
		
		LinkedListNode<Integer> l1 = new LinkedListNode<Integer>(7);l1.appendToTail(6);l1.next = intersection;
		LinkedListNode<Integer> l2 = new LinkedListNode<Integer>(9);l2.appendToTail(8);l2.appendToTail(6);l2.next = intersection;
		
		LinkedListNode<Integer> intersectionRes = intersection(l1, l2);
	
		System.out.println(intersection == intersectionRes);
	}
	
}
