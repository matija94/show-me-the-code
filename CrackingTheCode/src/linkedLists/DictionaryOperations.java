package linkedLists;

public class DictionaryOperations<E> {


    private static class Node<E> {
        E key;
        Node next;

        public Node(E key, Node next) {
            this.key = key;
            this.next = next;
        }
    }

    private Node nil;

    public DictionaryOperations() {
        nil = new Node(null, null);
    }

    public void insert(E key) {
        Node newNode = new Node(key,nil.next);
        if (nil.next == null) {
            newNode.next = nil;
        }
        nil.next = newNode;
    }

    public Node search(E key) {
        nil.key = key;
        Node current = nil.next;
        while (key != current.key) {
            current = current.next;
        }
        nil.key = null;
        if (current == nil) {
            return null;
        }
        return current;
    }

    public void delete(E key) {
        Node current = nil;
        while (current.next != nil) {
            if (current.next.key == key) {
                current.next = current.next.next;
            }
            else {
                current = current.next;
            }
        }
    }


    public void print() {
        Node current = nil.next;
        while(current != nil) {
            if (current != null) {
                System.out.print(current.key);
                if (current.next != nil) {
                    System.out.print(" --> ");
                }
                current = current.next;
            }
            else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        DictionaryOperations ops = new DictionaryOperations();
        ops.insert("Matija");
        ops.insert("Lukovic");
        ops.insert("Ckilima");
        ops.insert("Lukovski");

        ops.search("brt");



        ops.delete("brt");

        ops.delete("Ckilima");

        ops.print();

    }

}
