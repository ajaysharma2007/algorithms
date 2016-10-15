package queue;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * Created by ajay on 15/10/16.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        String[] stringInputs = StdIn.readAllStrings();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        if (stringInputs != null) {
            if (stringInputs.length < k) {
                throw new IllegalArgumentException("k can't be less " +
                        "than number of input strings.");
            }
            for (String input : stringInputs) {
                randomizedQueue.enqueue(input);
            }

            Iterator<String> randomizedQueueIterator = randomizedQueue.iterator();

            for (int j = 0; j < k; j++) {
                System.out.println(randomizedQueueIterator.next());
            }
        }
    }
}
