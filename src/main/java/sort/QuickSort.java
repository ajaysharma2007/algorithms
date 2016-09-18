package sort;

import java.util.Arrays;

/**
 * Created by ajay on 18/09/16.
 */

/*
    - Partitioning a sub-array with nn elements takes Θ(n) time. Each element A[j] is compared once with the pivot.
        A[j] may or may not be swapped with A[q], q may or may not be incremented, and j is always incremented.
        The total number of lines executed per element of the sub-array is a constant. Since the sub-array has
        n elements, the time to partition is Θ(n): linear-time partitioning.

    - When quicksort always has the most unbalanced partitions possible, then the original call takes cn time for
        some constant cc, the recursive call on n−1 elements takes c(n−1) time, the recursive call on n−2 elements
        takes c(n-2)c(n−2) time, and so on. Here's a tree of the subproblem sizes with their partitioning times:
            https://cdn.kastatic.org/ka-perseus-images/7da2ac32779bef669a6f05decb62f219a9132158.png

        When we total up the partitioning times for each level, we get:
            cn+c(n−1)+c(n−2)+⋯+2c=c(n+(n−1)+(n−2)+⋯+2)=c((n+1)(n/2)−1)

        The last line is because 1+2+3+⋯+n is the arithmetic series, as we saw when we analyzed selection sort.
        (We subtract 1 because for quicksort, the summation starts at 2, not 1.) We have some low-order terms and
        constant coefficients, but when we use big-Θ notation, we ignore them. In big-Θ notation, quicksort's
        worst-case running time is Θ(n^​2).

    - Quicksort's best case occurs when the partitions are as evenly balanced as possible: their sizes either are equal
        or are within 1 of each other.

        - In either of these cases, each partition has at most n/2 elements, and the tree of sub-problem sizes looks a
            lot like the tree of sub-problem sizes for merge sort, with the partitioning times looking like the merging
            times:
                https://cdn.kastatic.org/ka-perseus-images/21cd0d70813845d67fbb11496458214f90ad7cb8.png

        Using big-Θ notation, we get the same result as for merge sort: Θ(n*lg(n)).

    -  First, let's imagine that we don't always get evenly balanced partitions, but that we always get at worst a
        3-to-1 split. That is, imagine that each time we partition, one side gets 3n/4 elements and the other side
        gets n/4. Then the tree of sub-problem sizes and partitioning times would look like this:
            https://cdn.kastatic.org/ka-perseus-images/0afd6362c5866d2fc338a8aa9fb65fc2455f6792.png
        The left child of each node represents a sub-problem size 1/4 as large, and the right child represents a
        sub-problem size 3/4 as large. Since the smaller sub-problems are on the left, by following a path of left
        children, we get from the root down to a sub-problem size of 1 faster than along any other path.

        It might be easiest to think in terms of starting with a sub-problem size of 1 and multiplying it by 4 until
        we reach n. In other words, we're asking for what value of x is 4^x = n? The answer is log​ n/log 4.

        Since each right child is 3/4 of the size of the node above it (its parent node), each parent is 4/3 times the
        size of its right child. Let's again think of starting with a sub-problem of size 1 and multiplying the size by
        4/3 until we reach nn. For what value of x is (4/3)^​x​ = n? The answer is log(n)/log(​4/3).

        Altogether, there are log(n)/log(​4/3) levels, and so the total partitioning time is O(n*log(n)/log(4/3)).

        and so log(n)/log(​4/3) and lg(n) differ by only a factor of log(​4/3), which is a constant. Since constant
        factors don't matter when we use big-O notation, we can say that if all the splits are 3-to-1, then
        quicksort's running time is O(n*lg(n)), albeit with a larger hidden constant factor than the best-case running
        time.
 */
public class QuickSort {

    private int[] sourceArr = new int[]{9, 8, 7, 6};

    public static void main(String[] args) {

        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(quickSort.sourceArr, 0, quickSort.sourceArr.length - 1);
        Arrays.stream(quickSort.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + " ");
            return sortedArrElem;
        }).count();
    }

    private void quickSort(int[] unsortedArr, int startIndex, int endIndex) {

        if (startIndex < endIndex) {

            int pivotIndex = partition(unsortedArr, startIndex, endIndex);
            quickSort(unsortedArr, startIndex, pivotIndex-1);
            quickSort(unsortedArr, pivotIndex+1, endIndex);
        }
    }

    private int partition(int[] unsortedArr, int startIndex, int endIndex) {

        int q = startIndex;

        for (int j = startIndex; j < endIndex; j++) {

            if(unsortedArr[j] <= unsortedArr[endIndex]) {
                swapArrayIndexes(unsortedArr, q, j);
                q++;
            }
        }
        swapArrayIndexes(unsortedArr, endIndex, q);
        return q;
    }

    private void swapArrayIndexes(int[] unsortedArr, int index1, int index2) {

        int temp = unsortedArr[index1];
        unsortedArr[index1] = unsortedArr[index2];
        unsortedArr[index2] = temp;

    }

}
