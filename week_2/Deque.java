
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {
        public Item item;
        public Node<Item> prev;
        public Node<Item> next;

        public Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null!");
        }

        if (isEmpty()) {
            first = new Node<Item>(item);
            last = first;
        }
        else {
            Node<Item> oldFirst = first;
            first = new Node<Item>(item);
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null!");
        }

        if (isEmpty()) {
            last = new Node<Item>(item);
            first = last;
        }
        else {
            Node<Item> oldLast = last;
            last = new Node<Item>(item);
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue is empty!");
        }

        Node<Item> node = first;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first.next.prev = null;
            first = first.next;
        }
        node.next = null;
        size--;
        return node.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue is empty!");
        }

        Node<Item> node = last;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last.prev.next = null;
            last = last.prev;
        }
        node.prev = null;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Dequeue is empty!");
            }
            Node<Item> node = current;
            current = current.next;
            return node.item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not a supported operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();
        int n = 5;

        System.out.println("deck isEmpty: " + deck.isEmpty());
        for (int i = 0; i < n; i++) {
            deck.addFirst(i + 1);
        }
        System.out.println("deck size: " + deck.size());
        try {
            for (int i = 0; i <= n; i++) {
                System.out.println(deck.removeLast());
            }
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }

        try {
            deck.addFirst(null);
        }
        catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }

        try {
            deck.addLast(null);
        }
        catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (int i = 0; i < n; i++) {
            deck.addLast(i + 1);
        }
        try {
            for (int i = 0; i <= n; i++) {
                System.out.println(deck.removeFirst());
            }
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }

        Iterator<Integer> iter = deck.iterator();
        System.out.println("hasNext:" + iter.hasNext());
        for (int i = 0; i < n; i++) {
            deck.addFirst(i + 1);
        }
        iter = deck.iterator();
        System.out.println("hasNext:" + iter.hasNext());

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        try {
            iter.remove();
        }
        catch (UnsupportedOperationException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
