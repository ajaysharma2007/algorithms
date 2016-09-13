package recursion;

/**
 * Created by ajay on 13/09/16.
 */

/*
    - If the string is made of no letters or just one letter, then it is a palindrome.
    - Otherwise, compare the first and last letters of the string.
        - If the first and last letters differ, then the string is not a palindrome.
    - Otherwise, the first and last letters are the same. Strip them from the string, and determine whether the string
        that remains is a palindrome. Take the answer for this smaller string and use it as the answer for the original
        string.
 */
public class Palindrome {

    public static void main(String[] args) {

        System.out.println(isPalindrome2("a"));
        System.out.println(isPalindrome2(""));
        System.out.println(isPalindrome2("rotor"));
        System.out.println(isPalindrome2("asdff"));
    }

    private static boolean isPalindrome(String str){

        int strLength = str.length();

        if(strLength == 0 || strLength == 1) {
            return true;
        }

        if(str.charAt(0) != str.charAt(strLength-1)) {
            return false;
        }

        return isPalindrome(str.substring(1, strLength-1));
    }

    private static boolean isPalindrome2(String str) {

        for (int i = 0; i < str.length()/2; i++) {
            if(str.charAt(i) != str.charAt(str.length()-i-1)) {
                return false;
            }
        }
        return true;
    }
}
