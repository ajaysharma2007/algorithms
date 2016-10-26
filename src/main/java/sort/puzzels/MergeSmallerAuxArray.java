package sort.puzzels;

import java.util.Arrays;

/**
 * Created by ajay on 10/25/16.
 */
public class MergeSmallerAuxArray {
    private int[] sourceArr = new int[]{6, 8, 10, 1, 7, 14};

    public static void main(String[] args) {

        MergeSmallerAuxArray mergeSmallerAuxArray = new MergeSmallerAuxArray();
        mergeSmallerAuxArray.merge();
        Arrays.stream(mergeSmallerAuxArray.sourceArr).map(sortedArrElem -> {
            System.out.print(sortedArrElem + ", ");
            return sortedArrElem;
        }).count();

    }

    private void merge() {
        int[] aux = new int[sourceArr.length / 2];

        for (int i = 0; i < aux.length; i++) {
            aux[i] = sourceArr[i];
        }

        int mid = aux.length;
        int sourceIndex = 0;
        int count = 0;
        while (count < aux.length) {
            if (less(sourceArr[mid], aux[count])) {
                sourceArr[sourceIndex++] = sourceArr[mid++];
            } else {
                sourceArr[sourceIndex++] = aux[count++];
            }
        }
    }

    private static boolean less(int v, int w) {
        return v < w;
    }
}
