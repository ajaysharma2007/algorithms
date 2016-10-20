package queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * Created by ajay on 15/10/16.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] stringInputs = StdIn.readAllStrings();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        if (stringInputs != null) {
            if (stringInputs.length < k) {
                throw new IllegalArgumentException("k can't be less " +
                        "than number of input strings.");
            }

            int stringInputsLength = stringInputs.length;
            for (int j = 0; j < k; j++) {
                int lastIndex = stringInputsLength - j - 1;
                int randomNum = StdRandom.uniform(lastIndex+1);
                randomizedQueue.enqueue(stringInputs[randomNum]);
                swapArrayIndexes(stringInputs, randomNum, lastIndex);
                stringInputs[lastIndex] = null;

            }

            Iterator<String> randomizedQueueIterator = randomizedQueue.iterator();
            while (randomizedQueueIterator.hasNext()) {
                System.out.println(randomizedQueueIterator.next());
            }
        }
    }

    private static void swapArrayIndexes(String[] localItems,
                                         int index1, int index2) {
        String tempItem = localItems[index1];
        localItems[index1] = localItems[index2];
        localItems[index2] = tempItem;
    }
}
