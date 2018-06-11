package sort_search;

import java.util.LinkedList;
import java.util.Queue;

public class SecondSmallest {

    private static class Node {
        int val;
        boolean dir;
        Node left;
        Node right;

        public Node(int v, boolean d) {
            this.val = v;
            this.dir = d;
        }
    }

    public static int secondSmallest(int[] arr, int n) {
        Queue<Node> q = new LinkedList<>();
        Node root=null;
        for(int i=0; i<n; i++) {
            ((LinkedList<Node>) q).push(new Node(arr[i], false));
        }

        while(!q.isEmpty()) {
            int size = q.size();

            if (size == 1) {
                root = q.remove();
                break;
            }

            for(int i = 0; i<size; i+=2) {
                if (i == size-1) {
                    break;
                }
                else {
                    Node n1 = q.remove();
                    Node n2 = q.remove();
                    int smaller;
                    boolean dir;
                    if (n1.val <= n2.val) {
                        smaller = n1.val;
                        dir = false;
                    }
                    else {
                        smaller = n2.val;
                        dir = true;
                    }

                    Node no = new Node(smaller, dir);
                    no.left = n1;
                    no.right = n2;
                    ((LinkedList<Node>) q).push(no);
                }
            }
        }

        int min = root.val;
        int result = Integer.MAX_VALUE;
        while(root != null) {
            if (root.left != null && root.right != null) {
                result = Math.min((root.left.val ^ root.right.val ^ min), result);
            }
            root = root.dir == true ? root.right : root.left;
        }
        return result;
    }


    public static void main(String[] args) {
        int[] arr = {1,3,10,-1,5,15,-8,-3,19,-9};

        int ss = secondSmallest(arr, arr.length);


        System.out.println(ss);
    }
}
