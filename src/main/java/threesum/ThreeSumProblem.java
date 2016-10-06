package threesum;

/**
 * Created by ajay on 10/6/16.
 */
public class ThreeSumProblem {
    private static int[] sourceArr = {-3, -2, -1, 0, 1, 2, 3};
    private static int threeSumCount = 0;
    public static void main(String[] args) {
        for (int i = 0; i < sourceArr.length-2; i++) {

            int k = sourceArr.length - 1;
            int j = i + 1;

            while (j < k) {
               if((sourceArr[i] + sourceArr[j] + sourceArr[k]) == 0) {
                   threeSumCount++;
                   j++;
                   k--;
               } else if((sourceArr[i] + sourceArr[j] + sourceArr[k]) > 0) {
                   k--;
               } else {
                   j++;
               }
            }
        }
        System.out.println(threeSumCount);
    }
}
