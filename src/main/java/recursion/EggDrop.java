package recursion;

/**
 * Created by ajay on 07/10/16.
 */
public class EggDrop {

    private int eggDrop(int e, int f) {
        if (f == 0 || f == 1 || e == 1) {
            return f;
        }

        int min = Integer.MAX_VALUE;
        for (int trialFloor = 1; trialFloor <= f; trialFloor++) {
            int res = max(eggDrop(e - 1, trialFloor - 1), eggDrop(e, f - trialFloor));
            if (res < min) {
                min = res;
            }
        }

        return min + 1;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    public static void main(String[] args) {
        EggDrop eggDrop = new EggDrop();
        System.out.println(eggDrop.eggDrop(2, 100));
    }
}
