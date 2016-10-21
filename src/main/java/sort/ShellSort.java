package sort;

import java.util.Arrays;

/**
 * Created by ajay on 21/10/16.
 */
public class ShellSort {
    private Integer[] sourceArr = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static void main(String[] args) {

        ShellSort shellSort = new ShellSort();
        shellSort.shellSort(shellSort.sourceArr);
        Arrays.stream(shellSort.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + ", ");
            return sortedArrElem;
        }).count();

    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void swap(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private void shellSort(Integer[] unsortedArr) {
        int h = 1;
        int arrLength = unsortedArr.length;
        while (h < arrLength / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < arrLength; i++) {
                for (int j = i; j < arrLength; j += h) {
                    if (less(j, j - h)) {
                        swap(unsortedArr, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }
}
