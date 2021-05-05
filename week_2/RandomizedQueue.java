
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private Item[] resize(Item[] arrToCopy, int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < arrToCopy.length; i++) {
            if (arrToCopy[i] == null) {
                break;
            }
            copy[i] = arrToCopy[i];
        }
        return copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        if (size == q.length) {
            q = resize(q, size * 2);
        }

        q[size] = item;
        size++;
    }

    // // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int randomIndex = StdRandom.uniform(size);
        Item item = q[randomIndex];
        q[randomIndex] = q[size - 1];
        q[size - 1] = null;
        size--;

        if (size > 0 && size == q.length / 4) {
            q = resize(q, q.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }

        int randomIndex = StdRandom.uniform(size);
        Item item = q[randomIndex];

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(q, size);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] qCopy;
        private int sizeCopy;

        public RandomizedQueueIterator(Item[] q, int size) {
            sizeCopy = size;
            qCopy = resize(q, size);
        }

        public boolean hasNext() {
            return sizeCopy != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty!");
            }

            int randomIndex = StdRandom.uniform(sizeCopy);
            Item item = qCopy[randomIndex];
            qCopy[randomIndex] = qCopy[sizeCopy - 1];
            qCopy[sizeCopy - 1] = null;
            sizeCopy--;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported!");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        int n = StdRandom.uniform(20);
        for (int i = 0; i < n; i++) {
            randomizedQueue.enqueue(i + 1);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(randomizedQueue.dequeue());
        }

        for (int i = 0; i < n; i++) {
            randomizedQueue.enqueue(i + 1);
        }
        for (Integer item : randomizedQueue) {
            System.out.println(item);
        }

        System.out.println("Sample: " + randomizedQueue.sample());

        Iterator<Integer> it1 = randomizedQueue.iterator();
        Iterator<Integer> it2 = randomizedQueue.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            System.out.println("it1: " + it1.next() + ", it2: " + it2.next());
        }
    }

}