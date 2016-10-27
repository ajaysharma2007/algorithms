package puzzels;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by ajay on 10/27/16.
 */
public class ShuffleLinkListUniform {
    private class Node {
        private Object item;
        private Node next;
    }

    private void merge(Node lh, Node rh, int leftSize, int rightSize) {
        Node left = lh;
        Node right = rh;

        if (StdRandom.uniform(1) > 0) {
            lh = right;
            right = right.next;
        } else {
            left = left.next;
        }

        Node runner = lh;

        while (right != null || left != null) {
            if (left == null) {
                runner.next = right;
                right = right.next;
            } else if (right == null) {
                runner.next = left;
                left = left.next;
            } else if (StdRandom.uniform(1) > 0) {
                runner.next = right;
                right = right.next;
            } else {
                runner.next = left;
                left = left.next;
            }
            runner = runner.next;
        }
    }

    public void shuffle(Node head, int N) {
        if (N == 1) return;

        int k = 1;
        Node mid = head;
        while (k < N / 2) {
            mid = mid.next;
            k++;
        }
        Node rh = mid.next;
        mid.next = null;
        int leftSize = N/2;
        int rightSize = N - N/2;
        shuffle(head, N / 2);
        shuffle(rh, N - N / 2);
        merge(head, rh, leftSize, rightSize);
    }
}
