package queue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ajay on 14/10/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int lastArrayIndex;
    private Item[] items;

    public RandomizedQueue() {
        lastArrayIndex = -1;
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return lastArrayIndex == -1;
    }

    public int size() {
        return lastArrayIndex + 1;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null item to the queue.");
        }
        if (lastArrayIndex == items.length - 1) {
            resize(items.length * 2);
        }
        this.items[++lastArrayIndex] = item;
    }

    public Item dequeue() {
        if (this.lastArrayIndex == -1) {
            throw new NoSuchElementException("No elements present in the List");
        }

        int random = StdRandom.uniform(lastArrayIndex + 1);
        Item randomItem = items[random];
        for (int i = random + 1; i <= lastArrayIndex; i++) {
            items[i - 1] = items[i];
        }

        items[lastArrayIndex--] = null;

        if (lastArrayIndex == items.length / 4) {
            resize(items.length / 2);
        }

        return randomItem;
    }

    public Item sample() {
        if (this.lastArrayIndex == -1) {
            throw new NoSuchElementException("No elements present in the List");
        }

        int random = StdRandom.uniform(lastArrayIndex + 1);
        return items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int iteratorPointer = lastArrayIndex + 1;

        RandomizedQueueIterator() {
            if (!isEmpty()) {
                iteratorPointer = lastArrayIndex;
            }
        }

        @Override
        public boolean hasNext() {
            return iteratorPointer >= 0 && iteratorPointer <= lastArrayIndex;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next item " +
                        "present in the queue.");
            }
            int random = StdRandom.uniform(iteratorPointer + 1);
            Item randomItem = items[random];

            swapArrayIndexes(random, iteratorPointer);
            iteratorPointer--;
            return randomItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not " +
                    "supported on the iterator.");
        }
    }

    private void resize(int capacity) {
        Item[] newItemArr = (Item[]) new Object[capacity];
        for (int i = 0; i <= this.lastArrayIndex; i++) {
            newItemArr[i] = items[i];
        }
        items = newItemArr;
    }

    private void swapArrayIndexes(int index1, int index2) {
        Item tempItem = items[index1];
        items[index1] = items[index2];
        items[index2] = tempItem;
    }
}