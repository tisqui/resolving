
/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/1/13
 * Time: 11:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;

    }


    /**
     * construct an empty deque
     */
    public Deque() {
        super();
        first = new Node<Item>();
        last = first;
        size = 0;
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
            throw new java.lang.NullPointerException();
        }

        if (size != 0) {
            Node<Item> oldFirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        } else {
            first.item = item;
        }
        size++;
    }

    /**
     * insert the item at the end
     *
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (size != 0) {
            Node<Item> oldLast = last;

            last = new Node<Item>();
            last.item = item;
            last.previous = oldLast;
            oldLast.next = last;
        } else {
            last.item = item;
        }
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
        size--;
        if (size == 0) {
            Item item = first.item;
            first.item = null;
            return item;

        } else {
            Item item = first.item;
            first = first.next;
            first.previous = null;
            return item;
        }

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
        size--;
        if (size == 0) {
            Item item = last.item;
            last.item = null;
            return item;

        } else {
            Item item = last.item;
            last = last.previous;
            last.next = null;

            return item;
        }
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

        private Node<Item> current = first;

        {
            if (size == 0) {
                current = null;
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }


        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
