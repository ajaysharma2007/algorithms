package bitonic;

/**
 * Created by ajay on 10/6/16.
 */
public class BitonicSequenceSearch {

    public static void main(String[] args) {
        int[] sourceArr = {2, 10, 7, 5, 3, 1, 0};
        int findNum = 0;

        boolean isPresent = new BitonicSequenceSearch().bitonicSearch(findNum, sourceArr);
        System.out.format("Number is present in the given array %s%n", isPresent);
    }

    public boolean bitonicSearch(int findNum, int[] sourceArr) {
        int low = 0;
        int high = sourceArr.length - 1;
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (sourceArr[mid - 1] < sourceArr[mid] && sourceArr[mid] > sourceArr[mid + 1]) {
                break;
            } else if (sourceArr[mid - 1] < sourceArr[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        int searchResult = binarySearch(findNum, 0, mid, sourceArr);
        int result =  searchResult == -1 ? reverseBinarySearch(findNum, mid + 1, sourceArr.length - 1, sourceArr)  : searchResult;

        if (result != -1) {
            return true;
        }

        return false;
    }

    private int binarySearch(int findNum, int startIndex, int endIndex, int[] sourceArr) {
        int low = startIndex;
        int high = endIndex;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (sourceArr[mid] < findNum) {
                low = mid + 1;
            } else if (sourceArr[mid] > findNum) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private int reverseBinarySearch(int findNum, int startIndex, int endIndex, int[] sourceArr) {
        int low = startIndex;
        int high = endIndex;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (sourceArr[mid] < findNum) {
                high = mid - 1;
            } else if (sourceArr[mid] > findNum) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
