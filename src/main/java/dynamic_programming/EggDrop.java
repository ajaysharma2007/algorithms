package dynamic_programming;

/**
 * Created by ajay on 07/10/16.
 */
public class EggDrop {

    private void eggDrop(int e, int f) {

        int[][] eggFloorArr = new int[e + 1][f + 1];

        /*
            If there are no floors, number of trials will be zero.
            If there is one floor, number of trials will be one.
         */

        for (int i = 0; i < e + 1; i++) {
            eggFloorArr[i][0] = 0;
            eggFloorArr[i][1] = 1;
        }

        /*
            If there is no egg, number of trials will be zero.
            If there is one egg, number of trials will be equal to number of floors in worst case.
         */

        for (int j = 0; j < f + 1; j++) {
            eggFloorArr[0][j] = 0;
            eggFloorArr[1][j] = j;
        }

        for (int i = 2; i < e + 1; i++) {
            for (int j = 2; j < f + 1; j++) {
                eggFloorArr[i][j] = Integer.MAX_VALUE;

                int maxAttempts;
                for (int trialFloor = 1; trialFloor <= j; trialFloor++) {
                    maxAttempts = 1 + max(eggFloorArr[i - 1][trialFloor - 1], eggFloorArr[i][j - trialFloor]);
                    if (maxAttempts < eggFloorArr[i][j]) {
                        eggFloorArr[i][j] = maxAttempts;
                    }
                }
            }
        }
    }

    int max(int a, int b) {
        return a > b ? a : b;
    }

}
