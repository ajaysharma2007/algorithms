package sort;

import java.util.Arrays;

/**
 * Created by ajay on 09/09/16.
 */

/*
    - Selection sort relies on the logic of finding the minimum value in an array and moving it to correct position after each iteration.
    - It's like:
        - Find the smallest number. Swap it with the first number.
        - Find the second-smallest number. Swap it with the second number.
        - Find the third-smallest number. Swap it with the third number.
        - Repeat finding the next-smallest number, and swapping it into the correct position until the array is sorted.

    - This algorithm is called selection sort because it repeatedly selects the next-smallest element and swaps it into place.

    - The inner loop is called like this:
        - n times for 1st iteration
        - n-1 times for next and so on.
        - Total of this corresponds to n(n+1)/2.

    - swap() runs n times. It's complexity will be linear.
        - 3n

    - selectionSort() does assignment and increment operations only and hence will be linear.
        - Θ(n)

    - Adding up the running times for the three parts:
        - we have Θ(n^2) for the calls to inner loop,
        - Θ(n) for the calls to swap, and
        - Θ(n) for the rest of the loop in selectionSort.
    - The Θ(n^​2) term is the most significant, and so we say that the running time of selection sort is Θ(n^​2).

    - Notice also that no case is particularly good or particularly bad for selection sort.
    - The inner loop will always make n^2 + n/2n iterations, regardless of the input.
    - Therefore, we can say that selection sort runs in Θ(n^​2) time in all cases.

 */

public class SelectionSort {

    private int[] sourceArr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static void main(String[] args) {

        SelectionSort selectionSort = new SelectionSort();
        selectionSort.selectionSort(selectionSort.sourceArr);
        Arrays.stream(selectionSort.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + ", ");
            return sortedArrElem;
        }).count();
    }

    private void selectionSort(int[] unsortedArr) {

        for (int i = 0; i < unsortedArr.length - 1; i++) {

            int minimumIndex = i;

            for (int j = i; j < unsortedArr.length - 1; j++) {
                if (unsortedArr[minimumIndex] > unsortedArr[j + 1]) {
                    minimumIndex = j + 1;
                }
            }

            swapArrayIndexes(i, minimumIndex);
        }
    }

    private void swapArrayIndexes(int index1, int index2) {

        int temp = sourceArr[index1];
        sourceArr[index1] = sourceArr[index2];
        sourceArr[index2] = temp;

    }
}
