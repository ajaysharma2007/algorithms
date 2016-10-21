package sort;

import java.util.Arrays;

/**
 * Created by ajay on 16/09/16.
 */

/*
    - The divide step takes constant time, regardless of the sub-array size. After all, the divide step just computes the midpoint q of the indices p and r.
        Recall that in big-Θ notation, we indicate constant time by Θ(1).

    - The conquer step, where we recursively sort two sub-arrays of approximately n/2 elements each, takes some amount of time, but we'll account for that time
        when we consider the sub-problems.

    - The combine step merges a total of nn elements, taking Θ(n) time.
        - Each element is copied from array into either lowHalf or highHalf exactly one time in step 1.
        - Each comparison in step 2 takes constant time, since it compares just two elements, and each element "wins" a comparison at most one time.
        - Each element is copied back into array exactly one time in steps 2 and 3 combined.
        - Since we execute each line of code a constant number of times per element and we assume that the sub-array array[p..q] contains nn elements,
            the running time for merging is indeed Θ(n).

    - To keep things reasonably simple, let's assume that if n>1, then n is always even, so that when we need to think about n/2, it's an integer.
        (Accounting for the case in which n is odd doesn't change the result in terms of big-Θ notation.) So now we can think of the running time of mergeSort
        on an n-element sub-array as being the sum of twice the running time of mergeSort on an (n/2)-element sub-array (for the conquer step) plus
        cn (for the divide and combine steps—really for just the merging).

        As the sub-problems get smaller, the number of sub-problems doubles at each "level" of the recursion, but the merging time halves. The doubling and
        halving cancel each other out, and so the total merging time is cn at each level of recursion.

         Eventually, we get down to sub-problems of size 1: the base case. We have to spend Θ(1) time to sort sub-arrays of size 1, because we have to test
         whether p < r, and this test takes time. How many sub-arrays of size 1 are there? Since we started with nn elements, there must be n of them.
         Since each base case takes Θ(1) time, let's say that altogether, the base cases take cn time.

         https://cdn.kastatic.org/ka-cs-algorithms/merge_sort_tree_4.png

         The total time for mergeSort is the sum of the merging times for all the levels. If there are l levels in the tree, then the total merging time is
         l*cn. So what is l? We start with sub-problems of size n and repeatedly halve until we get down to sub-problems of size 1. We saw this characteristic
         when we analyzed binary search, and the answer is l=lg(n)+1. For example, if n=8, then lg(n)+1=4, and sure enough, the tree has four levels: n = 8, 4, 2, 1.
         The total time for mergeSort, then, is c*n(lg(n)+1). When we use big-Θ notation to describe this running time, we can discard the low-order term (+1) and
         the constant coefficient (c), giving us a running time of Θ(n*lg(n)), as promised.

         One other thing about merge sort is worth noting. During merging, it makes a copy of the entire array being sorted, with one half in lowHalf and the
         other half in highHalf. Because it copies more than a constant number of elements at some time, we say that merge sort does not work in place.
         By contrast, both selection sort and insertion sort do work in place, since they never make a copy of more than a constant number of array elements at any
         one time. Computer scientists like to consider whether an algorithm works in place, because there are some systems where space is at a premium,
         and thus in-place algorithms are preferred.

        Algorithm       Worst-case running time	  Best-case running time	 Average-case running time

        Selection sort      Θ(n^2)	                  Θ(n^​2)	                 Θ(n^2)
        Insertion sort      Θ(n^2)	                  Θ(n)                       Θ(n^​2)
        Merge sort	        Θ(n lg(n))                Θ(n lg(n))                 Θ(n lg(n))
        Quick sort          Θ(n^2)                    Θ(n lg(n))                 Θ(n lg(n))

 */
public class MergeSort {

    private int[] sourceArr = new int[]{9, 8, 7, 6};

    public static void main(String[] args) {

        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(mergeSort.sourceArr, 0, mergeSort.sourceArr.length - 1);
        Arrays.stream(mergeSort.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + " ");
            return sortedArrElem;
        }).count();
    }

    private void mergeSort(int[] unsortedArr, int startIndex, int endIndex) {

        if (startIndex < endIndex) {

            int middleIndex = (startIndex + endIndex) / 2;
            mergeSort(unsortedArr, startIndex, middleIndex);
            mergeSort(unsortedArr, middleIndex + 1, endIndex);
            mergeSubArrays(unsortedArr, startIndex, endIndex);
        }
    }

    private void mergeSubArrays(int[] unsortedArr, int startIndex, int endIndex) {

        int middleIndex = (startIndex + endIndex) / 2;

        int[] lowerArr = new int[middleIndex - startIndex + 1];
        int[] higherArr = new int[endIndex - middleIndex];

        for (int i = startIndex; i <= middleIndex; i++) {
            lowerArr[i - startIndex] = unsortedArr[i];
        }

        for (int i = middleIndex + 1; i <= endIndex; i++) {
            higherArr[i - middleIndex - 1] = unsortedArr[i];
        }

        int i = 0;
        int j = 0;
        int k = startIndex;
        while (i < lowerArr.length && j < higherArr.length) {

            if (lowerArr[i] < higherArr[j]) {
                unsortedArr[k] = lowerArr[i];
                i++;
            } else {
                unsortedArr[k] = higherArr[j];
                j++;
            }

            k++;
        }

        while (i < lowerArr.length) {
            unsortedArr[k] = lowerArr[i];
            i++;
            k++;
        }

        while (j < higherArr.length) {
            unsortedArr[k] = higherArr[j];
            j++;
            k++;
        }
    }
}
