/**
 * Created by ajay on 07/09/16.
 */
package search;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {

    @Test
    public void testBinarySearchNumberNotFound() {
        BinarySearch binarySearchTest = new BinarySearch();
        Assert.assertEquals("Number not found in array", -1, binarySearchTest.binarySearch(10));
    }

    @Test
    public void testBinarySearchLowerRangeNumberFound() {
        BinarySearch binarySearchTest = new BinarySearch();
        Assert.assertEquals("Number found.", 1, binarySearchTest.binarySearch(2));
    }

    @Test
    public void testBinarySearchHigherRangeNumberFound() {
        BinarySearch binarySearchTest = new BinarySearch();
        Assert.assertEquals("Number found.", 6, binarySearchTest.binarySearch(7));
    }

}
