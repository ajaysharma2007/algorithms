package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ajay on 10/14/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null object to the list.");
        }

        Node oldFirstNode = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirstNode;

        if (oldFirstNode == null) {
            this.last = this.first;
        } else {
            oldFirstNode.previous = first;
        }
        this.size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null object to the list.");
        }
        Node oldLastNode = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        this.last.previous = oldLastNode;

        if (oldLastNode == null) {
            this.first = this.last;
        } else {
            oldLastNode.next = this.last;
        }

        if (this.size == 0) {
        }

        this.size++;
    }

    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("Can't remove from the empty list.");
        }

        Item firstItem = this.first.item;
        this.first = this.first.next;

        if (first != null) {
            this.first.previous = null;
        }

        if (--this.size == 0) {
            this.last = null;
        }
        return firstItem;
    }

    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("Can't remove from the empty list.");
        }

        Item lastItem = this.last.item;
        this.last = this.last.previous;
        if (this.last != null) {
            this.last.next = null;
        }
        if (--this.size == 0) {
            this.first = null;
        }
        return lastItem;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node currentNode;
        private int iteratorPointer = 0;

        DequeIterator() {
            currentNode = first;
            if (currentNode != null) {
                iteratorPointer = 1;
            }
        }

        @Override
        public boolean hasNext() {
            return (iteratorPointer > 0 && iteratorPointer <= size);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No element present in the queue.");
            Item currentItem = currentNode.item;
            currentNode = currentNode.next;
            iteratorPointer++;
            return currentItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation not supported.");
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public static void main(String[] args) {

    }
}
