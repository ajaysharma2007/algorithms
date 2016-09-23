package dynamic_connectivity;

/**
 * Created by ajay on 9/23/16.
 */
public class WeightedQuickUnion {

    private int[] id;

    WeightedQuickUnion(int N) {

        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public static void main(String[] args) {

    }

    private int root(int childIndex) {

        while (id[childIndex] != childIndex) {
            childIndex = id[childIndex];
        }

        return childIndex;
    }

    private boolean isConnected(int nodeIndex1, int nodeIndex2) {

        return root(nodeIndex1) == root(nodeIndex2);
    }

    private void union(int nodeIndex1, int nodeIndex2) {

        int rootIndex1 = root(nodeIndex1);
        int rootIndex2 = root(nodeIndex2);


    }
}
