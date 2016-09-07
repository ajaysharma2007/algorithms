/**
 * Created by ajay on 07/09/16.
 */

/*
    - max need to be equal to array length - 1
    - And exit condition needs to be: min <= max.
    - Only if both of the above conditions are true only then all indexes in as array will be covered.
 */

/*
    - If we take following condition:
        - max = array length and
        - exit condition => min < max
            - Here we can't have min = max because then if both min and max becomes equal to array length then it will be
            ArrayIndexOutOfBound exception.
        - In this scenario if we take array as [1,2,3,4,5] then only following indexes will be covered:
            - 2, 4, 0
 */

/*
    The logarithm function grows very slowly. Logarithms are the inverse of exponentials, which grow very rapidly, so that if lg n=x, then n = 2^x​ .
    For example, because lg 128 = 7, we know that 2^7 = 128.

    When n(number of elements in an array) is not a power of 2, we can just go up to the next higher power of 2.
    For an array whose length is 1000, the next higher power of 2 is 1024, which equals 2^10.
    Therefore, for a 1000-element array, binary search would require at most 11 (10 + 1) guesses.
 */

package search;

import java.util.Arrays;

public class BinarySearch {

    private static int numberOfChances = 0;

    private int[] sourceArr = new int[] {1,2,3,4,5,6,7,8,9};
    private int min = 0;
    private int max = sourceArr.length - 1;

    public static void main(String[] args) {

        BinarySearch binarySearch = new BinarySearch();
        Arrays.stream(binarySearch.sourceArr).map(arrayNum -> {
            numberOfChances = 0;
            binarySearch.min = 0;
            binarySearch.max = binarySearch.sourceArr.length - 1;
            int foundIndex = binarySearch.binarySearch(arrayNum);
            if (foundIndex == -1) {
                System.out.println("Number not present in the array");
            } else {
                System.out.println("Number found at location : " + foundIndex);
            }
            System.out.println("Number of searches performed : " + numberOfChances);
            return arrayNum;
        }).count();

    }

    public int binarySearch(int findNum) {

        while (max >= min) {

            numberOfChances++;

            int guess = (min + max)/2;

            if (findNum == sourceArr[guess]) {
                return guess;
            } else if (findNum < sourceArr[guess]) {
                max = guess - 1;
            } else {
                min = guess + 1;
            }
        }

        return -1;
    }
}
