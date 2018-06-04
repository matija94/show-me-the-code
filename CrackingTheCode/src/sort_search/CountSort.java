package sort_search;


public class CountSort {


    public static void sort(int[] ints) {
        int max = Integer.MIN_VALUE;
        for (int i : ints) {
            max = Math.max(i,max);
        }
        int[] eCnt = new int[max+1];
        for (int i : ints) {
            eCnt[i]++;
        }
        for(int i = 1; i<=max; i++) {
            eCnt[i] += eCnt[i-1];
        }
        int[] sorted = new int[ints.length];
        for (int i = 0;i<ints.length; i++){
            sorted[eCnt[ints[i]]-1] = ints[i]; // eCount stores integers(1 based) representing count of numbers which are <= than given number. We subtract the computed value by 1(to get index 0 based integer) and store the given number at the new index.
            eCnt[ints[i]]--;
        }

        for(int i=0; i<ints.length; i++) {
            ints[i]=sorted[i];
        }
    }

    public static void main(String[] args) {
        int[] ints = {2,5,3,0,2,3,0,3};
        sort(ints);
        for(int i : ints) {
            System.out.println(i);
        }
    }

    // 1 4 7 9 10 15 20
    // 1 5 12

}
