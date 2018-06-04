package sort_search;

public class RadixSort {

    public static void sortInts(int[] ints, int d) {
        while(d > 0) {
            sortAccordingToDigitAtIndex(ints, d-1);
            d--;
        }
    }

    private static void sortAccordingToDigitAtIndex(int[] ints, int digitIndex) {
        int[] buffer = new int[10];
        for(int num : ints) {
            int digit = getDigitAtIndex(num, digitIndex);
            buffer[digit]++;
        }
        for(int i=1; i<10; i++) {
            buffer[i] = buffer[i] + buffer[i-1];
        }

        int[] sorted = new int[ints.length];
        for(int i = ints.length-1; i>=0; i--) {
            int num = ints[i];
            sorted[buffer[getDigitAtIndex(num, digitIndex)]-1] = num;
            buffer[getDigitAtIndex(num, digitIndex)]--;
        }
        for(int i=0; i<ints.length; i++) {
            ints[i]=sorted[i];
        }
    }

    private static int getDigitAtIndex(int number, int digitIndex) {
        return (int) Integer.toString(number).toCharArray()[digitIndex] - 48;
    }

    public static void main(String[] args) {
        int[] ints = {329, 457, 657, 839, 436, 720, 355};
        sortInts(ints, 3);
        for(int i : ints) {
            System.out.println(i);
        }
    }
}
