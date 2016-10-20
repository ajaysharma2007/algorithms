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
    public void testParallelIterator() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        randomizedQueue.enqueue("Test1");
        randomizedQueue.enqueue("Test2");
        randomizedQueue.enqueue("Test3");
        randomizedQueue.enqueue("Test4");
        randomizedQueue.enqueue("Test5");
        randomizedQueue.enqueue("Test6");
        randomizedQueue.enqueue("Test7");
        randomizedQueue.enqueue("Test8");
        randomizedQueue.enqueue("Test9");
        randomizedQueue.enqueue("Test10");
        Iterator<String> randomizedQueueIterator1 = randomizedQueue.iterator();
        Iterator<String> randomizedQueueIterator2 = randomizedQueue.iterator();

        Assert.assertTrue(randomizedQueueIterator1.hasNext());

        int count = 0;
        while (randomizedQueueIterator1.hasNext()) {
            String nextVal = randomizedQueueIterator1.next();
            count++;
        }

        Assert.assertEquals(count, 10);
        Assert.assertFalse(randomizedQueueIterator1.hasNext());
        Assert.assertTrue(randomizedQueueIterator2.hasNext());

        System.out.println();

        count = 0;
        while (randomizedQueueIterator2.hasNext()) {
            String nextVal = randomizedQueueIterator2.next();
            count++;
        }
        Assert.assertEquals(count, 10);
        Assert.assertFalse(randomizedQueueIterator2.hasNext());
    }
}
