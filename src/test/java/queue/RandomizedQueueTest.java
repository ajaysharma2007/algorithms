package queue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ajay on 15/10/16.
 */
public class RandomizedQueueTest {

    @Test(expected = NullPointerException.class)
    public void testEnqueueNullItem() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue(null);
    }

    @Test
    public void testEnqueueValidInput() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue("Test");
        Assert.assertFalse(randomizedQueue.isEmpty());
        Assert.assertEquals(randomizedQueue.size(), 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueEmptyQueue() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.dequeue();
    }

    @Test
    public void testDequeueValidInput() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue("Test");
        Assert.assertEquals(randomizedQueue.dequeue(), "Test");
        Assert.assertTrue(randomizedQueue.isEmpty());
        Assert.assertEquals(randomizedQueue.size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testSampleEmptyQueue() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.sample();
    }

    @Test
    public void testSampleValidInput() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue("Test");
        Assert.assertEquals(randomizedQueue.sample(), "Test");
        Assert.assertFalse(randomizedQueue.isEmpty());
        Assert.assertEquals(randomizedQueue.size(), 1);
    }

    @Test
    public void testIterator() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue("Test1");
        randomizedQueue.enqueue("Test2");
        Iterator<String> randomizedQueueIterator1 = randomizedQueue.iterator();
        Iterator<String> randomizedQueueIterator2 = randomizedQueue.iterator();

        Assert.assertTrue(randomizedQueueIterator1.hasNext());

        int count = 0;
        while (randomizedQueueIterator1.hasNext()) {
            String nextVal = randomizedQueueIterator1.next();
            Assert.assertTrue(nextVal.equals("Test1") || nextVal.equals("Test2"));
            count++;
        }

        Assert.assertEquals(count, 2);
        Assert.assertFalse(randomizedQueueIterator1.hasNext());
        Assert.assertTrue(randomizedQueueIterator2.hasNext());

        count = 0;
        while (randomizedQueueIterator2.hasNext()) {
            String nextVal = randomizedQueueIterator2.next();
            Assert.assertTrue(nextVal.equals("Test1") || nextVal.equals("Test2"));
            count++;
        }
        Assert.assertEquals(count, 2);
        Assert.assertFalse(randomizedQueueIterator2.hasNext());
    }
}
