import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackWithListImpl<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public StackWithListImpl() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() { return n; }

    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new RuntimeException("Empty stack");
        }
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new RuntimeException("Empty stack");
        }
        return first.item;
    }

    public Iterator iterator() {
        return new StackWithListImplIterator(first);
    }

    private class StackWithListImplIterator implements Iterator<Item> {

        private Node<Item> currNode;

        public StackWithListImplIterator(Node<Item> first) {
            currNode = first;
        }

        public boolean hasNext() {
            return currNode != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = currNode.item;
            currNode = currNode.next;
            return item;
        }
    }


    public static void main(String[] args) {
        StackWithListImpl<Integer> stackWithList = new StackWithListImpl<>();
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            stackWithList.push(i + 1);
        }
        for (Integer integer : stackWithList) {
            System.out.println(integer);
        }
    }
}
