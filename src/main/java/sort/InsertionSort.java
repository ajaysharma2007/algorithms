package sort;

import java.util.Arrays;

/**
 * Created by ajay on 13/09/16.
 *
 * <p>
 * In general, if there are k elements before the key and key is smallest of all of them, all k might have to slide over by one position.
 * Rather than counting exactly how many lines of code we need to test an element against a key and slide the element,
 * let's agree that it's a constant number of lines; let's call that constant c.
 * Therefore, it could take up to c.k lines to insert into a sub-array of k elements.
 * <p>
 * Let's suppose that upon every iteration of while loop, the key is less than every element in the sub-array to its left.
 * When we call while loop the first time k=1.
 * The second time, k=2.
 * The third time, k=3.
 * And so on, up through the last time, when k = n-1.
 * Therefore, the total time spent inserting into sorted sub-arrays is c*1+c*2+c*3+⋯c*(n−1)=c*(1+2+3+⋯+(n−1)).
 * <p>
 * c*(n−1+1)((n−1)/2) = cn^​2/2−cn/2 .
 * <p>
 * Using big-Θ notation, we discard the low-order term cn/2cn/2 and the constant factors cc and 1/2,
 * getting the result that the running time of insertion sort, in this case, is Θ(n^​2).
 * <p>
 * A call to while causes every element to slide over if the key being inserted is less than every element to its left.
 * So, if every element is less than every element to its left, the running time of insertion sort is Θ(n^​2).
 * What would it mean for every element to be less than the element to its left?
 * The array would have to start out in reverse sorted order, such as [11, 7, 5, 3, 2].
 * So a reverse-sorted array is the worst case for insertion sort.
 * <p>
 * A call to insert causes no elements to slide over if the key being inserted is greater than or equal to every element to its left.
 * So, if every element is greater than or equal to every element to its left, the running time of insertion sort is Θ(n).
 * This situation occurs if the array starts out already sorted, and so an already-sorted array is the best case for insertion sort.
 * <p>
 * What if you knew that the array was "almost sorted": every element starts out at most some constant number of positions,
 * say 17, from where it's supposed to be when sorted?
 * Then each call to insert slides at most 17 elements, and the time for one call of insert on a sub-array of k elements
 * would be at most 17*c .
 * Over all n-1 calls to insert, the running time would be 17*c*(n−1), which is Θ(n), just like the best case.
 * So insertion sort is fast when given an almost-sorted array.
 * <p>
 * <p>
 * Worst case: Θ(n^2).
 * Best case: Θ(n).
 * Average case for a random array: Θ(n^2).
 * "Almost sorted" case: Θ(n).
 *
 * If you had to make a blanket statement that applies to all cases of insertion sort, you would have to say that it runs in O(n^2) time.
 */
public class InsertionSort {

    private int[] sourceArr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static void main(String[] args) {

        InsertionSort insertionSort = new InsertionSort();
        insertionSort.insertionSort(insertionSort.sourceArr);
        Arrays.stream(insertionSort.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + ", ");
            return sortedArrElem;
        }).count();

    }

    private void insertionSort(int[] unsortedArr) {

        for (int i = 1; i < unsortedArr.length; i++) {

            int key = unsortedArr[i];
            int j = i;
            while (j > 0 && unsortedArr[j - 1] > key) {

                unsortedArr[j] = unsortedArr[j - 1];
                j--;
            }
            unsortedArr[j] = key;
        }
    }
}
