/**
 * Created by ajay on 07/09/16.
 */

package search;

public class BinarySearch {

    private int[] sourceArr = new int[] {1,2,3,4,5,6,7,8,9};
    private int min = 0;
    private int max = sourceArr.length;

    public static void main(String[] args) {

        BinarySearch binarySearch = new BinarySearch();
        int foundIndex = binarySearch.binarySearch(10);
        if (foundIndex == -1) {
            System.out.println("Number not present in the array");
        } else {
            System.out.println("Number found at location : " + foundIndex);
        }
    }

    public int binarySearch(int findNum) {

        while (max > min) {

            int guess = (min + max)/2;

            if (findNum == sourceArr[guess]) {
                return guess;
            } else if (findNum < sourceArr[guess]) {
                max = guess - 1;
            } else if (findNum > sourceArr[guess]) {
                min = guess + 1;
            }
        }

        return -1;
    }
}
