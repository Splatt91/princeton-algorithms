import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueWithArrayImpl<Item> implements Iterable<Item> {

    private Item[] q;
    private int n;
    private int head;
    private int tail;

    public QueueWithArrayImpl() {
        q = (Item[]) new Object[1];
        n = head = tail = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            copy[i] = q[(head + i) % q.length];
        }
        q = copy;
        head = 0;
        tail = n;
    }

    public void enqueue(Item item) {
        if (n == q.length) {
            resize(n * 2);
        }
        q[tail] = item;
        tail = (tail + 1) % q.length;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("No elements in queue");
        }
        Item item = q[head];
        q[head] = null;
        n--;
        head = (head + 1) % q.length;
        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new RuntimeException("No elements in queue");
        }
        return q[head];
    }

    public Iterator<Item> iterator() {
        return new QueueWithArrayIterator();
    }

    private class QueueWithArrayIterator implements Iterator<Item> {
        private int i;


        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = q[(i + head) % q.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        QueueWithArrayImpl<Integer> queueWithArray = new QueueWithArrayImpl<>();
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            queueWithArray.enqueue(i + 1);
        }
        for (Integer item : queueWithArray) {
            System.out.println(item);
        }
    }
}
