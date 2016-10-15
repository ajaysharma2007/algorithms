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
        swapArrayIndexes(items, random, lastArrayIndex);

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
        private Item[] iteratorItems;

        RandomizedQueueIterator() {
            if (!isEmpty()) {
                iteratorPointer = lastArrayIndex;
                iteratorItems = (Item[]) new Object[lastArrayIndex+1];
                for (int i = 0; i <= lastArrayIndex; i++) {
                    iteratorItems[i] = items[i];
                }
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
            Item randomItem = iteratorItems[random];

            swapArrayIndexes(iteratorItems, random, iteratorPointer);
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

    private void swapArrayIndexes(Item[] localItems, int index1, int index2) {
        Item tempItem = localItems[index1];
        localItems[index1] = localItems[index2];
        localItems[index2] = tempItem;
    }
}