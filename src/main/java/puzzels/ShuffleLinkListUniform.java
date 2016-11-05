package puzzels;

/**
 * Created by ajay on 10/27/16.
 */
public class ShuffleLinkListUniform {

    public static void main(String[] args) {
        ShuffleLinkListUniform s = new ShuffleLinkListUniform();
        Node na = s.new Node(1);
        Node nb = s.new Node(2);
        na.next = nb;
        Node nc = s.new Node(3);
        nb.next = nc;
        Node nd = s.new Node(4);
        nc.next = nd;
//        Node ne = s.new Node(5);
//        nd.next = ne;
//        Node nf = s.new Node(6);
//        ne.next = nf;
//        Node ng = s.new Node(7);
//        nf.next = ng;
//        Node nh = s.new Node(8);
//        ng.next = nh;
//        Node nj = s.new Node(9);
//        nh.next = nj;

        System.out.println("Before shuffle:");
        s.shuffle(na, 9);
        System.out.println("After shuffle:");
        s.print(na);
    }

    private void merge(Node lh, Node rh, int leftSize, int rightSize) {
        Node left = lh;
        Node right = rh;

        Node runner = new Node(0);

        int i = 1, j = 1;

        while (i <= leftSize || j <= rightSize) {
            int remainingLeftItems = leftSize - i + 1;
            int remainingRightItems = leftSize - j + 1;

            if (Math.random() < remainingLeftItems /
                    (remainingLeftItems + remainingRightItems)) {
                runner.next = left;
                left = left.next;
                i = i + 1;
            } else {
                runner.next = right;
                right = right.next;
                j = j + 1;
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
        int leftSize = N / 2;
        int rightSize = N - N / 2;
        shuffle(head, N / 2);
        shuffle(rh, N - N / 2);
        merge(head, rh, leftSize, rightSize);
    }

    public void print(Node n) {
        Node curr = n;
        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }
        System.out.println();
    }

    private class Node {
        private int val;
        private Node next;

        Node(int value) {
            this.val = value;
        }
    }

}
