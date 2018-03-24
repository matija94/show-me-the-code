package linkedLists;

public class SumLists {

	
	public static LinkedListNode<Integer> sumBackwards(LinkedListNode<Integer> a, LinkedListNode<Integer> b) {
		
		LinkedListNode<Integer> iterator_a = a;
		LinkedListNode<Integer> iterator_b = b;
		
		LinkedListNode<Integer> res = null;
		
		int followUp = 0;
		while (iterator_a != null || iterator_b != null) {
			int aData = iterator_a != null ? iterator_a.data : 0;
			int bData = iterator_b != null ? iterator_b.data : 0;
			
			int sum = aData + bData;
			if (sum+followUp < 10) {
				res = add(res, sum+followUp);
				followUp=0;
			}
			else {
				res= add(res, (sum-10)+followUp);
				followUp = 1;
			}
			iterator_a = iterator_a.next;
			iterator_b = iterator_b.next;
		}
		if (followUp != 0) {
			res = add(res, followUp);
		}
		
		return res;
		
	}
	
	
	public static LinkedListNode<Integer> sumForward(LinkedListNode<Integer> a, LinkedListNode<Integer> b) {
		// TODO: check lenghts of the lists and pad shorter one with zeros
		
		PartialSum sumForwardLists = sumForwardLists(a, b);
		if (sumForwardLists.carry == 0) {
			return sumForwardLists.sum;
		}
		else {
			return addBefore(sumForwardLists.sum, sumForwardLists.carry);
		}
	}
	
	private static class PartialSum {
		LinkedListNode<Integer> sum = null;
		int carry = 0;
	}

	private static LinkedListNode<Integer> addBefore(LinkedListNode<Integer> list, int data) {
		LinkedListNode<Integer> node = new LinkedListNode<Integer>(data);
		if (list != null) {
			node.next = list;
		}
		return node;
	}
	
	private static PartialSum sumForwardLists(LinkedListNode<Integer> a, LinkedListNode<Integer> b) {
		if (a == null && b == null) {
			return new PartialSum();
		}
		
		PartialSum sum = sumForwardLists(a.next, b.next);
		int value = a.data + b.data;
		if (value+sum.carry < 10) {
			sum.sum = addBefore(sum.sum, value+sum.carry);
			sum.carry = 0;
		}
		else {
			sum.sum = addBefore(sum.sum, (value-10)+sum.carry);
			sum.carry = 1;
		}
		return sum;
	}
 	
	
	private static LinkedListNode<Integer> add(LinkedListNode<Integer> res, int data) {
		if (res == null) {
			res = new LinkedListNode<Integer>(data);
		}
		else {
			res.appendToTail(data);
		}
		return res;
	}
	
	
	
	
	public static void main(String[] args) {
		LinkedListNode<Integer> a = new LinkedListNode<Integer>(7);
		a.appendToTail(6);
		a.appendToTail(1);
	
		LinkedListNode<Integer> b = new LinkedListNode<Integer>(5);
		b.appendToTail(3);
		b.appendToTail(9);
		
		sumForward(a, b).printLinkedList();
		
	}
}
