package recursion;

/**
 * Created by ajay on 13/09/16.
 */

/*
    - Each recursive call should be on a smaller instance of the same problem, that is, a smaller subproblem.
    - The recursive calls must eventually reach a base case, which is solved without further recursion.
 */
public class Factorial {

    public static void main(String[] args) {
        System.out.println(getFactorial(5));
    }

    private static int getFactorial(int n) {

        if (n == 0) {
            return 1;
        }
        return n * getFactorial(n - 1);
    }
}
