package stack_queues;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class TwoStacks<E> {

    private ArrayList<E> data = new ArrayList<>();
    private int sizeFirst=0;
    private int sizeSecond=0;

    private byte first = 0;
    private byte second = 1;

    public TwoStacks(int capacity) {
        for(int i=0; i<capacity; i++) {
            data.add(null);
        }
    }

    public void pushFirst(E elem) {
        push(first, elem);
        sizeFirst++;
    }

    public void pushSecond(E elem) {
        push(second, elem);
        sizeSecond++;
    }

    public E popFirst() {
        E elem = pop(first);
        sizeFirst--;
        return elem;
    }

    public E popSecond() {
        E elem =  pop(second);
        sizeSecond--;
        return elem;
    }

    public int getSizeFirst() {
        return sizeFirst;
    }

    public int getSizeSecond() {
        return sizeSecond;
    }

    private void push(byte stack, E elem) {
        int start = getStart(stack);
        if (data.get(start) != null) {
            throw new StackOverflowError(String.format("Stack %d overflow", stack));
        }
        data.set(start,elem);
    }

    private int getStart(byte stack) {
        int start = (stack == first) ?
                sizeFirst :
                data.size() - sizeSecond-1;

        return start;
    }

    private E pop(byte stack) {
        int start = getStart(stack);
        if (start == 0 || start == data.size()-1) {
            throw new EmptyStackException();
        }
        start = stack == first ? start-1 : start+1;
        E elem = data.get(start);
        if (elem == null) {
            throw new EmptyStackException();
        }
        data.set(start, null);
        return elem;
    }


    public static void main(String args[]) {
        TwoStacks<String> strStacks = new TwoStacks<>(10);


        strStacks.pushFirst("Matija");
        strStacks.pushSecond("Lukovic");

        System.out.println(strStacks.popFirst());
        strStacks.pushFirst("Ckily");
    }
}
