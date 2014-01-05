import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/1/13
 * Time: 11:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static Random rand = new Random();
    private static final int MINELEMENTS = 1;
    private Item[] elements;
    private boolean isEmpty;
    private int last;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        Item[] items =  (Item[]) new Object[MINELEMENTS];
        elements = items;
        isEmpty = true;
        last = 0;
    }

    /**
     * Check if the queue is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * return the number of items on the queue
     *
     * @return
     */
    public int size() {
        if (isEmpty) {
            return 0;
        } else {
            return last + 1;
        }
    }

    /**
     * Add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (isEmpty()) {
            elements[0] = item;
            last = 0;
            isEmpty = false;
        } else if ((last + 1) == elements.length) {
            resize(elements.length * 2);
            last++;
            elements[last] = item;
        } else {
            last++;
            elements[last] = item;
        }

    }

    private void resize(int newSize) {
        Item[] newElements = (Item[]) new Object[newSize];
        for (int i = 0; i <= last; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

    }

    /**
     * delete and return a random item
     *
     * @return
     */
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        exchange(findRandom(), last);

        Item deletingItem = elements[last];
        elements[last] = null;
        last--;

        if (last == -1) {
            isEmpty = true;
            last = 0;
            resize(1);
        } else if (last < elements.length / 2) {
            resize(elements.length / 2);
        } else if (last == 0) {
            resize(1);
        }

        return deletingItem;
    }

    private void exchange(int i, int j) {
        if (i != j) {
            Item buff = elements[i];
            elements[i] = elements[j];
            elements[j] = buff;
        }
    }

    private int findRandom() {
        return rand.nextInt(last+1);
    }

    /**
     * return (but do not delete) a random item
     *
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[findRandom()];
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int n = last + 1;
        private Item[] array = (Item[]) new Object[n];


        public RandomizedQueueIterator() {
            super();

            for (int i = 0; i < n; i++) {
                array[i] = elements[i];
            }
            StdRandom.shuffle(array);

        }

        @Override
        public boolean hasNext() {
            return n > 0;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return array[--n];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
