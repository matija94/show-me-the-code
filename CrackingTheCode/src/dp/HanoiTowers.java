package dp;

import java.util.Stack;

public class HanoiTowers {

	static class Tower {
		private Stack<Integer> s;
		
		Tower() {
			s = new Stack<>();
		}
		
		 void add(int i) {
			 if (!s.isEmpty() && s.peek() <= i) {
				 throw new IllegalArgumentException("bigger than top");
			 }
			 s.add(i);
		 }
		 
		 void moveDisks(int n, Tower destination, Tower buffer) {
			 if (n > 0) {
				 moveDisks(n-1, buffer, destination);
				 moveTop(destination);
				 buffer.moveDisks(n-1, destination, this);
			 }
		 }
		 
		 void moveTop(Tower destination) {
			 destination.add(s.pop());
		 }
		 
		
		public String toString() {
			return s.toString();
		}
	}
	
	public static void main(String[] args) {
		Tower t1 = new Tower();
		Tower t2 = new Tower();
		Tower t3 = new Tower();
		int n= 5;
		for(int i=n; i>0;i--) {
			t1.add(i);
		}
		t1.moveDisks(n, t3, t2);
		System.out.println(t3);
	}
	
}
