package bitonic;

/**
 * Created by ajay on 10/6/16.
 */
public class BitonicSequenceSearch {

    public static void main(String[] args) {
        int[] sourceArr = {2, 10, 7, 5, 3, 1, 0};
        int findNum = -4;

        boolean isPresent = new BitonicSequenceSearch().bitonicSearch(findNum, sourceArr);
        System.out.format("Is %d present in the given array ?   : %s%n", findNum, isPresent);
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
        int result = searchResult == -1 ? reverseBinarySearch(findNum, mid + 1, sourceArr.length - 1, sourceArr) : searchResult;

        result = search(findNum, mid , sourceArr);

        if (result != -1) {
            return true;
        }

        return false;
    }

    private int search(int findNum, int midIndex, int[] sourceArr) {
        int leftLow = 0;
        int leftHigh = midIndex;

        int rightLow = midIndex + 1;
        int rightHigh = sourceArr.length - 1;

        while (leftLow <= leftHigh || rightLow <= rightHigh) {
            int leftMid = (leftLow + leftHigh) / 2;
            int rightMid = (rightLow + rightHigh) / 2;

            if (sourceArr[leftMid] < findNum) {
                leftLow = leftMid + 1;
            } else if (sourceArr[leftMid] > findNum) {
                leftHigh = leftMid - 1;
            } else {
                return leftMid;
            }

            if (sourceArr[rightMid] > findNum) {
                rightLow = rightMid + 1;
            } else if (sourceArr[rightMid] < findNum) {
                rightHigh = rightMid - 1;
            } else {
                return rightMid;
            }
        }
        return -1;
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
