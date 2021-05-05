import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueWithListImpl<Item> implements Iterable<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private int n;

    public static class Node<Item> {
        public Item item;
        public Node<Item> next;
    }

    public QueueWithListImpl() {
        head = null;
        tail = null;
        n = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        Node<Item> oldTail = tail;
        tail = new Node<Item>();
        tail.item = item;
        tail.next = null;
        if (isEmpty()) {
            head = tail;
        }
        else {
            oldTail.next = tail;
        }
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = head.item;
        head = head.next;
        n--;
        if (isEmpty()) {
            tail = null; // avoid loitering
        }
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.item;
    }

    public static void main(String[] args) {
        QueueWithListImpl<Integer> queueWithList = new QueueWithListImpl<>();
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            queueWithList.enqueue(i + 1);
        }
        for (Integer item : queueWithList) {
            System.out.println(item);
        }
    }

    public Iterator<Item> iterator() {
        return new QueueWithListIterator(head);
    }

    private class QueueWithListIterator implements Iterator<Item> {

        private Node<Item> currElem;

        public QueueWithListIterator(Node<Item> head) {
            currElem = head;
        }

        public boolean hasNext() {
            return currElem != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = currElem.item;
            currElem = currElem.next;
            return item;
        }
    }
}
