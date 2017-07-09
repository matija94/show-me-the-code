package android.matija.com.audisrbija;

/**
 * Created by matija on 1.2.17..
 */

public class MyMutableInt {
    private int integer;


    public MyMutableInt(int i) {
        this.integer = i;
    }


    public int getInteger() {
        return integer;
    }

    public void setInteger(int i) {
        this.integer = i;
    }

    public void increment() {
        integer++;
    }

    public void decrement() {
        integer--;
    }


}
