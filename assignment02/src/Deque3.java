
/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/1/13
 * Time: 11:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque3<Item> implements Iterable<Item> {

    private static final int SIZE = 30;

    private Object[] first = new Object[SIZE + 2];
    private Object[] last = first;

    private int firstN = SIZE / 2;
    private int lastN = firstN;
    private int size = 0;

    /**
     * construct an empty deque
     */
    public Deque3() {
        super();
    }

    /**
     * is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        firstN = (firstN - 1 + SIZE) % SIZE;
        int i = firstN + 1;
        if (i == SIZE) {
            Object[] nf = new Object[SIZE + 2];
            nf[SIZE + 1] = first;
            first[0] = nf;
            first = nf;
        }
        first[i] = item;
        size++;
    }

    /**
     * insert the item at the end
     *
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        lastN = (lastN + 1) % SIZE;
        int i = lastN + 1;
        if (i == 1) {
            Object[] nf = new Object[SIZE + 2];
            nf[0] = last;
            last[SIZE + 1] = nf;
            last = nf;
        }
        last[i] = item;
        size++;
    }

    /**
     * delete and return the item at the front
     *
     * @return
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int i = firstN % SIZE + 1;
        Item item = (Item) first[i];
        first[i] = null;

        if (i == SIZE && size > 1) {
            first = (Object[]) first[SIZE + 1];
            first[0] = null;
        }

        firstN++;
        size--;
        return item;
    }

    /**
     * delete and return the item at the end
     *
     * @return
     */
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int i = lastN % SIZE + 1;
        Item item = (Item) last[i];
        last[i] = null;

        if (i == 1 && size > 1) {
            last = (Object[]) last[0];
            last[SIZE + 1] = null;
        }

        lastN = lastN - 1 + SIZE;
        size--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Object[] current = first;
        private int index = firstN % SIZE;
        private int n = 0;

        @Override
        public boolean hasNext() {
            return n < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            n++;

            Item item = (Item) current[index + 1];

            index = (index + 1) % SIZE;
            if (index == 0 && n < size) {
                current = (Object[]) current[SIZE + 1];
            }
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
