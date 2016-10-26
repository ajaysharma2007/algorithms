package sort;

import java.util.Arrays;

/**
 * Created by ajay on 21/10/16.
 */
public class MergeSortCoursera {
    private Integer[] sourceArr = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
    private int inversions = 0;

    public static void main(String[] args) {

        MergeSortCoursera mergeSortCoursera = new MergeSortCoursera();
        mergeSortCoursera.mergeSort(mergeSortCoursera.sourceArr);
        Arrays.stream(mergeSortCoursera.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + ", ");
            return sortedArrElem;
        }).count();

        System.out.println("Number of inversion is : " + mergeSortCoursera.inversions);

    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void mergeSort(Comparable[] unsortedArr) {

        Integer[] aux = new Integer[unsortedArr.length];
        int low = 0;
        int high = unsortedArr.length - 1;

        sort(unsortedArr, aux, low, high);
    }

    private void merge(Comparable[] sourceArray, Comparable[] auxArr, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            auxArr[k] = sourceArray[k];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {

            if (i > mid) {
                sourceArray[k] = auxArr[j++];
            } else if (j > high) {
                sourceArray[k] = auxArr[i++];
            } else if (less(auxArr[j], auxArr[i])) {
                inversions = inversions + (mid + 1 - i);
                sourceArray[k] = auxArr[j++];
            } else {
                sourceArray[k] = auxArr[i++];
            }
        }
    }
}
