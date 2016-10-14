package queue;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.portable.Streamable;
import search.BinarySearch;

/**
 * Created by ajay on 10/14/16.
 */
public class DequeTest {

    @Test(expected = NullPointerException.class)
    public void testAddFirstThrowException() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addFirst(null);
    }

    @Test
    public void testAddFirstValidInput() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addFirst("Test");
        Assert.assertEquals(1, dequeTest.size());
        Assert.assertFalse(dequeTest.isEmpty());
        Assert.assertEquals("Test", dequeTest.removeFirst());
        Assert.assertEquals(dequeTest.isEmpty(), true);

    }

    @Test(expected = NullPointerException.class)
    public void testAddLastThrowException() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addLast(null);
    }

    @Test
    public void testAddLastValidInput() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addFirst("Remove1");
        dequeTest.addLast("Remove2");
        Assert.assertEquals(2, dequeTest.size());
        Assert.assertFalse(dequeTest.isEmpty());
        Assert.assertEquals("Remove1", dequeTest.removeFirst());
        Assert.assertEquals("Remove2", dequeTest.removeLast());
        Assert.assertEquals(0, dequeTest.size());
        Assert.assertTrue(dequeTest.isEmpty());

    }

    @Test
    public void testRemoveFirstValidInput() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addFirst("Remove1");
        dequeTest.addLast("Remove2");
        Assert.assertEquals(2, dequeTest.size());
        Assert.assertFalse(dequeTest.isEmpty());
        Assert.assertEquals("Remove1", dequeTest.removeFirst());
        Assert.assertEquals("Remove2", dequeTest.removeFirst());
        Assert.assertEquals(0, dequeTest.size());
        Assert.assertTrue(dequeTest.isEmpty());
    }

    @Test
    public void testRemoveLastValidInput() {
        Deque<String> dequeTest = new Deque();
        dequeTest.addFirst("Remove1");
        dequeTest.addLast("Remove2");
        Assert.assertEquals(2, dequeTest.size());
        Assert.assertFalse(dequeTest.isEmpty());
        Assert.assertEquals("Remove2", dequeTest.removeLast());
        Assert.assertEquals("Remove1", dequeTest.removeLast());
        Assert.assertEquals(0, dequeTest.size());
        Assert.assertTrue(dequeTest.isEmpty());
    }

}
