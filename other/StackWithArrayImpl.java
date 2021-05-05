import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackWithArrayImpl<Item> implements Iterable<Item> {

    private Item[] stack;
    private int n;

    public StackWithArrayImpl() {
        stack = (Item[]) new Object[1];
        n = 0;
    }

    private void resize(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];
        for (int i = 0; i < stack.length; i++) {
            tmp[i] = stack[i];
        }
        stack = tmp;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void push(Item item) {
        if (n == stack.length) {
            resize(n * 2);
        }
        stack[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new RuntimeException("Empty stack");
        }
        Item item = stack[n - 1];
        stack[n--] = null;
        if (n > 0 && n == stack.length / 4) {
            resize(stack.length / 2);
        }
        return item;
        // return stack[--N]; // loitering
    }

    public Item peek() {
        if (isEmpty()) {
            throw new RuntimeException("Empty stack");
        }
        return stack[n - 1];
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {

        private int currItem = n - 1;

        public boolean hasNext() {
            return currItem >= 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack[currItem--];
        }
    }

    // Test method
    public static void main(String[] args) {
        StackWithArrayImpl<Integer> testStackWithArrayImpl = new StackWithArrayImpl<>();
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            testStackWithArrayImpl.push(i + 1);
        }
        for (Integer integer : testStackWithArrayImpl) {
            System.out.println(integer);
        }
    }
}
