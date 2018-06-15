package stack_queues;


import sun.plugin.dom.exception.InvalidStateException;

public class LinkedList<E> {

    private static class Node<E> {
        private Node next;
        private E data;

        public Node(E data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int cap;

    public LinkedList(int maxCap) {
        cap = maxCap;
    }

    public LinkedList() {
        cap = -1;
    }

    public boolean add(E elem) {
        if (size == cap) {
            throw new InvalidStateException("Queue is full");
        }
        Node _new = new Node(elem);
        if (tail == null) {
            head = tail = _new;
        }
        tail.next = _new;
        tail = _new;
        size++;
        return true;
    }

    public boolean offer(E elem) {
       try {
           add(elem);
       }
       catch (InvalidStateException e) {
           return false;
       }
       return true;
    }

    public E remove() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        Node<E> old = head;
        head = head.next;
        if(size == 1) {
            tail = null;
        }
        size--;
        return old.data;
    }

    public E poll() {
        E elem = null;
        try{
            elem = remove();
        }
        catch (InvalidStateException e) {}
        return elem;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<>();
        queue.add("Matija");
        queue.add("Lukovic");
        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
        }
    }
}
