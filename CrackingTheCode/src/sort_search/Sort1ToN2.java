package sort_search;

public class Sort1ToN2 {

    // sort the data using RadixSort in O(n) time. To achieve this time complexity we will convert base 10 numbers to
    // be base n
    public static void sort(int data[], int n) {
        radixSort(data, n, 1);

        radixSort(data, n, n);
    }

    private static void radixSort(int[] data, int n, int exp) {
        int count[] = new int[n];
        int output[] = new int[n];

        // get counts
        for(int d : data) {
            count[(d/exp)%n]++;
        }

        // get actual positioning
        for(int i=1; i<n; i++) {
            count[i] += count[i-1];
        }

        for(int i = n-1; i>=0; i--) {
            output[ count[data[i]/exp%n]-1 ] = data[i];
            count[data[i]/exp%n]--;
        }

        for(int i=0; i<n; i++) {
            data[i] = output[i];
        }


    }

    public static void main(String[] args) {
        int data[] = {5,1,13,15};
        int n = 4;

        System.out.println("Input array: ");
        for (int d : data) {
            System.out.print(d + " ");
        }

        sort(data, n);

        System.out.println("\nOutput array: ");
        for (int d : data) {
            System.out.print(d + " ");
        }

    }
}
