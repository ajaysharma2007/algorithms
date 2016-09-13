package recursion;

/**
 * Created by ajay on 13/09/16.
 */

/*
    - The base case is when n = 0, and x^0 = 1
    - If n is positive and even, recursively compute y = x^{n/2}  and then x^n = y*y.
        Notice that you can get away with making just one recursive call in this case, computing x^{n/2} just once,
        and then you multiply the result of this recursive call by itself.
    - If n is positive and odd, recursively compute x^{n-1}, so that the exponent either is 0 or is positive and even.
        Then, x^n = x^{n-1}*x
​   - If n is negative, recursively compute x^{-n} so that the exponent becomes positive. Then, x^n = 1 / x^{-n}.
 */

public class Power {

    public static void main(String[] args) {
        System.out.println(computePower(0,2));
        System.out.println(computePower(2,0));
        System.out.println(computePower(3,1));
        System.out.println(computePower(4,2));
        System.out.println(computePower(5,-1));
    }

    private static float computePower(int base, int exponent) {

        if (base == 0) {
            return 0;
        }

        if (exponent == 0) {
            return 1;
        } else if (exponent < 0) {
            return 1/computePower(base, -exponent);
        } else if (isEven(exponent)) {
            float result = computePower(base, exponent / 2);
            return result * result;
        } else {
            return base * computePower(base, exponent - 1);
        }
    }

    private static boolean isEven(int num) {
        if (num % 2 == 0) {
            return true;
        }
        return false;
    }
}
