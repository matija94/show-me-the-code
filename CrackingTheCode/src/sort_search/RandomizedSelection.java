package sort_search;

public class RandomizedSelection {

    public static int findIthSmallest(Integer[] arr, int start, int end, int i) {
        int pivotIndex = QuickSort.radonmizedPartition(arr, start, end);

        int elementsLess = pivotIndex - start + 1;
        if (elementsLess == i) {
            return arr[pivotIndex];
        }
        else if (i < elementsLess) {
            return findIthSmallest(arr, start, pivotIndex-1, i);
        }
        else {
            return findIthSmallest(arr, pivotIndex+1, end, i-elementsLess);
        }
    }


    public static int findIthSmallestIterative(Integer[] arr, int start, int end, int i) {
        int pivotIndex = QuickSort.radonmizedPartition(arr, start, end);

        int elementsLess = pivotIndex - start + 1;

        while(elementsLess != i) {
            if(i < elementsLess) {
                pivotIndex = QuickSort.radonmizedPartition(arr, start, pivotIndex-1);
            }
            else {
                pivotIndex = QuickSort.radonmizedPartition(arr, pivotIndex+1, end);
            }
            elementsLess = pivotIndex - start + 1;
        }
        return arr[pivotIndex];
    }

    public static void main(String[] args) {
        Integer arr[] = {1,10,3,-1,15,-2,19,20,-3};
        int thirdSmallest = findIthSmallestIterative(arr, 0, arr.length-1, 3);
        System.out.println(thirdSmallest);
    }



}
