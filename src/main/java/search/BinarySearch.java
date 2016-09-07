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
