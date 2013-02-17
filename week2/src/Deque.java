import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue supporting inserting and removing items from either the
 * front or the back of the data structure.
 *
 * @author Simon Nattrass
 */
public class Deque<Item> implements Iterable<Item>
{
    private int size = 0;
    private Node<Item> first;   // first item in the deque
    private Node<Item> last;    // last item in the deque

    /**
     * Construct and empty deque
     */
    public Deque()
    {
    }

    /**
     * Is the deque empty?
     */
    public boolean isEmpty()
    {
        return (null == first) || (null == last);
    }

    /**
     * Return ths number if items in the deque
     */
    public int size()
    {
        return size;
    }

    /**
     * Insert an item at the front of the deque
     */
    public void addFirst(Item item)
    {
        if (null == item) {
            throw new NullPointerException();
        }

        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;

        first.previous = null;

        if (isEmpty()) {
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.previous = last;
        }

        size++;
    }

    /**
     * Insert an item at the end of the deque
     */
    public void addLast(Item item)
    {
        if (null == item) {
            throw new NullPointerException();
        }

        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;

        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.previous = oldLast;
        }

        size++;
    }

    /**
     * Delete and return the item at the front
     */
    public Item removeFirst()
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }

        size--;
        return item;
    }

    /**
     * Delete and return the item at the end
     */
    public Item removeLast()
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.previous;

        if (isEmpty()) {
            first = null;
        }

        size--;
        return item;
    }

    /**
     * Return an iterator for the deque
     */
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    /**
     * Class representing a node in the deque
     */
    private class Node<Item>
    {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    /**
     * Iterator for the deque
     */
    private class DequeIterator implements Iterator<Item>
    {
        private Node<Item> current = first;

        /**
         * Do we have more items to iterate over?
         */
        public boolean hasNext()
        {
            return null != current;
        }

        /**
         * Not supported
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        /**
         * Move to the next item in the iteration
         */
        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
